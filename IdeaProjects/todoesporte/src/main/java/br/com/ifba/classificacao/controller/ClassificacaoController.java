package br.com.ifba.classificacao.controller;

import br.com.ifba.classificacao.dto.ClassificacaoGetResponseDto;
import br.com.ifba.classificacao.dto.ClassificacaoPostRequestDto;
import br.com.ifba.classificacao.entity.Classificacao;
import br.com.ifba.classificacao.service.ClassificacaoIService;
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
@RequestMapping(path = "/classificacoes")
@RequiredArgsConstructor
public class ClassificacaoController {

    private final ClassificacaoIService classificacaoService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de uma nova classificação.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ClassificacaoGetResponseDto> save(
            @RequestBody @Valid ClassificacaoPostRequestDto requestDto
    ) {

        Classificacao classificacao = ObjectMapperUtil.map(
                requestDto,
                Classificacao.class
        );

        Classificacao classificacaoSalva =
                classificacaoService.save(classificacao);

        ClassificacaoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        classificacaoSalva,
                        ClassificacaoGetResponseDto.class
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
    public ResponseEntity<ClassificacaoGetResponseDto> findById(
            @PathVariable Long id
    ) {

        ClassificacaoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        classificacaoService.findById(id),
                        ClassificacaoGetResponseDto.class
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
    public ResponseEntity<Page<ClassificacaoGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<ClassificacaoGetResponseDto> responseDto =
                classificacaoService.findAll(pageable)
                        .map(classificacao ->
                                ObjectMapperUtil.map(
                                        classificacao,
                                        ClassificacaoGetResponseDto.class
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
    public ResponseEntity<ClassificacaoGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid ClassificacaoPostRequestDto dto
    ) {

        Classificacao classificacao = ObjectMapperUtil.map(
                dto,
                Classificacao.class
        );

        Classificacao classificacaoAtualizada =
                classificacaoService.update(id, classificacao);

        ClassificacaoGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        classificacaoAtualizada,
                        ClassificacaoGetResponseDto.class
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

        classificacaoService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}