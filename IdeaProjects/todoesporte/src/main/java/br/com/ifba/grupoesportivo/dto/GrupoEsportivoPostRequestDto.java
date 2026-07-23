package br.com.ifba.grupoesportivo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class GrupoEsportivoPostRequestDto implements Serializable {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(
            min = 3,
            max = 100,
            message = "O nome deve possuir entre 3 e 100 caracteres."
    )
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(
            min = 5,
            max = 500,
            message = "A descrição deve possuir entre 5 e 500 caracteres."
    )
    @JsonProperty("descricao")
    private String descricao;

    @NotNull(message = "A data de criação é obrigatória.")
    @JsonProperty("dataCriacao")
    private LocalDate dataCriacao;

    // Alterado de esporteId para esporteNome
    @NotBlank(message = "O nome do esporte é obrigatório.")
    @JsonProperty("esporteNome")
    private String esporteNome;
}