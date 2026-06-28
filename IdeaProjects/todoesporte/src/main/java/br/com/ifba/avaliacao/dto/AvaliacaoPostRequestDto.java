package br.com.ifba.avaliacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AvaliacaoPostRequestDto implements Serializable {

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 1, message = "A nota mínima é 1.")
    @Max(value = 5, message = "A nota máxima é 5.")
    @JsonProperty("nota")
    private Integer nota;

    @NotBlank(message = "O comentário é obrigatório.")
    @JsonProperty("comentario")
    private String comentario;

    @NotNull(message = "A data é obrigatória.")
    @JsonProperty("data")
    private LocalDate data;

}