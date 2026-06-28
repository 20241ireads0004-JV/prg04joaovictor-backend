package br.com.ifba.estatistica.controller;

import br.com.ifba.estatistica.dto.EstatisticaGetResponseDto;
import br.com.ifba.estatistica.dto.EstatisticaPostRequestDto;
import br.com.ifba.estatistica.entity.Estatistica;
import br.com.ifba.estatistica.service.EstatisticaIService;
import br.com.ifba.infraestructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/estatisticas")
@RequiredArgsConstructor
public class EstatisticaController {

    private final EstatisticaIService estatisticaService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de uma nova estatística.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EstatisticaGetResponseDto> save(
            @RequestBody @Valid EstatisticaPostRequestDto requestDto
    ) {

        Estatistica estatistica = ObjectMapperUtil.map(
                requestDto,
                Estatistica.class
        );

        Estatistica estatisticaSalva =
                estatisticaService.save(estatistica);

        EstatisticaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        estatisticaSalva,
                        EstatisticaGetResponseDto.class
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
    public ResponseEntity<EstatisticaGetResponseDto> findById(
            @PathVariable Long id
    ) {

        EstatisticaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        estatisticaService.findById(id),
                        EstatisticaGetResponseDto.class
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
    public ResponseEntity<Page<EstatisticaGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<EstatisticaGetResponseDto> responseDto =
                estatisticaService.findAll(pageable)
                        .map(estatistica ->
                                ObjectMapperUtil.map(
                                        estatistica,
                                        EstatisticaGetResponseDto.class
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
    public ResponseEntity<EstatisticaGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid EstatisticaPostRequestDto dto
    ) {

        Estatistica estatistica = ObjectMapperUtil.map(
                dto,
                Estatistica.class
        );

        Estatistica estatisticaAtualizada =
                estatisticaService.update(id, estatistica);

        EstatisticaGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        estatisticaAtualizada,
                        EstatisticaGetResponseDto.class
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

        estatisticaService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}