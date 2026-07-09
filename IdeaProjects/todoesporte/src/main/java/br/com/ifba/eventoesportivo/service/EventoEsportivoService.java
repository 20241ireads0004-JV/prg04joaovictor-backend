package br.com.ifba.eventoesportivo.service;

import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import br.com.ifba.eventoesportivo.repository.EventoEsportivoRepository;
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
public class EventoEsportivoService implements EventoEsportivoIService {

    private static final Logger logger =
            LoggerFactory.getLogger(EventoEsportivoService.class);

    private final EventoEsportivoRepository eventoEsportivoRepository;

    @Override
    @Transactional
    public EventoEsportivo save(EventoEsportivo eventoEsportivo) {

        logger.info(
                "[SERVICE] Cadastrando novo evento esportivo."
        );

        try {

            EventoEsportivo eventoSalvo =
                    eventoEsportivoRepository.save(eventoEsportivo);

            logger.info(
                    "[SERVICE] Evento esportivo cadastrado com sucesso."
            );

            return eventoSalvo;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar evento esportivo: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao cadastrar o evento esportivo."
            );
        }

    }

    @Override
    public Page<EventoEsportivo> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todos os eventos esportivos."
        );

        return eventoEsportivoRepository.findAll(pageable);
    }

    @Override
    public EventoEsportivo findById(Long id) {

        return eventoEsportivoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Evento esportivo não encontrado."
                        )
                );

    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão do evento esportivo ID: {}",
                id
        );

        if (!eventoEsportivoRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Evento esportivo não encontrado. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Evento esportivo não encontrado."
            );
        }

        eventoEsportivoRepository.deleteById(id);

        logger.info(
                "[SERVICE] Evento esportivo removido com sucesso."
        );

    }

    @Override
    @Transactional
    public EventoEsportivo update(
            Long id,
            EventoEsportivo eventoEsportivo
    ) {

        logger.info(
                "[SERVICE] Atualizando evento esportivo ID: {}",
                id
        );

        EventoEsportivo eventoExistente =
                eventoEsportivoRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Evento esportivo não encontrado."
                                )
                        );

        eventoExistente.setData(
                eventoEsportivo.getData()
        );

        eventoExistente.setHorario(
                eventoEsportivo.getHorario()
        );

        eventoExistente.setVagas(
                eventoEsportivo.getVagas()
        );

        eventoExistente.setDescricao(
                eventoEsportivo.getDescricao()
        );

        EventoEsportivo eventoAtualizado =
                eventoEsportivoRepository.save(eventoExistente);

        logger.info(
                "[SERVICE] Evento esportivo atualizado com sucesso."
        );

        return eventoAtualizado;

    }

}