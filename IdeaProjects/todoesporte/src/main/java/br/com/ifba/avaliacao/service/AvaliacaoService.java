package br.com.ifba.avaliacao.service;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.avaliacao.repository.AvaliacaoRepository;
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
public class AvaliacaoService implements AvaliacaoIService {

    // Logger utilizado para registrar eventos da aplicação
    private static final Logger logger =
            LoggerFactory.getLogger(AvaliacaoService.class);

    // Injeção de dependência automática pelo Lombok
    private final AvaliacaoRepository avaliacaoRepository;

    @Override
    @Transactional
    public Avaliacao save(Avaliacao avaliacao) {

        logger.info(
                "[SERVICE] Iniciando cadastro da avaliação."
        );

        try {

            Avaliacao avaliacaoSalva =
                    avaliacaoRepository.save(avaliacao);

            logger.info(
                    "[SERVICE] Avaliação cadastrada com sucesso."
            );

            return avaliacaoSalva;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar avaliação: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao realizar cadastro da avaliação."
            );
        }
    }

    @Override
    public Page<Avaliacao> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todas as avaliações."
        );

        return avaliacaoRepository.findAll(pageable);
    }

    @Override
    public Avaliacao findById(Long id) {

        logger.info(
                "[SERVICE] Buscando avaliação ID: {}",
                id
        );

        return avaliacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Avaliação não encontrada."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão da avaliação ID: {}",
                id
        );

        if (!avaliacaoRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Avaliação não encontrada. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Avaliação não encontrada."
            );
        }

        avaliacaoRepository.deleteById(id);

        logger.info(
                "[SERVICE] Avaliação deletada com sucesso."
        );
    }

    @Override
    @Transactional
    public Avaliacao update(Long id, Avaliacao avaliacao) {

        logger.info(
                "[SERVICE] Atualizando avaliação ID: {}",
                id
        );

        Avaliacao avaliacaoExistente =
                avaliacaoRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Avaliação não encontrada."
                                )
                        );

        avaliacaoExistente.setNota(
                avaliacao.getNota()
        );

        avaliacaoExistente.setComentario(
                avaliacao.getComentario()
        );

        avaliacaoExistente.setData(
                avaliacao.getData()
        );

        Avaliacao avaliacaoAtualizada =
                avaliacaoRepository.save(avaliacaoExistente);

        logger.info(
                "[SERVICE] Avaliação atualizada com sucesso."
        );

        return avaliacaoAtualizada;
    }

}