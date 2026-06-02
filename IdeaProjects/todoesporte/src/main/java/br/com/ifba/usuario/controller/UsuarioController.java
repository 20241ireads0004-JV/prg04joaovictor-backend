package br.com.ifba.usuario.controller;

import br.com.ifba.infraestructure.util.ObjectMapperUtil;
import br.com.ifba.usuario.dto.UsuarioGetResponseDto;
import br.com.ifba.usuario.dto.UsuarioPostRequestDto;
import br.com.ifba.usuario.dto.UsuarioPutRequestDto;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import br.com.ifba.usuario.service.UsuarioIService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioIService usuarioService;

    private ObjectMapperUtil objectMapperUtil;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Lista de todos os usuários cadastrados na base de dados.
     * @return uma entidade de resposta genérica.
     * **/

    // =========================
    // POST
    // =========================
    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UsuarioGetResponseDto> save(
            @RequestBody @Valid UsuarioPostRequestDto requestDto
    ) {
        Usuario usuario = ObjectMapperUtil.map(
                requestDto,
                Usuario.class
        );
        Usuario usuarioSalvo = usuarioService.save(usuario);
        UsuarioGetResponseDto responseDto = ObjectMapperUtil.map(
                usuarioSalvo,
                UsuarioGetResponseDto.class
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
    public ResponseEntity<UsuarioGetResponseDto> findById(
            @PathVariable Long id
    ) {
        UsuarioGetResponseDto responseDto = ObjectMapperUtil.map(
                usuarioService.findById(id),
                UsuarioGetResponseDto.class
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
    public ResponseEntity<Page<UsuarioGetResponseDto>> findAll(
            Pageable pageable
    ) {

        //Utilização de paginação
        Page<UsuarioGetResponseDto> responseDto =
                usuarioService.findAll(pageable)
                        .map(usuario ->
                                ObjectMapperUtil.map(
                                        usuario,
                                        UsuarioGetResponseDto.class
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
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UsuarioGetResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioPutRequestDto dto
    ) {
        Usuario usuario = ObjectMapperUtil.map(
                dto,
                Usuario.class
        );
        Usuario usuarioAtualizado = usuarioService.update(id, usuario);

        UsuarioGetResponseDto responseDto = ObjectMapperUtil.map(
                usuarioAtualizado,
                UsuarioGetResponseDto.class
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
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
