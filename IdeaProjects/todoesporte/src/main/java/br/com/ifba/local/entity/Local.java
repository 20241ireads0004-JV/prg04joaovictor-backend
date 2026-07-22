package br.com.ifba.local.entity;
import br.com.ifba.eventoesportivo.entity.EventoEsportivo;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "locais")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Local extends PersistenceEntity implements Serializable{

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "endereco", nullable = false, length = 50)
    private String endereco;

    @Column(name = "cidade", nullable = false, length = 50)
    private String cidade;

    @Column(name = "bairro", nullable = false, length = 50)
    private String bairro;

    @OneToMany(mappedBy = "local")
    private List<EventoEsportivo> eventosEsportivos;
}
