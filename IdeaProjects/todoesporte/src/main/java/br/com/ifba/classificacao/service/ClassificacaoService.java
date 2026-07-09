package br.com.ifba.classificacao.service;

import br.com.ifba.classificacao.entity.Classificacao;
import br.com.ifba.classificacao.repository.ClassificacaoRepository;
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
public class ClassificacaoService implements ClassificacaoIService {

    private static final Logger logger =
            LoggerFactory.getLogger(ClassificacaoService.class);

    private final ClassificacaoRepository classificacaoRepository;

    @Override
    @Transactional
    public Classificacao save(Classificacao classificacao) {

        logger.info(
                "[SERVICE] Cadastrando nova classificação."
        );

        try {

            Classificacao classificacaoSalva =
                    classificacaoRepository.save(classificacao);

            logger.info(
                    "[SERVICE] Classificação cadastrada com sucesso."
            );

            return classificacaoSalva;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar classificação: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao cadastrar a classificação."
            );
        }
    }

    @Override
    public Page<Classificacao> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todas as classificações."
        );

        return classificacaoRepository.findAll(pageable);
    }

    @Override
    public Classificacao findById(Long id) {

        return classificacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Classificação não encontrada."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão da classificação ID: {}",
                id
        );

        if (!classificacaoRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Classificação não encontrada. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Classificação não encontrada."
            );
        }

        classificacaoRepository.deleteById(id);

        logger.info(
                "[SERVICE] Classificação removida com sucesso."
        );
    }

    @Override
    @Transactional
    public Classificacao update(
            Long id,
            Classificacao classificacao
    ) {

        logger.info(
                "[SERVICE] Atualizando classificação ID: {}",
                id
        );

        Classificacao classificacaoExistente =
                classificacaoRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Classificação não encontrada."
                                )
                        );

        classificacaoExistente.setPosicao(
                classificacao.getPosicao()
        );

        classificacaoExistente.setPontuacao(
                classificacao.getPontuacao()
        );

        classificacaoExistente.setGolsMarcados(
                classificacao.getGolsMarcados()
        );

        classificacaoExistente.setGolsSofridos(
                classificacao.getGolsSofridos()
        );

        Classificacao classificacaoAtualizada =
                classificacaoRepository.save(classificacaoExistente);

        logger.info(
                "[SERVICE] Classificação atualizada com sucesso."
        );

        return classificacaoAtualizada;
    }

}