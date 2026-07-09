package br.com.ifba.classificacao.dto;

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
public class ClassificacaoPostRequestDto implements Serializable {

    @NotNull(message = "A posição é obrigatória.")
    @Min(value = 1, message = "A posição deve ser maior que zero.")
    @JsonProperty("posicao")
    private Integer posicao;

    @NotNull(message = "A pontuação é obrigatória.")
    @Min(value = 0, message = "A pontuação não pode ser negativa.")
    @JsonProperty("pontuacao")
    private Integer pontuacao;

    @NotNull(message = "A quantidade de gols marcados é obrigatória.")
    @Min(value = 0, message = "Os gols marcados não podem ser negativos.")
    @JsonProperty("golsMarcados")
    private Integer golsMarcados;

    @NotNull(message = "A quantidade de gols sofridos é obrigatória.")
    @Min(value = 0, message = "Os gols sofridos não podem ser negativos.")
    @JsonProperty("golsSofridos")
    private Integer golsSofridos;

}