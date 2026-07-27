package br.com.ifba.grupoesportivo.service;

import br.com.ifba.administrador.entity.Administrador;
import br.com.ifba.administrador.repository.AdministradorRepository;
import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.atleta.repository.AtletaRepository;
import br.com.ifba.esporte.entity.Esporte;
import br.com.ifba.esporte.repository.EsporteRepository;
import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import br.com.ifba.grupoesportivo.repository.GrupoEsportivoRepository;
import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GrupoEsportivoService implements GrupoEsportivoIService {

    private static final Logger logger = LoggerFactory.getLogger(GrupoEsportivoService.class);

    private final GrupoEsportivoRepository grupoEsportivoRepository;
    private final AdministradorRepository administradorRepository;
    private final AtletaRepository atletaRepository;
    private final EsporteRepository esporteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public GrupoEsportivo save(GrupoEsportivo grupoEsportivo, Long usuarioId, String esporteNome) {
        logger.info("[SERVICE] Criando grupo '{}' para o utilizador ID {}", grupoEsportivo.getNome(), usuarioId);

        // 1. Valida nome único do grupo
        validarNomeUnico(grupoEsportivo);

        // 2. Busca o Esporte cadastrado
        Esporte esporte = esporteRepository.findByNome(esporteNome)
                .orElseThrow(() -> new ResourceNotFoundException("Esporte '" + esporteNome + "' não encontrado."));

        // 3. Garante que o utilizador existe na base de dados (tabela usuarios)
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilizador não encontrado com o ID: " + usuarioId));

        // 4. Tenta encontrar o Administrador diretamente ou realiza a promoção nativa no banco
        Administrador admin = administradorRepository.findById(usuarioId)
                .orElseGet(() -> {
                    logger.info("[SERVICE] Promovendo o utilizador ID {} para a tabela 'administradores'...", usuarioId);

                    // Insere o registro na tabela administradores e limpa o cache JPA
                    administradorRepository.promoverUsuarioParaAdministrador(usuario.getId());

                    // Busca novamente o Administrador atualizado do banco
                    return administradorRepository.findById(usuario.getId())
                            .orElseThrow(() -> new BusinessException("Falha ao carregar perfil de Administrador após a promoção."));
                });

        // 5. Vincula Administrador e Esporte ao Grupo Esportivo
        grupoEsportivo.setAdministrador(admin);
        grupoEsportivo.setEsporte(esporte);

        if (grupoEsportivo.getDataCriacao() == null) {
            grupoEsportivo.setDataCriacao(LocalDate.now());
        }

        // 6. Salva o Grupo Esportivo
        GrupoEsportivo grupoSalvo = grupoEsportivoRepository.save(grupoEsportivo);
        logger.info("[SERVICE] Grupo Esportivo '{}' salvo com sucesso com ID {}!", grupoSalvo.getNome(), grupoSalvo.getId());

        return grupoSalvo;
    }
    // Mantêm-se os outros métodos da classe...
    @Transactional
    public void solicitarEntrada(Long grupoId, Long atletaId) {
        GrupoEsportivo grupo = grupoEsportivoRepository.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo esportivo não encontrado."));

        Atleta atleta = atletaRepository.findById(atletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));

        if (!grupo.getSolicitacoesPendentes().contains(atleta) && !grupo.getAtletas().contains(atleta)) {
            grupo.getSolicitacoesPendentes().add(atleta);
            grupoEsportivoRepository.save(grupo);
        }
    }

    @Transactional
    public void aceitarAtleta(Long grupoId, Long atletaId, Long adminIdLogado) {
        GrupoEsportivo grupo = grupoEsportivoRepository.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo esportivo não encontrado."));

        if (!grupo.getAdministrador().getId().equals(adminIdLogado)) {
            throw new BusinessException("Apenas o Administrador deste grupo pode aceitar atletas.");
        }

        Atleta atleta = atletaRepository.findById(atletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));

        grupo.getSolicitacoesPendentes().remove(atleta);
        if (!grupo.getAtletas().contains(atleta)) {
            grupo.getAtletas().add(atleta);
        }

        grupoEsportivoRepository.save(grupo);
    }

    @Override
    public Page<GrupoEsportivo> findAll(Pageable pageable) {
        return grupoEsportivoRepository.findAll(pageable);
    }

    @Override
    public GrupoEsportivo findById(Long id) {
        return grupoEsportivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo esportivo não encontrado."));
    }

    @Override
    public void delete(Long id) {
        if (!grupoEsportivoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grupo esportivo não encontrado.");
        }
        grupoEsportivoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public GrupoEsportivo update(Long id, GrupoEsportivo grupoEsportivo) {
        logger.info("[SERVICE] Atualizando grupo esportivo ID {}", id);

        // 1. Busca o grupo existente no banco de dados
        GrupoEsportivo grupoExistente = findById(id);

        // 2. Valida se o novo nome já está em uso por OUTRO grupo
        if (grupoEsportivo.getNome() != null &&
                !grupoExistente.getNome().equalsIgnoreCase(grupoEsportivo.getNome()) &&
                grupoEsportivoRepository.existsByNomeAndIdNot(grupoEsportivo.getNome(), id)) {

            logger.error("[SERVICE] Já existe outro grupo cadastrado com o nome: {}", grupoEsportivo.getNome());
            throw new BusinessException("Já existe um grupo esportivo cadastrado com esse nome.");
        }

        // 3. Atualiza os campos permitidos
        if (grupoEsportivo.getNome() != null && !grupoEsportivo.getNome().isBlank()) {
            grupoExistente.setNome(grupoEsportivo.getNome());
        }

        if (grupoEsportivo.getDescricao() != null && !grupoEsportivo.getDescricao().isBlank()) {
            grupoExistente.setDescricao(grupoEsportivo.getDescricao());
        }

        // 4. Salva e retorna o grupo atualizado
        GrupoEsportivo grupoAtualizado = grupoEsportivoRepository.save(grupoExistente);
        logger.info("[SERVICE] Grupo esportivo ID {} atualizado com sucesso!", id);

        return grupoAtualizado;
    }

    private void validarNomeUnico(GrupoEsportivo grupoEsportivo) {
        if (grupoEsportivoRepository.existsByNome(grupoEsportivo.getNome())) {
            throw new BusinessException("Já existe um grupo esportivo cadastrado com esse nome.");
        }
    }
}