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
        logger.info("[SERVICE] Criando grupo '{}' pelo utilizador ID {}", grupoEsportivo.getNome(), usuarioId);

        // 1. Valida se já existe um grupo com o mesmo nome
        validarNomeUnico(grupoEsportivo);

        // 2. Busca o Esporte na base de dados pelo NOME
        Esporte esporte = esporteRepository.findByNome(esporteNome)
                .orElseThrow(() -> new ResourceNotFoundException("Esporte '" + esporteNome + "' não encontrado."));

        // 3. Tenta encontrar o Administrador pelo ID.
        // Se não encontrar, promove o usuário existente inserindo o ID na tabela 'administradores'
        Administrador admin = administradorRepository.findById(usuarioId)
                .orElseGet(() -> {
                    logger.info("[SERVICE] Utilizador ID {} não é administrador. Promovendo perfil...", usuarioId);

                    // Garante que o usuário existe na tabela base de usuários
                    Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                            .orElseThrow(() -> new ResourceNotFoundException("Utilizador não encontrado com o ID: " + usuarioId));

                    try {
                        // Promove o usuário diretamente no PostgreSQL
                        administradorRepository.promoverUsuarioParaAdministrador(usuarioExistente.getId());

                        // Retorna o Administrador devidamente carregado pelo JPA
                        return administradorRepository.findById(usuarioExistente.getId())
                                .orElseThrow(() -> new BusinessException("Falha ao carregar perfil de Administrador após promoção."));
                    } catch (Exception ex) {
                        logger.error("[SERVICE] Erro ao promover utilizador para Administrador: {}", ex.getMessage(), ex);
                        throw new BusinessException("Erro ao associar perfil de Administrador ao utilizador.");
                    }
                });

        // 4. Associa Administrador e Esporte ao Grupo Esportivo
        grupoEsportivo.setAdministrador(admin);
        grupoEsportivo.setEsporte(esporte);

        if (grupoEsportivo.getDataCriacao() == null) {
            grupoEsportivo.setDataCriacao(LocalDate.now());
        }

        try {
            GrupoEsportivo grupoSalvo = grupoEsportivoRepository.save(grupoEsportivo);
            logger.info("[SERVICE] Grupo '{}' salvo com sucesso pelo Admin ID {}!", grupoSalvo.getNome(), admin.getId());
            return grupoSalvo;
        } catch (Exception e) {
            logger.error("[SERVICE] Erro ao salvar grupo esportivo: {}", e.getMessage(), e);
            throw new BusinessException("Erro interno ao realizar o cadastro do grupo esportivo.");
        }
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
        GrupoEsportivo grupoExistente = findById(id);

        if (grupoEsportivoRepository.existsByNomeAndIdNot(grupoEsportivo.getNome(), id)) {
            throw new BusinessException("Já existe um grupo esportivo cadastrado com esse nome.");
        }

        grupoExistente.setNome(grupoEsportivo.getNome());
        grupoExistente.setDescricao(grupoEsportivo.getDescricao());

        return grupoEsportivoRepository.save(grupoExistente);
    }

    private void validarNomeUnico(GrupoEsportivo grupoEsportivo) {
        if (grupoEsportivoRepository.existsByNome(grupoEsportivo.getNome())) {
            throw new BusinessException("Já existe um grupo esportivo cadastrado com esse nome.");
        }
    }
}