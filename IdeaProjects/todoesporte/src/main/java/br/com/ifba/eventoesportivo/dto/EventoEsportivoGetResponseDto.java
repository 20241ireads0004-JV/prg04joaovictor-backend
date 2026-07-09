package br.com.ifba.eventoesportivo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoEsportivoGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("data")
    private LocalDate data;

    @JsonProperty("horario")
    private LocalTime horario;

    @JsonProperty("vagas")
    private Integer vagas;

    @JsonProperty("descricao")
    private String descricao;

}