package br.com.ifba.atleta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtletaPostRequestDto implements Serializable {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100)
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Email inválido.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "O login é obrigatório.")
    @Size(min = 4, max = 50)
    @JsonProperty("login")
    private String login;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, max = 20)
    @JsonProperty("senha")
    private String senha;

    @NotBlank(message = "O telefone é obrigatório.")
    @JsonProperty("telefone")
    private String telefone;

    @NotNull(message = "A data de cadastro é obrigatória.")
    @JsonProperty("dataCadastro")
    private LocalDate dataCadastro;

    @NotNull(message = "O status é obrigatório.")
    @JsonProperty("status")
    private Boolean status;

}