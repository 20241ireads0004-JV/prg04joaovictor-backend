package br.com.ifba.sorteio.service;

import br.com.ifba.infraestructure.exception.BusinessException;
import br.com.ifba.infraestructure.exception.ResourceNotFoundException;
import br.com.ifba.sorteio.entity.Sorteio;
import br.com.ifba.sorteio.repository.SorteioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SorteioService implements SorteioIService {

    private static final Logger logger =
            LoggerFactory.getLogger(SorteioService.class);

    private final SorteioRepository sorteioRepository;

    @Override
    @Transactional
    public Sorteio save(Sorteio sorteio) {

        logger.info(
                "[SERVICE] Cadastrando novo sorteio."
        );

        try {

            Sorteio sorteioSalvo =
                    sorteioRepository.save(sorteio);

            logger.info(
                    "[SERVICE] Sorteio cadastrado com sucesso."
            );

            return sorteioSalvo;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar sorteio: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao cadastrar o sorteio."
            );
        }
    }

    @Override
    public Page<Sorteio> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todos os sorteios."
        );

        return sorteioRepository.findAll(pageable);
    }

    @Override
    public Sorteio findById(Long id) {

        return sorteioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sorteio não encontrado."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão do sorteio ID: {}",
                id
        );

        if (!sorteioRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Sorteio não encontrado. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Sorteio não encontrado."
            );
        }

        sorteioRepository.deleteById(id);

        logger.info(
                "[SERVICE] Sorteio removido com sucesso."
        );
    }

    @Override
    @Transactional
    public Sorteio update(Long id, Sorteio sorteio) {

        logger.info(
                "[SERVICE] Atualizando sorteio ID: {}",
                id
        );

        Sorteio sorteioExistente =
                sorteioRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Sorteio não encontrado."
                                )
                        );

        sorteioExistente.setTipo(
                sorteio.getTipo()
        );

        sorteioExistente.setData(
                sorteio.getData()
        );

        Sorteio sorteioAtualizado =
                sorteioRepository.save(sorteioExistente);

        logger.info(
                "[SERVICE] Sorteio atualizado com sucesso."
        );

        return sorteioAtualizado;
    }

}