package br.com.ifba.campeonato.service;

import br.com.ifba.campeonato.entity.Campeonato;
import br.com.ifba.campeonato.repository.CampeonatoRepository;
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
public class CampeonatoService implements CampeonatoIService {

    private static final Logger logger =
            LoggerFactory.getLogger(CampeonatoService.class);

    private final CampeonatoRepository campeonatoRepository;

    @Override
    @Transactional
    public Campeonato save(Campeonato campeonato) {

        logger.info(
                "[SERVICE] Iniciando cadastro do campeonato: {}",
                campeonato.getNome()
        );

        validarNome(campeonato);

        try {

            Campeonato campeonatoSalvo =
                    campeonatoRepository.save(campeonato);

            logger.info(
                    "[SERVICE] Campeonato cadastrado com sucesso."
            );

            return campeonatoSalvo;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar campeonato: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao realizar cadastro do campeonato."
            );
        }
    }

    @Override
    public Page<Campeonato> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todos os campeonatos."
        );

        return campeonatoRepository.findAll(pageable);
    }

    @Override
    public Campeonato findById(Long id) {

        return campeonatoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Campeonato não encontrado."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão do campeonato ID: {}",
                id
        );

        if (!campeonatoRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Campeonato não encontrado. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Campeonato não encontrado."
            );
        }

        campeonatoRepository.deleteById(id);

        logger.info(
                "[SERVICE] Campeonato deletado com sucesso."
        );
    }

    @Override
    @Transactional
    public Campeonato update(Long id, Campeonato campeonato) {

        logger.info(
                "[SERVICE] Atualizando campeonato ID: {}",
                id
        );

        Campeonato campeonatoExistente =
                campeonatoRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Campeonato não encontrado."
                                )
                        );

        if (campeonatoRepository.existsByNomeAndIdNot(
                campeonato.getNome(),
                id
        )) {

            logger.warn(
                    "[SERVICE] Nome do campeonato já cadastrado: {}",
                    campeonato.getNome()
            );

            throw new BusinessException(
                    "Já existe um campeonato com esse nome."
            );
        }

        campeonatoExistente.setNome(campeonato.getNome());
        campeonatoExistente.setComentario(campeonato.getComentario());
        campeonatoExistente.setData(campeonato.getData());
        campeonatoExistente.setStatus(campeonato.getStatus());

        Campeonato campeonatoAtualizado =
                campeonatoRepository.save(campeonatoExistente);

        logger.info(
                "[SERVICE] Campeonato atualizado com sucesso."
        );

        return campeonatoAtualizado;
    }

    /*
     * Método auxiliar responsável por validar
     * a unicidade do nome.
     */
    private void validarNome(Campeonato campeonato) {

        if (campeonatoRepository.existsByNome(
                campeonato.getNome()
        )) {

            logger.warn(
                    "[SERVICE] Nome do campeonato já cadastrado: {}",
                    campeonato.getNome()
            );

            throw new BusinessException(
                    "Já existe um campeonato com esse nome."
            );
        }

    }

}