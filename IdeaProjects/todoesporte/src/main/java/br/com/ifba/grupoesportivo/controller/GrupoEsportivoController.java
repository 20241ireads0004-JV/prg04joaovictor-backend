package br.com.ifba.grupoesportivo.controller;

import br.com.ifba.grupoesportivo.dto.GrupoEsportivoGetResponseDto;
import br.com.ifba.grupoesportivo.dto.GrupoEsportivoPostRequestDto;
import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import br.com.ifba.grupoesportivo.service.GrupoEsportivoIService;
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
@RequestMapping(path = "/grupos-esportivos")
@RequiredArgsConstructor
public class GrupoEsportivoController {

    private final GrupoEsportivoIService grupoEsportivoService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de um novo grupo esportivo.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GrupoEsportivoGetResponseDto> save(
            @RequestBody @Valid GrupoEsportivoPostRequestDto requestDto
    ) {

        GrupoEsportivo grupoEsportivo = ObjectMapperUtil.map(
                requestDto,
                GrupoEsportivo.class
        );

        GrupoEsportivo grupoSalvo =
                grupoEsportivoService.save(grupoEsportivo);

        GrupoEsportivoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        grupoSalvo,
                        GrupoEsportivoGetResponseDto.class
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
    public ResponseEntity<GrupoEsportivoGetResponseDto> findById(
            @PathVariable Long id
    ) {

        GrupoEsportivoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        grupoEsportivoService.findById(id),
                        GrupoEsportivoGetResponseDto.class
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
    public ResponseEntity<Page<GrupoEsportivoGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<GrupoEsportivoGetResponseDto> responseDto =
                grupoEsportivoService.findAll(pageable)
                        .map(grupo ->
                                ObjectMapperUtil.map(
                                        grupo,
                                        GrupoEsportivoGetResponseDto.class
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
    public ResponseEntity<GrupoEsportivoGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid GrupoEsportivoPostRequestDto dto
    ) {

        GrupoEsportivo grupoEsportivo = ObjectMapperUtil.map(
                dto,
                GrupoEsportivo.class
        );

        GrupoEsportivo grupoAtualizado =
                grupoEsportivoService.update(id, grupoEsportivo);

        GrupoEsportivoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        grupoAtualizado,
                        GrupoEsportivoGetResponseDto.class
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

        grupoEsportivoService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}