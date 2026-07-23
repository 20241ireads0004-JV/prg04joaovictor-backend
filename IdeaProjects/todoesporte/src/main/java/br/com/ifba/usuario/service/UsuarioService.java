package br.com.ifba.usuario.service;

import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.repository.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Usuario> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todos os usuários."
        );

        // Retorna todos os usuários cadastrados usando paginação
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Usuario findById(Long id){

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado com o ID: " + id)
                );
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

        // Busca o usuário existente
        Usuario usuarioExistente = usuarioRepository.findById(id)
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

        // Atualiza os dados permitidos
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setLogin(usuario.getLogin());
        usuarioExistente.setSenha(usuario.getSenha());
        usuarioExistente.setTelefone(usuario.getTelefone());
        usuarioExistente.setStatus(usuario.getStatus());

        // Não atualiza dataCadastro

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

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

    /**
     * Autentica o usuário verificando o login OU email e a senha.
     */
    @Override
    public Usuario autenticar(String loginOuEmail, String senha) {
        logger.info("[SERVICE] Tentativa de autenticação para: {}", loginOuEmail);

        // Busca o usuário verificando se o parâmetro coincide com o login OU com o email
        Usuario usuario = usuarioRepository.findByLoginOrEmail(loginOuEmail, loginOuEmail)
                .orElseThrow(() -> {
                    logger.warn("[SERVICE] Credenciais inválidas para: {}", loginOuEmail);
                    return new BusinessException("Login/Email ou senha inválidos.");
                });

        // Verifica a senha
        if (!usuario.getSenha().equals(senha)) {
            logger.warn("[SERVICE] Senha incorreta para o usuário: {}", loginOuEmail);
            throw new BusinessException("Login/Email ou senha inválidos.");
        }

        logger.info("[SERVICE] Usuário autenticado com sucesso: {}", usuario.getLogin());
        return usuario;
    }
}