package br.com.ifba.webclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostApiDto {

    @NotNull(message = "O id não pode ser nulo.")
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "O id do usuário não pode ser nulo.")
    @JsonProperty("userId")
    private Long userId;

    @NotBlank(message = "O título não pode estar vazio.")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "O conteúdo não pode estar vazio.")
    @JsonProperty("body")
    private String body;
}
