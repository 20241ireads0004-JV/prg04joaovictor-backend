package br.com.ifba.avaliacao.controller;

import br.com.ifba.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.avaliacao.dto.AvaliacaoPostRequestDto;
import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.avaliacao.service.AvaliacaoIService;
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
@RequestMapping(path = "/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoIService avaliacaoService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de uma nova avaliação.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AvaliacaoGetResponseDto> save(
            @RequestBody @Valid AvaliacaoPostRequestDto requestDto
    ) {

        Avaliacao avaliacao = ObjectMapperUtil.map(
                requestDto,
                Avaliacao.class
        );

        Avaliacao avaliacaoSalva =
                avaliacaoService.save(avaliacao);

        AvaliacaoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        avaliacaoSalva,
                        AvaliacaoGetResponseDto.class
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
    public ResponseEntity<AvaliacaoGetResponseDto> findById(
            @PathVariable Long id
    ) {

        AvaliacaoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        avaliacaoService.findById(id),
                        AvaliacaoGetResponseDto.class
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
    public ResponseEntity<Page<AvaliacaoGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<AvaliacaoGetResponseDto> responseDto =
                avaliacaoService.findAll(pageable)
                        .map(avaliacao ->
                                ObjectMapperUtil.map(
                                        avaliacao,
                                        AvaliacaoGetResponseDto.class
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
    public ResponseEntity<AvaliacaoGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid AvaliacaoPostRequestDto dto
    ) {

        Avaliacao avaliacao = ObjectMapperUtil.map(
                dto,
                Avaliacao.class
        );

        Avaliacao avaliacaoAtualizada =
                avaliacaoService.update(id, avaliacao);

        AvaliacaoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        avaliacaoAtualizada,
                        AvaliacaoGetResponseDto.class
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

        avaliacaoService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}