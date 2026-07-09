package br.com.ifba.sorteio.entity;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "sorteios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Sorteio extends PersistenceEntity implements Serializable{

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "data", nullable = false)
    private LocalDate data;

}
