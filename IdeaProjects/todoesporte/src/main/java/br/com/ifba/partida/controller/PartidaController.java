package br.com.ifba.partida.controller;

import br.com.ifba.infraestructure.util.ObjectMapperUtil;
import br.com.ifba.partida.dto.PartidaGetResponseDto;
import br.com.ifba.partida.dto.PartidaPostRequestDto;
import br.com.ifba.partida.entity.Partida;
import br.com.ifba.partida.service.PartidaIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/partidas")
@RequiredArgsConstructor
public class PartidaController {

    private final PartidaIService partidaService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de uma nova partida.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PartidaGetResponseDto> save(
            @RequestBody @Valid PartidaPostRequestDto requestDto
    ) {

        Partida partida = ObjectMapperUtil.map(
                requestDto,
                Partida.class
        );

        Partida partidaSalva = partidaService.save(partida);

        PartidaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        partidaSalva,
                        PartidaGetResponseDto.class
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
    public ResponseEntity<PartidaGetResponseDto> findById(
            @PathVariable Long id
    ) {

        PartidaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        partidaService.findById(id),
                        PartidaGetResponseDto.class
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
    public ResponseEntity<Page<PartidaGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<PartidaGetResponseDto> responseDto =
                partidaService.findAll(pageable)
                        .map(partida ->
                                ObjectMapperUtil.map(
                                        partida,
                                        PartidaGetResponseDto.class
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
    public ResponseEntity<PartidaGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid PartidaPostRequestDto dto
    ) {

        Partida partida = ObjectMapperUtil.map(
                dto,
                Partida.class
        );

        Partida partidaAtualizada =
                partidaService.update(id, partida);

        PartidaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        partidaAtualizada,
                        PartidaGetResponseDto.class
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

        partidaService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}