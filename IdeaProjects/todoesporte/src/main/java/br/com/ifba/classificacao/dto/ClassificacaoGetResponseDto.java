package br.com.ifba.classificacao.dto;

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
public class ClassificacaoGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("posicao")
    private Integer posicao;

    @JsonProperty("pontuacao")
    private Integer pontuacao;

    @JsonProperty("golsMarcados")
    private Integer golsMarcados;

    @JsonProperty("golsSofridos")
    private Integer golsSofridos;

}