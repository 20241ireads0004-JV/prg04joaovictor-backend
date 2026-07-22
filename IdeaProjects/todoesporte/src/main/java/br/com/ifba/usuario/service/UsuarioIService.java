package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioIService {

    /*
     * Salva um novo usuário no banco de dados.
     *
     * Este método realiza validações de
     * unicidade de email e login antes
     * de persistir o usuário.
     */
    Usuario save(Usuario usuario);

    /*
     * Retorna uma lista com todos
     * os usuários cadastrados usando paginação.
     */
    Page<Usuario> findAll(Pageable pageable);

    /*
     * Retorna o usuário que contém
     * o ID informado
     */
    Usuario findById(Long id);

    /*
     * Remove um usuário do banco
     * através do ID informado.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de um usuário.
     *
     * O método verifica:
     * - se o usuário existe
     * - duplicidade de email
     * - duplicidade de login
     */
    Usuario update(Long id, Usuario usuario);

    Usuario autenticar(String login, String senha);
}