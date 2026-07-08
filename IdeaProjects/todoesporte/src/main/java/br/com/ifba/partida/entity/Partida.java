package br.com.ifba.partida.entity;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "partidas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Partida extends PersistenceEntity implements Serializable{

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "horario", nullable = false)
    private LocalTime horario;

    @Column(name = "placar_a", nullable = false)
    private Integer placarA;

    @Column(name = "placar_b", nullable = false)
    private Integer placarB;

    @Column(name = "status", nullable = false)
    private String status;
}
