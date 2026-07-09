package br.com.ifba.campeonato.entity;
import br.com.ifba.classificacao.entity.Classificacao;
import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import br.com.ifba.partida.entity.Partida;
import br.com.ifba.sorteio.entity.Sorteio;
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

    // campeonato pertence a um grupo
    @ManyToOne
    @JoinColumn(name = "grupo_esportivo_id")
    private GrupoEsportivo grupoEsportivo;

    // campeonato possui várias partidas
    @OneToMany(mappedBy = "campeonato")
    private List<Partida> partidas;

    // campeonato possui várias classificações
    @OneToMany(mappedBy = "campeonato")
    private List<Classificacao> classificacoes;

    // campeonato possui um sorteio
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sorteio_id")
    private Sorteio sorteio;
}
