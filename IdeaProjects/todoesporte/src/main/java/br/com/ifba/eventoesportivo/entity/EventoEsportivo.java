package br.com.ifba.eventoesportivo.entity;
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
@Table(name = "eventos_esportivos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventoEsportivo extends PersistenceEntity implements Serializable{

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "horario", nullable = false)
    private LocalTime horario;

    @Column(name = "vagas", nullable = false)
    private Integer vagas;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
}
