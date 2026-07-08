package br.com.ifba.campeonato.entity;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "campeonatos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Campeonato extends PersistenceEntity implements Serializable{

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "comentario", nullable = false, length = 500)
    private String comentario;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "status", nullable = false)
    private String status;
}
