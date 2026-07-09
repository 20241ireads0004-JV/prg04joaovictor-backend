package br.com.ifba.eventoesportivo.entity;
import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import br.com.ifba.local.entity.Local;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
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

    // vários eventos -> um grupo
    @ManyToOne
    @JoinColumn(name = "grupo_esportivo_id")
    private GrupoEsportivo grupoEsportivo;

    // vários eventos -> um local
    @ManyToOne
    @JoinColumn(name = "local_id")
    private Local local;
}
