package br.com.ifba.equipe.controller;

import br.com.ifba.equipe.dto.EquipeGetResponseDto;
import br.com.ifba.equipe.dto.EquipePostRequestDto;
import br.com.ifba.equipe.entity.Equipe;
import br.com.ifba.equipe.service.EquipeIService;
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
@RequestMapping(path = "/equipes")
@RequiredArgsConstructor
public class EquipeController {

    private final EquipeIService equipeService;

    /**
     * Cadastra uma nova equipe.
     */
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EquipeGetResponseDto> save(
            @RequestBody @Valid EquipePostRequestDto dto
    ) {

        Equipe equipe = ObjectMapperUtil.map(
                dto,
                Equipe.class
        );

        Equipe equipeSalva = equipeService.save(equipe);

        EquipeGetResponseDto responseDto = ObjectMapperUtil.map(
                equipeSalva,
                EquipeGetResponseDto.class
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    /**
     * Busca uma equipe pelo ID.
     */
    @GetMapping(
            path = "/findById/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EquipeGetResponseDto> findById(
            @PathVariable Long id
    ) {

        EquipeGetResponseDto responseDto = ObjectMapperUtil.map(
                equipeService.findById(id),
                EquipeGetResponseDto.class
        );

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Lista todas as equipes com paginação.
     */
    @GetMapping(
            path = "/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<EquipeGetResponseDto>> findAll(
            Pageable pageable
    ) {

        Page<EquipeGetResponseDto> response = equipeService
                .findAll(pageable)
                .map(equipe ->
                        ObjectMapperUtil.map(
                                equipe,
                                EquipeGetResponseDto.class
                        )
                );

        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza uma equipe.
     */
    @PutMapping(
            path = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EquipeGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid EquipePostRequestDto dto
    ) {

        Equipe equipe = ObjectMapperUtil.map(
                dto,
                Equipe.class
        );

        Equipe equipeAtualizada = equipeService.update(
                id,
                equipe
        );

        EquipeGetResponseDto responseDto = ObjectMapperUtil.map(
                equipeAtualizada,
                EquipeGetResponseDto.class
        );

        return ResponseEntity.ok(responseDto);
    }

    /**
     * Remove uma equipe.
     */
    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        equipeService.delete(id);

        return ResponseEntity.noContent().build();
    }

}