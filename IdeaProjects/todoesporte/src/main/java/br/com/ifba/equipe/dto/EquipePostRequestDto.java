package br.com.ifba.equipe.dto;

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
public class EquipePostRequestDto implements Serializable {

    @NotBlank(message = "O nome da equipe é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome da equipe deve possuir entre 3 e 100 caracteres.")
    @JsonProperty("nome")
    private String nome;

}