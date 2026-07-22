package br.com.ifba.grupoesportivo.dto;

import br.com.ifba.administrador.dto.AdministradorGetResponseDto;
import br.com.ifba.atleta.dto.AtletaGetResponseDto;
import br.com.ifba.esporte.dto.EsporteGetResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrupoEsportivoGetResponseDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("dataCriacao")
    private LocalDate dataCriacao;

    @JsonProperty("esporte")
    private EsporteGetResponseDto esporte;

    // Retorna os dados do Administrador responsável pelo grupo
    @JsonProperty("administrador")
    private AdministradorGetResponseDto administrador;

    // Retorna a lista de atletas membros do grupo
    @JsonProperty("atletas")
    private List<AtletaGetResponseDto> atletas;

    // Retorna as solicitações de atletas que aguardam aprovação
    @JsonProperty("solicitacoesPendentes")
    private List<AtletaGetResponseDto> solicitacoesPendentes;
}