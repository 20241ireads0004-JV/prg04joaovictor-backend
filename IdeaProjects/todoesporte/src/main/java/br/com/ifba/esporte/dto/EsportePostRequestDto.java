package br.com.ifba.esporte.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsportePostRequestDto implements Serializable {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100,
            message = "O nome deve possuir entre 3 e 100 caracteres.")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 5, max = 500,
            message = "A descrição deve possuir entre 5 e 500 caracteres.")
    @JsonProperty("descricao")
    private String descricao;

    @NotNull(message = "A quantidade de jogadores é obrigatória.")
    @Min(value = 1,
            message = "A quantidade de jogadores deve ser maior que zero.")
    @JsonProperty("quantidadeJogadores")
    private Integer quantidadeJogadores;

}