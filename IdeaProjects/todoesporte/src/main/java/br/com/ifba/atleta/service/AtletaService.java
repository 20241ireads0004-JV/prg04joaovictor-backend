package br.com.ifba.atleta.service;

import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.atleta.repository.AtletaRepository;
import br.com.ifba.infraestructure.exception.BusinessException;
import br.com.ifba.infraestructure.exception.ResourceNotFoundException;
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

    @Override
    @Transactional
    public Atleta save(Atleta atleta) {
        logger.info("[SERVICE] Iniciando cadastro do atleta: {}", atleta.getLogin());

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

    @Override
    public Page<Atleta> findAll(Pageable pageable) {
        logger.info("[SERVICE] Buscando todos os atletas.");
        return atletaRepository.findAll(pageable);
    }

    @Override
    public Atleta findById(Long id) {
        return atletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));
    }

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

    @Override
    @Transactional
    public Atleta update(Long id, Atleta atleta) {
        logger.info("[SERVICE] Atualizando atleta ID: {}", id);

        Atleta atletaExistente = atletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));

        if (atletaRepository.existsByEmailAndIdNot(atleta.getEmail(), id)) {
            logger.warn("[SERVICE] Email já cadastrado: {}", atleta.getEmail());
            throw new BusinessException("Email já cadastrado.");
        }

        if (atletaRepository.existsByLoginAndIdNot(atleta.getLogin(), id)) {
            logger.warn("[SERVICE] Login já cadastrado: {}", atleta.getLogin());
            throw new BusinessException("Login já cadastrado.");
        }

        // Atualização de campos básicos
        atletaExistente.setNome(atleta.getNome());
        atletaExistente.setEmail(atleta.getEmail());
        atletaExistente.setLogin(atleta.getLogin());
        atletaExistente.setSenha(atleta.getSenha());
        atletaExistente.setTelefone(atleta.getTelefone());
        atletaExistente.setStatus(atleta.getStatus());

        // Atualização dos relacionamentos existentes
        atletaExistente.setEquipes(atleta.getEquipes());
        atletaExistente.setGruposEsportivos(atleta.getGruposEsportivos());

        Atleta atletaAtualizado = atletaRepository.save(atletaExistente);
        logger.info("[SERVICE] Atleta atualizado com sucesso.");

        return atletaAtualizado;
    }

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