package br.com.ifba.equipe.service;

import br.com.ifba.equipe.entity.Equipe;
import br.com.ifba.equipe.repository.EquipeRepository;
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
public class EquipeService implements EquipeIService {

    private static final Logger logger =
            LoggerFactory.getLogger(EquipeService.class);

    private final EquipeRepository equipeRepository;

    @Override
    @Transactional
    public Equipe save(Equipe equipe) {

        logger.info(
                "[SERVICE] Iniciando cadastro da equipe: {}",
                equipe.getNome()
        );

        validarNomeUnico(equipe);

        try {

            Equipe equipeSalva =
                    equipeRepository.save(equipe);

            logger.info(
                    "[SERVICE] Equipe cadastrada com sucesso."
            );

            return equipeSalva;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar equipe: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao realizar cadastro da equipe."
            );
        }
    }

    @Override
    public Page<Equipe> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todas as equipes."
        );

        return equipeRepository.findAll(pageable);
    }

    @Override
    public Equipe findById(Long id) {

        return equipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Equipe não encontrada."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão da equipe ID: {}",
                id
        );

        if (!equipeRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Equipe não encontrada. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Equipe não encontrada."
            );
        }

        equipeRepository.deleteById(id);

        logger.info(
                "[SERVICE] Equipe deletada com sucesso."
        );
    }

    @Override
    @Transactional
    public Equipe update(Long id, Equipe equipe) {

        logger.info(
                "[SERVICE] Atualizando equipe ID: {}",
                id
        );

        Equipe equipeExistente =
                equipeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Equipe não encontrada."
                                )
                        );

        if (equipeRepository.existsByNomeAndIdNot(
                equipe.getNome(),
                id
        )) {

            logger.warn(
                    "[SERVICE] Nome da equipe já cadastrado: {}",
                    equipe.getNome()
            );

            throw new BusinessException(
                    "Já existe uma equipe cadastrada com esse nome."
            );
        }

        equipeExistente.setNome(
                equipe.getNome()
        );

        Equipe equipeAtualizada =
                equipeRepository.save(equipeExistente);

        logger.info(
                "[SERVICE] Equipe atualizada com sucesso."
        );

        return equipeAtualizada;
    }

    /*
     * Método auxiliar responsável por validar
     * a unicidade do nome da equipe.
     */
    private void validarNomeUnico(Equipe equipe) {

        if (equipeRepository.existsByNome(
                equipe.getNome()
        )) {

            logger.warn(
                    "[SERVICE] Nome da equipe já cadastrado: {}",
                    equipe.getNome()
            );

            throw new BusinessException(
                    "Já existe uma equipe cadastrada com esse nome."
            );
        }
    }

}