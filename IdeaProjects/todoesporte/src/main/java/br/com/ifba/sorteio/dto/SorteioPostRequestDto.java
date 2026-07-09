package br.com.ifba.sorteio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SorteioPostRequestDto implements Serializable {

    @NotBlank(message = "O tipo é obrigatório.")
    @JsonProperty("tipo")
    private String tipo;

    @NotNull(message = "A data é obrigatória.")
    @JsonProperty("data")
    private LocalDate data;

}