package br.com.ifba.estatistica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("gols")
    private Integer gols;

    @JsonProperty("assistencias")
    private Integer assistencias;

    @JsonProperty("vitorias")
    private Integer vitorias;

    @JsonProperty("derrotas")
    private Integer derrotas;

    @JsonProperty("empates")
    private Integer empates;

    @JsonProperty("pontos")
    private Integer pontos;

}