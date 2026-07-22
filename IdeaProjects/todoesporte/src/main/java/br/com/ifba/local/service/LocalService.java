package br.com.ifba.local.service;

import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.local.entity.Local;
import br.com.ifba.local.repository.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocalService implements LocalIService {

    private static final Logger logger =
            LoggerFactory.getLogger(LocalService.class);

    private final LocalRepository localRepository;

    @Override
    @Transactional
    public Local save(Local local) {

        logger.info(
                "[SERVICE] Cadastrando novo local: {}",
                local.getNome()
        );

        validarNome(local);

        try {

            Local localSalvo =
                    localRepository.save(local);

            logger.info(
                    "[SERVICE] Local cadastrado com sucesso."
            );

            return localSalvo;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar local: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao cadastrar o local."
            );
        }
    }

    @Override
    public Page<Local> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todos os locais."
        );

        return localRepository.findAll(pageable);
    }

    @Override
    public Local findById(Long id) {

        return localRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Local não encontrado."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão do local ID: {}",
                id
        );

        if (!localRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Local não encontrado. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Local não encontrado."
            );
        }

        localRepository.deleteById(id);

        logger.info(
                "[SERVICE] Local removido com sucesso."
        );
    }

    @Override
    @Transactional
    public Local update(Long id, Local local) {

        logger.info(
                "[SERVICE] Atualizando local ID: {}",
                id
        );

        Local localExistente =
                localRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Local não encontrado."
                                )
                        );

        if (localRepository.existsByNomeAndIdNot(
                local.getNome(),
                id
        )) {

            logger.warn(
                    "[SERVICE] Nome do local já cadastrado: {}",
                    local.getNome()
            );

            throw new BusinessException(
                    "Já existe um local cadastrado com esse nome."
            );
        }

        localExistente.setNome(local.getNome());
        localExistente.setEndereco(local.getEndereco());
        localExistente.setCidade(local.getCidade());
        localExistente.setBairro(local.getBairro());

        Local localAtualizado =
                localRepository.save(localExistente);

        logger.info(
                "[SERVICE] Local atualizado com sucesso."
        );

        return localAtualizado;
    }

    /*
     * Valida se já existe um local
     * cadastrado com o mesmo nome.
     */
    private void validarNome(Local local) {

        if (localRepository.existsByNome(
                local.getNome()
        )) {

            logger.warn(
                    "[SERVICE] Nome do local já cadastrado: {}",
                    local.getNome()
            );

            throw new BusinessException(
                    "Já existe um local cadastrado com esse nome."
            );
        }
    }

}