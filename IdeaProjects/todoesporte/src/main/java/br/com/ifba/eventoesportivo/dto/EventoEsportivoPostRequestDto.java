package br.com.ifba.eventoesportivo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EventoEsportivoPostRequestDto implements Serializable {

    @NotNull(message = "A data é obrigatória.")
    @JsonProperty("data")
    private LocalDate data;

    @NotNull(message = "O horário é obrigatório.")
    @JsonProperty("horario")
    private LocalTime horario;

    @NotNull(message = "A quantidade de vagas é obrigatória.")
    @Min(value = 1, message = "O evento deve possuir pelo menos uma vaga.")
    @Max(value = 1000, message = "O número máximo de vagas é 1000.")
    @JsonProperty("vagas")
    private Integer vagas;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(
            min = 5,
            max = 500,
            message = "A descrição deve possuir entre 5 e 500 caracteres."
    )
    @JsonProperty("descricao")
    private String descricao;

}