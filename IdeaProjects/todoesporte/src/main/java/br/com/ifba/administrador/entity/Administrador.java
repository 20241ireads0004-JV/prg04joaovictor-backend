package br.com.ifba.administrador.entity;

import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entidade que representa o usuário Administrador no sistema TODO ESPORTE.
 * Herda todos os atributos básicos (nome, email, login, senha, etc.) da classe Usuario.
 */
@Entity
@Table(name = "administradores")
@Data
@NoArgsConstructor // Gera apenas o construtor vazio necessário para o JPA/Hibernate
@EqualsAndHashCode(callSuper = true) // Garante que os métodos equals e hashCode considerem os campos da classe pai (Usuario)
public class Administrador extends Usuario implements Serializable {

    // A classe herda todos os campos de Usuario.
    // Caso adiciones um campo exclusivo no futuro (ex: private String nivelAcesso;),
    // poderás voltar a utilizar a anotação @AllArgsConstructor.
}