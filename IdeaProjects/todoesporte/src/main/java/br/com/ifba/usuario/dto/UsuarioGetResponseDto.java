package br.com.ifba.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetResponseDto {

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "login")
    private String login;

}
