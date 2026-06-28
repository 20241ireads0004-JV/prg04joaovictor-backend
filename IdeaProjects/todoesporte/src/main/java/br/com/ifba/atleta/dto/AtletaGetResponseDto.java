package br.com.ifba.atleta.dto;

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
public class AtletaGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("email")
    private String email;

    @JsonProperty("login")
    private String login;

    @JsonProperty("telefone")
    private String telefone;

    @JsonProperty("dataCadastro")
    private LocalDate dataCadastro;

    @JsonProperty("status")
    private Boolean status;

}