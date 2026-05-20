package main.java.br.com.ifba.br.usuario.controller;

import lombok.RequiredArgsConstructor;
import main.java.br.com.ifba.br.usuario.service.UsuarioIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.awt.*;

@RestController
@RequestMapping(path = "/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioIService usuarioService;

    /**
     * @author João Victor
     * @apiNote Endpoint criado desde a versão V1.0.1
     * Lista de todos os usuários cadastrados na base de dados.
     * @return uma entidade de resposta genérica.
     * **/

    @PostMapping(path = "/save",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody UsuarioPostRequestDto dto){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        objectMapperUtil.map(
                                this.usuarioService.save(dto),
                                UsuarioGetResponseDto.class
                        )
                );
    }
}
