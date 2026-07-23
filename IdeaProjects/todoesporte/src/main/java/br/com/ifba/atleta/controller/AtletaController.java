package br.com.ifba.atleta.controller;

import br.com.ifba.atleta.dto.AtletaGetResponseDto;
import br.com.ifba.atleta.dto.AtletaPostRequestDto;
import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.atleta.service.AtletaIService;
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
@RequestMapping(path = "/atletas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AtletaController {

    private final AtletaIService atletaService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de um novo atleta.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AtletaGetResponseDto> save(
            @RequestBody @Valid AtletaPostRequestDto requestDto
    ) {

        Atleta atleta = ObjectMapperUtil.map(
                requestDto,
                Atleta.class
        );

        Atleta atletaSalvo =
                atletaService.save(atleta);

        AtletaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        atletaSalvo,
                        AtletaGetResponseDto.class
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
    public ResponseEntity<AtletaGetResponseDto> findById(
            @PathVariable Long id
    ) {

        AtletaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        atletaService.findById(id),
                        AtletaGetResponseDto.class
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
    public ResponseEntity<Page<AtletaGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<AtletaGetResponseDto> responseDto =
                atletaService.findAll(pageable)
                        .map(atleta ->
                                ObjectMapperUtil.map(
                                        atleta,
                                        AtletaGetResponseDto.class
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
    public ResponseEntity<AtletaGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid AtletaPostRequestDto dto
    ) {

        Atleta atleta = ObjectMapperUtil.map(
                dto,
                Atleta.class
        );

        Atleta atletaAtualizado =
                atletaService.update(id, atleta);

        AtletaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        atletaAtualizado,
                        AtletaGetResponseDto.class
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

        atletaService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}