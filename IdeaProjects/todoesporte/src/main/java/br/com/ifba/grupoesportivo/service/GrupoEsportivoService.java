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
    // Injeção do repositório base de usuários para fallback
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
        // Se não encontrar, tenta buscar em Atleta ou Usuario base e promove a Administrador
        Administrador admin = administradorRepository.findById(usuarioId)
                .orElseGet(() -> {
                    logger.info("[SERVICE] Utilizador ID {} ainda não é administrador. Registando perfil...", usuarioId);

                    // Busca primeiro no Atleta ou diretamente no Usuario base
                    Usuario usuarioBase = atletaRepository.findById(usuarioId)
                            .map(atleta -> (Usuario) atleta)
                            .orElseGet(() -> usuarioRepository.findById(usuarioId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Utilizador não encontrado com o ID: " + usuarioId))
                            );

                    // Instancia o novo Administrador copiando os dados do utilizador encontrado
                    Administrador novoAdmin = new Administrador();
                    novoAdmin.setNome(usuarioBase.getNome());
                    novoAdmin.setEmail(usuarioBase.getEmail());
                    novoAdmin.setLogin(usuarioBase.getLogin());
                    novoAdmin.setSenha(usuarioBase.getSenha());
                    novoAdmin.setTelefone(usuarioBase.getTelefone());
                    novoAdmin.setDataCadastro(usuarioBase.getDataCadastro() != null ? usuarioBase.getDataCadastro() : LocalDate.now());
                    novoAdmin.setStatus(usuarioBase.getStatus() != null ? usuarioBase.getStatus() : true);

                    try {
                        Administrador adminSalvo = administradorRepository.save(novoAdmin);
                        logger.info("[SERVICE] Perfil de Administrador (ID: {}) gerado com sucesso.", adminSalvo.getId());
                        return adminSalvo;
                    } catch (Exception ex) {
                        logger.error("[SERVICE] Erro ao cadastrar perfil de Administrador: {}", ex.getMessage(), ex);
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
            logger.info("[SERVICE] Grupo '{}' salvo com sucesso!", grupoSalvo.getNome());
            return grupoSalvo;
        } catch (Exception e) {
            logger.error("[SERVICE] Erro ao salvar grupo esportivo: {}", e.getMessage(), e);
            throw new BusinessException("Erro interno ao realizar o cadastro do grupo esportivo.");
        }
    }

    // Demais métodos permanecem iguais...
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