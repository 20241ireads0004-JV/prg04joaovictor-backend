package br.com.ifba.usuario.controller;

import br.com.ifba.infrastructure.util.ObjectMapperUtil;
import br.com.ifba.usuario.dto.LoginRequestDto;
import br.com.ifba.usuario.dto.LoginResponseDto;
import br.com.ifba.usuario.dto.UsuarioGetResponseDto;
import br.com.ifba.usuario.dto.UsuarioPostRequestDto;
import br.com.ifba.usuario.dto.UsuarioPutRequestDto;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioIService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioIService usuarioService;

    // =========================
    // POST - SALVAR USUÁRIO
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
        usuario.setStatus(true);
        usuario.setDataCadastro(LocalDate.now());

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
    // GET ALL (COM PAGINAÇÃO)
    // =========================
    @GetMapping(
            path = "/findAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<UsuarioGetResponseDto>> findAll(
            Pageable pageable
    ) {
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
    // PUT - ATUALIZAR
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
    // DELETE - REMOVER
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

    // =========================
    // POST - LOGIN / AUTENTICAÇÃO
    // =========================
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto dto
    ) {
        Usuario usuario = usuarioService.autenticar(
                dto.getLogin(),
                dto.getSenha()
        );

        LoginResponseDto response = new LoginResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}