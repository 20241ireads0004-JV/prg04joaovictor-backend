package br.com.ifba.partida.dto;

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
public class PartidaGetResponseDto implements Serializable {

    @JsonProperty("data")
    private LocalDate data;

    @JsonProperty("horario")
    private LocalTime horario;

    @JsonProperty("placarA")
    private Integer placarA;

    @JsonProperty("placarB")
    private Integer placarB;

    @JsonProperty("status")
    private String status;

}