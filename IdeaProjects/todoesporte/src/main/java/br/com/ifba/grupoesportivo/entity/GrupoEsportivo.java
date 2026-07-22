package br.com.ifba.grupoesportivo.entity;

import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.esporte.entity.Esporte;
import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import br.com.ifba.infrastructure.entity.PersistenceEntity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "grupos_esportivos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GrupoEsportivo extends PersistenceEntity implements Serializable {

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    // Muitos grupos -> Um esporte
    @ManyToOne
    @JoinColumn(name = "esporte_id")
    private Esporte esporte;

    // Grupo -> Eventos
    @OneToMany(mappedBy = "grupoEsportivo")
    private List<EventoEsportivo> eventos;

    // Relacionamento Mapeado de Atletas (Atleta é o dono da relação)
    @ManyToMany(mappedBy = "gruposEsportivos")
    private List<Atleta> atletas;
}