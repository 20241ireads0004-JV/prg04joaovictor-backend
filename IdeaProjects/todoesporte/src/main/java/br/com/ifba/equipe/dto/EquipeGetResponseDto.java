package br.com.ifba.equipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipeGetResponseDto {

    @JsonProperty(value = "nome")
    private String nome;
}
