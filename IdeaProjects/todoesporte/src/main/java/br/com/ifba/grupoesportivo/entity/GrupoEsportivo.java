package br.com.ifba.grupoesportivo.entity;

import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "gruposEsportivos")
    private List<Atleta> atletas = new ArrayList<>();

}