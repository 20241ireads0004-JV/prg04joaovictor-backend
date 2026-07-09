package br.com.ifba.local.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("endereco")
    private String endereco;

    @JsonProperty("cidade")
    private String cidade;

    @JsonProperty("bairro")
    private String bairro;

}