package br.com.ifba.grupoesportivo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GrupoEsportivoGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("dataCriacao")
    private LocalDate dataCriacao;

}