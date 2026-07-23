package br.com.ifba.esporte.controller;

import br.com.ifba.esporte.dto.EsporteGetResponseDto;
import br.com.ifba.esporte.dto.EsportePostRequestDto;
import br.com.ifba.esporte.entity.Esporte;
import br.com.ifba.esporte.service.EsporteIService;
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
@RequestMapping(path = "/esportes")
@RequiredArgsConstructor
public class EsporteController {

    private final EsporteIService esporteService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de um novo esporte.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EsporteGetResponseDto> save(
            @RequestBody @Valid EsportePostRequestDto requestDto
    ) {

        Esporte esporte = ObjectMapperUtil.map(
                requestDto,
                Esporte.class
        );

        Esporte esporteSalvo = esporteService.save(esporte);

        EsporteGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        esporteSalvo,
                        EsporteGetResponseDto.class
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
    public ResponseEntity<EsporteGetResponseDto> findById(
            @PathVariable Long id
    ) {

        EsporteGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        esporteService.findById(id),
                        EsporteGetResponseDto.class
                );

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    // =========================
// GET ALL (Listar Esportes com Paginação)
// =========================
    @GetMapping(
            path = "/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<EsporteGetResponseDto>> findAll(
            Pageable pageable
    ) {
        // Busca os esportes paginados no service e converte cada entidade para DTO
        Page<EsporteGetResponseDto> responseDto =
                esporteService.findAll(pageable)
                        .map(esporte ->
                                ObjectMapperUtil.map(
                                        esporte,
                                        EsporteGetResponseDto.class
                                )
                        );

        // Retorna a página de DTOs com status HTTP 200 OK
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
    public ResponseEntity<EsporteGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid EsportePostRequestDto dto
    ) {

        Esporte esporte = ObjectMapperUtil.map(
                dto,
                Esporte.class
        );

        Esporte esporteAtualizado =
                esporteService.update(id, esporte);

        EsporteGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        esporteAtualizado,
                        EsporteGetResponseDto.class
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

        esporteService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}