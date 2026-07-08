package br.com.ifba.campeonato.controller;

import br.com.ifba.campeonato.dto.CampeonatoGetResponseDto;
import br.com.ifba.campeonato.dto.CampeonatoPostRequestDto;
import br.com.ifba.campeonato.entity.Campeonato;
import br.com.ifba.campeonato.service.CampeonatoIService;
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
@RequestMapping(path = "/campeonatos")
@RequiredArgsConstructor
public class CampeonatoController {

    private final CampeonatoIService campeonatoService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de um novo campeonato.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CampeonatoGetResponseDto> save(
            @RequestBody @Valid CampeonatoPostRequestDto requestDto
    ) {

        Campeonato campeonato = ObjectMapperUtil.map(
                requestDto,
                Campeonato.class
        );

        Campeonato campeonatoSalvo =
                campeonatoService.save(campeonato);

        CampeonatoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        campeonatoSalvo,
                        CampeonatoGetResponseDto.class
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
    public ResponseEntity<CampeonatoGetResponseDto> findById(
            @PathVariable Long id
    ) {

        CampeonatoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        campeonatoService.findById(id),
                        CampeonatoGetResponseDto.class
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
    public ResponseEntity<Page<CampeonatoGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<CampeonatoGetResponseDto> responseDto =
                campeonatoService.findAll(pageable)
                        .map(campeonato ->
                                ObjectMapperUtil.map(
                                        campeonato,
                                        CampeonatoGetResponseDto.class
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
    public ResponseEntity<CampeonatoGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid CampeonatoPostRequestDto dto
    ) {

        Campeonato campeonato = ObjectMapperUtil.map(
                dto,
                Campeonato.class
        );

        Campeonato campeonatoAtualizado =
                campeonatoService.update(id, campeonato);

        CampeonatoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        campeonatoAtualizado,
                        CampeonatoGetResponseDto.class
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

        campeonatoService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}