package br.com.ifba.atleta.service;

import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.atleta.repository.AtletaRepository;
import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtletaService implements AtletaIService {

    private static final Logger logger = LoggerFactory.getLogger(AtletaService.class);

    private final AtletaRepository atletaRepository;

    /**
     * Cadastra um novo atleta no banco de dados.
     */
    @Override
    @Transactional
    public Atleta save(Atleta atleta) {
        logger.info("[SERVICE] Iniciando cadastro do atleta: {}", atleta.getLogin());

        // Valida se o email ou o login já existem no banco
        validarDadosUnicos(atleta);

        try {
            Atleta atletaSalvo = atletaRepository.save(atleta);
            logger.info("[SERVICE] Atleta cadastrado com sucesso.");
            return atletaSalvo;
        } catch (Exception e) {
            logger.error("[SERVICE] Erro ao salvar atleta: {}", e.getMessage());
            throw new BusinessException("Erro interno ao realizar cadastro do atleta.");
        }
    }

    /**
     * Retorna a lista paginada de todos os atletas.
     */
    @Override
    public Page<Atleta> findAll(Pageable pageable) {
        logger.info("[SERVICE] Buscando todos os atletas.");
        return atletaRepository.findAll(pageable);
    }

    /**
     * Busca um atleta específico pelo seu ID.
     */
    @Override
    public Atleta findById(Long id) {
        return atletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));
    }

    /**
     * Remove um atleta pelo ID informado.
     */
    @Override
    public void delete(Long id) {
        logger.info("[SERVICE] Solicitação de exclusão do atleta ID: {}", id);

        if (!atletaRepository.existsById(id)) {
            logger.error("[SERVICE] Atleta não encontrado. ID: {}", id);
            throw new ResourceNotFoundException("Atleta não encontrado.");
        }

        atletaRepository.deleteById(id);
        logger.info("[SERVICE] Atleta deletado com sucesso.");
    }

    /**
     * Atualiza as informações do atleta existente.
     */
    @Override
    @Transactional
    public Atleta update(Long id, Atleta atleta) {
        logger.info("[SERVICE] Atualizando atleta ID: {}", id);

        Atleta atletaExistente = atletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));

        // Validação: Garante que o email não pertence a outro atleta
        if (atletaRepository.existsByEmailAndIdNot(atleta.getEmail(), id)) {
            logger.warn("[SERVICE] Email já cadastrado por outro usuário: {}", atleta.getEmail());
            throw new BusinessException("Email já cadastrado.");
        }

        // Validação: Garante que o login não pertence a outro atleta
        if (atletaRepository.existsByLoginAndIdNot(atleta.getLogin(), id)) {
            logger.warn("[SERVICE] Login já cadastrado por outro usuário: {}", atleta.getLogin());
            throw new BusinessException("Login já cadastrado.");
        }

        // Atualização dos campos básicos
        atletaExistente.setNome(atleta.getNome());
        atletaExistente.setEmail(atleta.getEmail());
        atletaExistente.setLogin(atleta.getLogin());
        atletaExistente.setTelefone(atleta.getTelefone());
        atletaExistente.setStatus(atleta.getStatus());

        // Atualiza a senha somente se uma nova senha for fornecida
        if (atleta.getSenha() != null && !atleta.getSenha().isBlank()) {
            atletaExistente.setSenha(atleta.getSenha());
        }

        // Atualização segura dos relacionamentos (mantenha a coleção do Hibernate)
        if (atleta.getEquipes() != null) {
            atletaExistente.getEquipes().clear();
            atletaExistente.getEquipes().addAll(atleta.getEquipes());
        }

        if (atleta.getGruposEsportivos() != null) {
            atletaExistente.getGruposEsportivos().clear();
            atletaExistente.getGruposEsportivos().addAll(atleta.getGruposEsportivos());
        }

        Atleta atletaAtualizado = atletaRepository.save(atletaExistente);
        logger.info("[SERVICE] Atleta atualizado com sucesso.");

        return atletaAtualizado;
    }

    /**
     * Método privado auxiliar para verificar unicidade de dados no cadastro.
     */
    private void validarDadosUnicos(Atleta atleta) {
        if (atletaRepository.existsByEmail(atleta.getEmail())) {
            logger.warn("[SERVICE] Email já cadastrado: {}", atleta.getEmail());
            throw new BusinessException("Email já cadastrado.");
        }

        if (atletaRepository.existsByLogin(atleta.getLogin())) {
            logger.warn("[SERVICE] Login já cadastrado: {}", atleta.getLogin());
            throw new BusinessException("Login já cadastrado.");
        }
    }
}