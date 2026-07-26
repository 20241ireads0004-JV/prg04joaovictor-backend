package br.com.ifba.eventoesportivo.service;

import br.com.ifba.eventoesportivo.dto.EventoEsportivoPostRequestDto;
import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import br.com.ifba.eventoesportivo.repository.EventoEsportivoRepository;
import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import br.com.ifba.grupoesportivo.repository.GrupoEsportivoRepository;
import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.infrastructure.util.ObjectMapperUtil;
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
public class EventoEsportivoService implements EventoEsportivoIService {

    private static final Logger logger =
            LoggerFactory.getLogger(EventoEsportivoService.class);

    private final EventoEsportivoRepository eventoEsportivoRepository;

    // Injeção dos repositórios necessários para buscar o Local e o Grupo
    private final LocalRepository localRepository;
    private final GrupoEsportivoRepository grupoEsportivoRepository;

    @Override
    @Transactional
    public EventoEsportivo save(EventoEsportivoPostRequestDto dto) {

        logger.info("[SERVICE] Cadastrando novo evento esportivo.");

        try {
            // 1. Mapeia os campos simples do DTO para a entidade EventoEsportivo
            EventoEsportivo eventoEsportivo = ObjectMapperUtil.map(dto, EventoEsportivo.class);

            // 2. Associa o Local buscando pelo localId recebido no DTO
            if (dto.getLocalId() != null) {
                Local local = localRepository.findById(dto.getLocalId())
                        .orElseThrow(() -> new ResourceNotFoundException("Local não encontrado com o ID: " + dto.getLocalId()));
                eventoEsportivo.setLocal(local);
            }

            // 3. Associa o Grupo Esportivo buscando pelo grupoId recebido no DTO
            if (dto.getGrupoId() != null) {
                GrupoEsportivo grupo = grupoEsportivoRepository.findById(dto.getGrupoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Grupo Esportivo não encontrado com o ID: " + dto.getGrupoId()));
                eventoEsportivo.setGrupoEsportivo(grupo);
            }

            // 4. Salva o evento com os relacionamentos devidamente preenchidos
            EventoEsportivo eventoSalvo = eventoEsportivoRepository.save(eventoEsportivo);

            logger.info("[SERVICE] Evento esportivo cadastrado com sucesso.");

            return eventoSalvo;

        } catch (ResourceNotFoundException e) {
            logger.error("[SERVICE] Recurso não encontrado ao salvar evento: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("[SERVICE] Erro ao salvar evento esportivo: {}", e.getMessage());
            throw new BusinessException("Erro interno ao cadastrar o evento esportivo.");
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
    public EventoEsportivo update(Long id, EventoEsportivoPostRequestDto dto) {

        logger.info("[SERVICE] Atualizando evento esportivo ID: {}", id);

        EventoEsportivo eventoExistente = eventoEsportivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento esportivo não encontrado."));

        // Atualização dos atributos simples
        eventoExistente.setData(dto.getData());
        eventoExistente.setHorario(dto.getHorario());
        eventoExistente.setVagas(dto.getVagas());
        eventoExistente.setDescricao(dto.getDescricao());

        // Atualização dos relacionamentos (Local e Grupo), se informados
        if (dto.getLocalId() != null) {
            Local local = localRepository.findById(dto.getLocalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Local não encontrado com o ID: " + dto.getLocalId()));
            eventoExistente.setLocal(local);
        }

        if (dto.getGrupoId() != null) {
            GrupoEsportivo grupo = grupoEsportivoRepository.findById(dto.getGrupoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Grupo Esportivo não encontrado com o ID: " + dto.getGrupoId()));
            eventoExistente.setGrupoEsportivo(grupo);
        }

        EventoEsportivo eventoAtualizado = eventoEsportivoRepository.save(eventoExistente);

        logger.info("[SERVICE] Evento esportivo atualizado com sucesso.");

        return eventoAtualizado;
    }

}