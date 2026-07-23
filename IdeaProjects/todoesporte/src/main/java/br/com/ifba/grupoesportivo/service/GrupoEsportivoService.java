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

    @Override
    @Transactional
    public GrupoEsportivo save(GrupoEsportivo grupoEsportivo, Long usuarioId, String esporteNome) {
        logger.info("[SERVICE] Criando grupo '{}' pelo utilizador ID {}", grupoEsportivo.getNome(), usuarioId);

        // 1. Valida se já existe um grupo com o mesmo nome
        validarNomeUnico(grupoEsportivo);

        // 2. Busca o Esporte na base de dados pelo NOME selecionado
        Esporte esporte = esporteRepository.findByNome(esporteNome)
                .orElseThrow(() -> new ResourceNotFoundException("Esporte '" + esporteNome + "' não encontrado."));

        // 3. Tenta encontrar o Administrador pelo ID.
        // Se não encontrar na tabela de Administradores, copia os dados completos do Atleta
        Administrador admin = administradorRepository.findById(usuarioId)
                .orElseGet(() -> {
                    logger.info("[SERVICE] Utilizador ID {} não é administrador. Registando perfil de Administrador...", usuarioId);

                    Atleta atleta = atletaRepository.findById(usuarioId)
                            .orElseThrow(() -> new ResourceNotFoundException("Utilizador/Atleta não encontrado com o ID: " + usuarioId));

                    // Instancia novo Administrador preenchendo TODOS os campos herdados obrigatorios de Usuario
                    Administrador novoAdmin = new Administrador();
                    novoAdmin.setNome(atleta.getNome());
                    novoAdmin.setEmail(atleta.getEmail());
                    novoAdmin.setLogin(atleta.getLogin());
                    novoAdmin.setSenha(atleta.getSenha());
                    novoAdmin.setTelefone(atleta.getTelefone());
                    novoAdmin.setDataCadastro(atleta.getDataCadastro() != null ? atleta.getDataCadastro() : LocalDate.now());
                    novoAdmin.setStatus(atleta.getStatus() != null ? atleta.getStatus() : true);

                    try {
                        Administrador adminSalvo = administradorRepository.save(novoAdmin);
                        logger.info("[SERVICE] Perfil de Administrador (ID: {}) gerado com sucesso para o usuário.", adminSalvo.getId());
                        return adminSalvo;
                    } catch (Exception ex) {
                        logger.error("[SERVICE] Erro ao persistir perfil de Administrador: {}", ex.getMessage(), ex);
                        throw new BusinessException("Erro ao associar perfil de Administrador ao usuário.");
                    }
                });

        // 4. Associa o Administrador e o Esporte ao novo grupo
        grupoEsportivo.setAdministrador(admin);
        grupoEsportivo.setEsporte(esporte);

        // 5. Define a data de criação do grupo caso não venha informada
        if (grupoEsportivo.getDataCriacao() == null) {
            grupoEsportivo.setDataCriacao(LocalDate.now());
        }

        try {
            // 6. Guarda o grupo esportivo no PostgreSQL
            GrupoEsportivo grupoSalvo = grupoEsportivoRepository.save(grupoEsportivo);
            logger.info("[SERVICE] Grupo '{}' salvo com sucesso! Administrador responsável: ID {}", grupoSalvo.getNome(), admin.getId());
            return grupoSalvo;
        } catch (Exception e) {
            logger.error("[SERVICE] Erro ao salvar grupo esportivo: {}", e.getMessage(), e);
            throw new BusinessException("Erro interno ao realizar o cadastro do grupo esportivo.");
        }
    }

    // REGRA: Atleta solicita entrada no grupo
    @Transactional
    public void solicitarEntrada(Long grupoId, Long atletaId) {
        logger.info("[SERVICE] Atleta ID {} solicitando entrada no Grupo ID {}", atletaId, grupoId);

        GrupoEsportivo grupo = grupoEsportivoRepository.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo esportivo não encontrado."));

        Atleta atleta = atletaRepository.findById(atletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));

        // Se o atleta não estiver na lista de pendentes e não for membro, adiciona a solicitação
        if (!grupo.getSolicitacoesPendentes().contains(atleta) && !grupo.getAtletas().contains(atleta)) {
            grupo.getSolicitacoesPendentes().add(atleta);
            grupoEsportivoRepository.save(grupo);
        }
    }

    // REGRA: Administrador aceita solicitação de atleta
    @Transactional
    public void aceitarAtleta(Long grupoId, Long atletaId, Long adminIdLogado) {
        logger.info("[SERVICE] Admin ID {} tentando aprovar Atleta ID {} no Grupo ID {}", adminIdLogado, atletaId, grupoId);

        GrupoEsportivo grupo = grupoEsportivoRepository.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo esportivo não encontrado."));

        // VALIDAÇÃO DE SEGURANÇA: Apenas o Administrador do grupo pode aceitar
        if (!grupo.getAdministrador().getId().equals(adminIdLogado)) {
            logger.warn("[SERVICE] Negado: Usuário ID {} não é o admin do grupo {}", adminIdLogado, grupoId);
            throw new BusinessException("Apenas o Administrador deste grupo pode aceitar atletas.");
        }

        Atleta atleta = atletaRepository.findById(atletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));

        // Remove das solicitações e adiciona aos membros do grupo
        grupo.getSolicitacoesPendentes().remove(atleta);
        if (!grupo.getAtletas().contains(atleta)) {
            grupo.getAtletas().add(atleta);
        }

        grupoEsportivoRepository.save(grupo);
        logger.info("[SERVICE] Atleta ID {} aceito com sucesso no Grupo ID {}", atletaId, grupoId);
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