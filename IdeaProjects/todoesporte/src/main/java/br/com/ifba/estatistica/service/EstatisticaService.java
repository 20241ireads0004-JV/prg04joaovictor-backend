package br.com.ifba.estatistica.service;

import br.com.ifba.estatistica.entity.Estatistica;
import br.com.ifba.estatistica.repository.EstatisticaRepository;
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
public class EstatisticaService implements EstatisticaIService {

    // Logger utilizado para registrar eventos da aplicação
    private static final Logger logger =
            LoggerFactory.getLogger(EstatisticaService.class);

    // Injeção de dependência automática pelo Lombok
    private final EstatisticaRepository estatisticaRepository;

    @Override
    @Transactional
    public Estatistica save(Estatistica estatistica) {

        logger.info(
                "[SERVICE] Iniciando cadastro da estatística."
        );

        try {

            Estatistica estatisticaSalva =
                    estatisticaRepository.save(estatistica);

            logger.info(
                    "[SERVICE] Estatística cadastrada com sucesso."
            );

            return estatisticaSalva;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar estatística: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao realizar cadastro da estatística."
            );
        }
    }

    @Override
    public Page<Estatistica> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todas as estatísticas."
        );

        return estatisticaRepository.findAll(pageable);
    }

    @Override
    public Estatistica findById(Long id) {

        logger.info(
                "[SERVICE] Buscando estatística ID: {}",
                id
        );

        return estatisticaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Estatística não encontrada."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão da estatística ID: {}",
                id
        );

        if (!estatisticaRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Estatística não encontrada. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Estatística não encontrada."
            );
        }

        estatisticaRepository.deleteById(id);

        logger.info(
                "[SERVICE] Estatística deletada com sucesso."
        );
    }

    @Override
    @Transactional
    public Estatistica update(Long id, Estatistica estatistica) {

        logger.info(
                "[SERVICE] Atualizando estatística ID: {}",
                id
        );

        Estatistica estatisticaExistente =
                estatisticaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Estatística não encontrada."
                                )
                        );

        estatisticaExistente.setGols(
                estatistica.getGols()
        );

        estatisticaExistente.setAssistencias(
                estatistica.getAssistencias()
        );

        estatisticaExistente.setVitorias(
                estatistica.getVitorias()
        );

        estatisticaExistente.setDerrotas(
                estatistica.getDerrotas()
        );

        estatisticaExistente.setEmpates(
                estatistica.getEmpates()
        );

        estatisticaExistente.setPontos(
                estatistica.getPontos()
        );

        Estatistica estatisticaAtualizada =
                estatisticaRepository.save(estatisticaExistente);

        logger.info(
                "[SERVICE] Estatística atualizada com sucesso."
        );

        return estatisticaAtualizada;
    }

}