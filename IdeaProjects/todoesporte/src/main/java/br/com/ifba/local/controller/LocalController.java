package br.com.ifba.local.controller;

import br.com.ifba.infrastructure.util.ObjectMapperUtil;
import br.com.ifba.local.dto.LocalGetResponseDto;
import br.com.ifba.local.dto.LocalPostRequestDto;
import br.com.ifba.local.entity.Local;
import br.com.ifba.local.service.LocalIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/locais")
@RequiredArgsConstructor
public class LocalController {

    private final LocalIService localService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Realiza o cadastro de um novo local.
     */

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LocalGetResponseDto> save(
            @RequestBody @Valid LocalPostRequestDto requestDto
    ) {

        Local local = ObjectMapperUtil.map(
                requestDto,
                Local.class
        );

        Local localSalvo = localService.save(local);

        LocalGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        localSalvo,
                        LocalGetResponseDto.class
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
    public ResponseEntity<LocalGetResponseDto> findById(
            @PathVariable Long id
    ) {

        LocalGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        localService.findById(id),
                        LocalGetResponseDto.class
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
    public ResponseEntity<Page<LocalGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<LocalGetResponseDto> responseDto =
                localService.findAll(pageable)
                        .map(local ->
                                ObjectMapperUtil.map(
                                        local,
                                        LocalGetResponseDto.class
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
    public ResponseEntity<LocalGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid LocalPostRequestDto dto
    ) {

        Local local = ObjectMapperUtil.map(
                dto,
                Local.class
        );

        Local localAtualizado =
                localService.update(id, local);

        LocalGetResponseDto responseDto =
                ObjectMapperUtil.map(
                        localAtualizado,
                        LocalGetResponseDto.class
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

        localService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}