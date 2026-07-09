package br.com.ifba.sorteio.controller;

import br.com.ifba.infraestructure.util.ObjectMapperUtil;
import br.com.ifba.sorteio.dto.SorteioGetResponseDto;
import br.com.ifba.sorteio.dto.SorteioPostRequestDto;
import br.com.ifba.sorteio.entity.Sorteio;
import br.com.ifba.sorteio.service.SorteioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/sorteios")
@RequiredArgsConstructor
public class SorteioController {

    private final SorteioIService sorteioService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de um novo sorteio.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SorteioGetResponseDto> save(
            @RequestBody @Valid SorteioPostRequestDto requestDto
    ) {

        Sorteio sorteio = ObjectMapperUtil.map(
                requestDto,
                Sorteio.class
        );

        Sorteio sorteioSalvo =
                sorteioService.save(sorteio);

        SorteioGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        sorteioSalvo,
                        SorteioGetResponseDto.class
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
    public ResponseEntity<SorteioGetResponseDto> findById(
            @PathVariable Long id
    ) {

        SorteioGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        sorteioService.findById(id),
                        SorteioGetResponseDto.class
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
    public ResponseEntity<Page<SorteioGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<SorteioGetResponseDto> responseDto =
                sorteioService.findAll(pageable)
                        .map(sorteio ->
                                ObjectMapperUtil.map(
                                        sorteio,
                                        SorteioGetResponseDto.class
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
    public ResponseEntity<SorteioGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid SorteioPostRequestDto dto
    ) {

        Sorteio sorteio = ObjectMapperUtil.map(
                dto,
                Sorteio.class
        );

        Sorteio sorteioAtualizado =
                sorteioService.update(id, sorteio);

        SorteioGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        sorteioAtualizado,
                        SorteioGetResponseDto.class
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

        sorteioService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}