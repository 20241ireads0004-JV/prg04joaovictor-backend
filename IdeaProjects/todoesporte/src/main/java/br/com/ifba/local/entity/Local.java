package br.com.ifba.local.entity;
import br.com.ifba.infraestructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
}
