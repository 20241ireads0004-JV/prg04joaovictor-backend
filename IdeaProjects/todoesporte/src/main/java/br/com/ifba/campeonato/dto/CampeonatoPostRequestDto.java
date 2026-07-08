package br.com.ifba.campeonato.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CampeonatoPostRequestDto implements Serializable {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(
            min = 3,
            max = 100,
            message = "O nome deve possuir entre 3 e 100 caracteres."
    )
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "O comentário é obrigatório.")
    @Size(
            min = 5,
            max = 500,
            message = "O comentário deve possuir entre 5 e 500 caracteres."
    )
    @JsonProperty("comentario")
    private String comentario;

    @NotNull(message = "A data é obrigatória.")
    @JsonProperty("data")
    private LocalDate data;

    @NotBlank(message = "O status é obrigatório.")
    @Size(
            min = 3,
            max = 30,
            message = "O status deve possuir entre 3 e 30 caracteres."
    )
    @JsonProperty("status")
    private String status;

}