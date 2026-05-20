package main.java.br.com.ifba.br.usuario.service;

import main.java.br.com.ifba.br.usuario.entity.Usuario;

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
     * os usuários cadastrados.
     */
    List<Usuario> findAll();

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
}