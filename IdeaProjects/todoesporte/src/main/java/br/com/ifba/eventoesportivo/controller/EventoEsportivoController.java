package br.com.ifba.eventoesportivo.controller;

import br.com.ifba.eventoesportivo.dto.EventoEsportivoGetResponseDto;
import br.com.ifba.eventoesportivo.dto.EventoEsportivoPostRequestDto;
import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import br.com.ifba.eventoesportivo.service.EventoEsportivoIService;
import br.com.ifba.infrastructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/eventos-esportivos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventoEsportivoController {

    private final EventoEsportivoIService eventoEsportivoService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de um novo evento esportivo.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventoEsportivoGetResponseDto> save(
            @RequestBody @Valid EventoEsportivoPostRequestDto requestDto
    ) {

        EventoEsportivo eventoEsportivo = ObjectMapperUtil.map(
                requestDto,
                EventoEsportivo.class
        );

        EventoEsportivo eventoSalvo =
                eventoEsportivoService.save(eventoEsportivo);

        EventoEsportivoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        eventoSalvo,
                        EventoEsportivoGetResponseDto.class
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // =========================
    // GET BY ID
    // =========================
    @GetMapping(
            path = "/findById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventoEsportivoGetResponseDto> findById(
            @PathVariable Long id
    ) {

        EventoEsportivoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        eventoEsportivoService.findById(id),
                        EventoEsportivoGetResponseDto.class
                );

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    // =========================
    // GET ALL
    // =========================
    @GetMapping(
            path = "/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<EventoEsportivoGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<EventoEsportivoGetResponseDto> responseDto =
                eventoEsportivoService.findAll(pageable)
                        .map(evento ->
                                ObjectMapperUtil.map(
                                        evento,
                                        EventoEsportivoGetResponseDto.class
                                )
                        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    // =========================
    // PUT
    // =========================
    @PutMapping(
            path = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventoEsportivoGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid EventoEsportivoPostRequestDto dto
    ) {

        EventoEsportivo eventoEsportivo = ObjectMapperUtil.map(
                dto,
                EventoEsportivo.class
        );

        EventoEsportivo eventoAtualizado =
                eventoEsportivoService.update(id, eventoEsportivo);

        EventoEsportivoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        eventoAtualizado,
                        EventoEsportivoGetResponseDto.class
                );

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        eventoEsportivoService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}