package br.com.ifba.equipe.entity;

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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Equipe extends PersistenceEntity implements Serializable {

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "equipes")
    private List<Atleta> atletas = new ArrayList<>();

}