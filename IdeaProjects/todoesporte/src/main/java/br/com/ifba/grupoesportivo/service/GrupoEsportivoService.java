package br.com.ifba.grupoesportivo.service;

import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import br.com.ifba.grupoesportivo.repository.GrupoEsportivoRepository;
import br.com.ifba.infraestructure.exception.BusinessException;
import br.com.ifba.infraestructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrupoEsportivoService implements GrupoEsportivoIService {

    // Logger utilizado para registrar eventos da aplicação
    private static final Logger logger =
            LoggerFactory.getLogger(GrupoEsportivoService.class);

    // Injeção de dependência automática pelo Lombok
    private final GrupoEsportivoRepository grupoEsportivoRepository;

    @Override
    @Transactional
    public GrupoEsportivo save(GrupoEsportivo grupoEsportivo) {

        logger.info(
                "[SERVICE] Iniciando cadastro do grupo esportivo: {}",
                grupoEsportivo.getNome()
        );

        validarNomeUnico(grupoEsportivo);

        try {

            GrupoEsportivo grupoSalvo =
                    grupoEsportivoRepository.save(grupoEsportivo);

            logger.info(
                    "[SERVICE] Grupo esportivo cadastrado com sucesso."
            );

            return grupoSalvo;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar grupo esportivo: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao realizar cadastro do grupo esportivo."
            );
        }
    }

    @Override
    public Page<GrupoEsportivo> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todos os grupos esportivos."
        );

        return grupoEsportivoRepository.findAll(pageable);
    }

    @Override
    public GrupoEsportivo findById(Long id) {

        logger.info(
                "[SERVICE] Buscando grupo esportivo ID: {}",
                id
        );

        return grupoEsportivoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Grupo esportivo não encontrado."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão do grupo esportivo ID: {}",
                id
        );

        if (!grupoEsportivoRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Grupo esportivo não encontrado. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Grupo esportivo não encontrado."
            );
        }

        grupoEsportivoRepository.deleteById(id);

        logger.info(
                "[SERVICE] Grupo esportivo deletado com sucesso."
        );
    }

    @Override
    @Transactional
    public GrupoEsportivo update(Long id, GrupoEsportivo grupoEsportivo) {

        logger.info(
                "[SERVICE] Atualizando grupo esportivo ID: {}",
                id
        );

        GrupoEsportivo grupoExistente =
                grupoEsportivoRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Grupo esportivo não encontrado."
                                )
                        );

        if (grupoEsportivoRepository.existsByNomeAndIdNot(
                grupoEsportivo.getNome(),
                id
        )) {

            logger.warn(
                    "[SERVICE] Nome do grupo esportivo já cadastrado: {}",
                    grupoEsportivo.getNome()
            );

            throw new BusinessException(
                    "Já existe um grupo esportivo cadastrado com esse nome."
            );
        }

        grupoExistente.setNome(
                grupoEsportivo.getNome()
        );

        grupoExistente.setDescricao(
                grupoEsportivo.getDescricao()
        );

        grupoExistente.setDataCriacao(
                grupoEsportivo.getDataCriacao()
        );

        GrupoEsportivo grupoAtualizado =
                grupoEsportivoRepository.save(grupoExistente);

        logger.info(
                "[SERVICE] Grupo esportivo atualizado com sucesso."
        );

        return grupoAtualizado;
    }

    /*
     * Método auxiliar responsável por validar
     * a unicidade do nome do grupo esportivo.
     */
    private void validarNomeUnico(GrupoEsportivo grupoEsportivo) {

        if (grupoEsportivoRepository.existsByNome(
                grupoEsportivo.getNome()
        )) {

            logger.warn(
                    "[SERVICE] Nome do grupo esportivo já cadastrado: {}",
                    grupoEsportivo.getNome()
            );

            throw new BusinessException(
                    "Já existe um grupo esportivo cadastrado com esse nome."
            );
        }
    }

}