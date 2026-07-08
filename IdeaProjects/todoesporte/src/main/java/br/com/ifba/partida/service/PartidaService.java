package br.com.ifba.partida.service;

import br.com.ifba.infraestructure.exception.BusinessException;
import br.com.ifba.infraestructure.exception.ResourceNotFoundException;
import br.com.ifba.partida.entity.Partida;
import br.com.ifba.partida.repository.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartidaService implements PartidaIService {

    private static final Logger logger =
            LoggerFactory.getLogger(PartidaService.class);

    private final PartidaRepository partidaRepository;

    @Override
    @Transactional
    public Partida save(Partida partida) {

        logger.info(
                "[SERVICE] Cadastrando nova partida."
        );

        try {

            Partida partidaSalva =
                    partidaRepository.save(partida);

            logger.info(
                    "[SERVICE] Partida cadastrada com sucesso."
            );

            return partidaSalva;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar partida: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao cadastrar a partida."
            );
        }
    }

    @Override
    public Page<Partida> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todas as partidas."
        );

        return partidaRepository.findAll(pageable);
    }

    @Override
    public Partida findById(Long id) {

        return partidaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Partida não encontrada."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão da partida ID: {}",
                id
        );

        if (!partidaRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Partida não encontrada. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Partida não encontrada."
            );
        }

        partidaRepository.deleteById(id);

        logger.info(
                "[SERVICE] Partida removida com sucesso."
        );
    }

    @Override
    @Transactional
    public Partida update(Long id, Partida partida) {

        logger.info(
                "[SERVICE] Atualizando partida ID: {}",
                id
        );

        Partida partidaExistente =
                partidaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Partida não encontrada."
                                )
                        );

        partidaExistente.setData(
                partida.getData()
        );

        partidaExistente.setHorario(
                partida.getHorario()
        );

        partidaExistente.setPlacarA(
                partida.getPlacarA()
        );

        partidaExistente.setPlacarB(
                partida.getPlacarB()
        );

        partidaExistente.setStatus(
                partida.getStatus()
        );

        Partida partidaAtualizada =
                partidaRepository.save(partidaExistente);

        logger.info(
                "[SERVICE] Partida atualizada com sucesso."
        );

        return partidaAtualizada;
    }

}