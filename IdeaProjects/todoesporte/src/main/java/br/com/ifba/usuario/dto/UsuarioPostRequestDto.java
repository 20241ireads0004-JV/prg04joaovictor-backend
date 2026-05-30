package br.com.ifba.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPostRequestDto implements Serializable {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail informado é inválido")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "O login é obrigatório")
    @Size(min = 4, max = 50, message = "O login deve ter entre 4 e 50 caracteres")
    @JsonProperty("login")
    private String login;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres")
    @JsonProperty("senha")
    private String senha;
}