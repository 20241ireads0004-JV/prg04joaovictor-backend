package br.com.ifba.estatistica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaPostRequestDto implements Serializable {

    @NotNull(message = "A quantidade de gols é obrigatória.")
    @Min(value = 0, message = "A quantidade de gols não pode ser negativa.")
    @JsonProperty("gols")
    private Integer gols;

    @NotNull(message = "A quantidade de assistências é obrigatória.")
    @Min(value = 0, message = "A quantidade de assistências não pode ser negativa.")
    @JsonProperty("assistencias")
    private Integer assistencias;

    @NotNull(message = "A quantidade de vitórias é obrigatória.")
    @Min(value = 0, message = "A quantidade de vitórias não pode ser negativa.")
    @JsonProperty("vitorias")
    private Integer vitorias;

    @NotNull(message = "A quantidade de derrotas é obrigatória.")
    @Min(value = 0, message = "A quantidade de derrotas não pode ser negativa.")
    @JsonProperty("derrotas")
    private Integer derrotas;

    @NotNull(message = "A quantidade de empates é obrigatória.")
    @Min(value = 0, message = "A quantidade de empates não pode ser negativa.")
    @JsonProperty("empates")
    private Integer empates;

    @NotNull(message = "A quantidade de pontos é obrigatória.")
    @Min(value = 0, message = "A quantidade de pontos não pode ser negativa.")
    @JsonProperty("pontos")
    private Integer pontos;

}