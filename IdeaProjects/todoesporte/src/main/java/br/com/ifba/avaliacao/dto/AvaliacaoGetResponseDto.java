package br.com.ifba.avaliacao.dto;

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
public class AvaliacaoGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nota")
    private Integer nota;

    @JsonProperty("comentario")
    private String comentario;

    @JsonProperty("data")
    private LocalDate data;

}