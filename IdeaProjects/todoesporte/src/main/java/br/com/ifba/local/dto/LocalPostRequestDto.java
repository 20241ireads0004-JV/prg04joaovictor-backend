package br.com.ifba.local.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class LocalPostRequestDto implements Serializable {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(
            min = 3,
            max = 50,
            message = "O nome deve possuir entre 3 e 50 caracteres."
    )
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "O endereço é obrigatório.")
    @Size(
            min = 5,
            max = 50,
            message = "O endereço deve possuir entre 5 e 50 caracteres."
    )
    @JsonProperty("endereco")
    private String endereco;

    @NotBlank(message = "A cidade é obrigatória.")
    @Size(
            min = 2,
            max = 50,
            message = "A cidade deve possuir entre 2 e 50 caracteres."
    )
    @JsonProperty("cidade")
    private String cidade;

    @NotBlank(message = "O bairro é obrigatório.")
    @Size(
            min = 2,
            max = 50,
            message = "O bairro deve possuir entre 2 e 50 caracteres."
    )
    @JsonProperty("bairro")
    private String bairro;

}