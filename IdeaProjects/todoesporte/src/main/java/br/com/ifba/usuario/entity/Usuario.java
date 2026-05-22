package br.com.ifba.usuario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import br.com.ifba.infraestructure.entity.PersistenceEntity;

import java.io.Serializable;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Usuario extends PersistenceEntity implements Serializable {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_name", nullable = false, unique = true)
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;
}
