package br.com.ifba.partida.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PartidaPostRequestDto implements Serializable {

    @NotNull(message = "A data é obrigatória.")
    @JsonProperty("data")
    private LocalDate data;

    @NotNull(message = "O horário é obrigatório.")
    @JsonProperty("horario")
    private LocalTime horario;

    @NotNull(message = "O placar do time A é obrigatório.")
    @JsonProperty("placarA")
    private Integer placarA;

    @NotNull(message = "O placar do time B é obrigatório.")
    @JsonProperty("placarB")
    private Integer placarB;

    @NotBlank(message = "O status é obrigatório.")
    @JsonProperty("status")
    private String status;

}