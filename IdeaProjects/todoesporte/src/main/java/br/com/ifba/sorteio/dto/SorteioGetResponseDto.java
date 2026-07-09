package br.com.ifba.sorteio.dto;

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
public class SorteioGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("data")
    private LocalDate data;

}