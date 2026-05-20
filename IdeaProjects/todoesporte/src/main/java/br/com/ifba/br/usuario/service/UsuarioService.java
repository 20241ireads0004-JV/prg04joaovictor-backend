package main.java.br.com.ifba.br.usuario.service;

import lombok.RequiredArgsConstructor;
import main.java.br.com.ifba.br.usuario.entity.Usuario;
import main.java.br.com.ifba.br.usuario.repository.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService {

    // Logger utilizado para registrar eventos da aplicação
    private static final Logger logger =
            LoggerFactory.getLogger(UsuarioService.class);

    // Injeção de dependência automática pelo Lombok
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {

        logger.info(
                "[SERVICE] Iniciando cadastro do usuário: {}",
                usuario.getLogin()
        );

        // Verifica se email e login já existem
        validarDadosUnicos(usuario);

        try {

            // Salva o usuário no banco
            Usuario usuarioSalvo =
                    usuarioRepository.save(usuario);

            logger.info(
                    "[SERVICE] Usuário cadastrado com sucesso."
            );

            return usuarioSalvo;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar usuário: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao realizar cadastro do usuário."
            );
        }
    }

    @Override
    public List<Usuario> findAll() {

        logger.info(
                "[SERVICE] Buscando todos os usuários."
        );

        // Retorna todos os usuários cadastrados
        return usuarioRepository.findAll();
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão do usuário ID: {}",
                id
        );

        // Verifica se o usuário existe
        if (!usuarioRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Usuário não encontrado. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Usuário não encontrado."
            );
        }

        // Remove o usuário do banco
        usuarioRepository.deleteById(id);

        logger.info(
                "[SERVICE] Usuário deletado com sucesso."
        );
    }

    @Override
    @Transactional
    public Usuario update(Long id, Usuario usuario) {

        logger.info(
                "[SERVICE] Atualizando usuário ID: {}",
                id
        );

        // Busca o usuário no banco
        Usuario usuarioExistente =
                usuarioRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Usuário não encontrado."
                                )
                        );

        /*
         * Verifica se outro usuário já utiliza
         * o email informado
         */
        if (usuarioRepository.existsByEmailAndIdNot(
                usuario.getEmail(),
                id
        )) {

            logger.warn(
                    "[SERVICE] Email já cadastrado: {}",
                    usuario.getEmail()
            );

            throw new BusinessException(
                    "Email já cadastrado."
            );
        }

        /*
         * Verifica se outro usuário já utiliza
         * o login informado
         */
        if (usuarioRepository.existsByLoginAndIdNot(
                usuario.getLogin(),
                id
        )) {

            logger.warn(
                    "[SERVICE] Login já cadastrado: {}",
                    usuario.getLogin()
            );

            throw new BusinessException(
                    "Login já cadastrado."
            );
        }

        // Atualiza os dados do usuário
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setLogin(usuario.getLogin());
        usuarioExistente.setSenha(usuario.getSenha());

        // Salva as alterações no banco
        Usuario usuarioAtualizado =
                usuarioRepository.save(usuarioExistente);

        logger.info(
                "[SERVICE] Usuário atualizado com sucesso."
        );

        return usuarioAtualizado;
    }

    /*
     * Método auxiliar responsável por validar
     * campos únicos antes do cadastro
     */
    private void validarDadosUnicos(Usuario usuario) {

        // Verifica duplicidade de email
        if (usuarioRepository.existsByEmail(
                usuario.getEmail()
        )) {

            logger.warn(
                    "[SERVICE] Email já cadastrado: {}",
                    usuario.getEmail()
            );

            throw new BusinessException(
                    "Email já cadastrado."
            );
        }

        // Verifica duplicidade de login
        if (usuarioRepository.existsByLogin(
                usuario.getLogin()
        )) {

            logger.warn(
                    "[SERVICE] Login já cadastrado: {}",
                    usuario.getLogin()
            );

            throw new BusinessException(
                    "Login já cadastrado."
            );
        }
    }
}