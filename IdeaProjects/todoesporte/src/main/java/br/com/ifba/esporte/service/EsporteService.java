package br.com.ifba.esporte.service;

import br.com.ifba.esporte.entity.Esporte;
import br.com.ifba.esporte.repository.EsporteRepository;
import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EsporteService implements EsporteIService {

    // Logger utilizado para registrar eventos da aplicação
    private static final Logger logger =
            LoggerFactory.getLogger(EsporteService.class);

    // Injeção de dependência automática pelo Lombok
    private final EsporteRepository esporteRepository;

    @Override
    @Transactional
    public Esporte save(Esporte esporte) {

        logger.info(
                "[SERVICE] Iniciando cadastro do esporte: {}",
                esporte.getNome()
        );

        validarNomeUnico(esporte);

        try {

            Esporte esporteSalvo =
                    esporteRepository.save(esporte);

            logger.info(
                    "[SERVICE] Esporte cadastrado com sucesso."
            );

            return esporteSalvo;

        } catch (Exception e) {

            logger.error(
                    "[SERVICE] Erro ao salvar esporte: {}",
                    e.getMessage()
            );

            throw new BusinessException(
                    "Erro interno ao realizar cadastro do esporte."
            );
        }
    }

    @Override
    public Page<Esporte> findAll(Pageable pageable) {

        logger.info(
                "[SERVICE] Buscando todos os esportes."
        );

        return esporteRepository.findAll(pageable);
    }

    @Override
    public Esporte findById(Long id) {

        logger.info(
                "[SERVICE] Buscando esporte ID: {}",
                id
        );

        return esporteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Esporte não encontrado."
                        )
                );
    }

    @Override
    public void delete(Long id) {

        logger.info(
                "[SERVICE] Solicitação de exclusão do esporte ID: {}",
                id
        );

        if (!esporteRepository.existsById(id)) {

            logger.error(
                    "[SERVICE] Esporte não encontrado. ID: {}",
                    id
            );

            throw new ResourceNotFoundException(
                    "Esporte não encontrado."
            );
        }

        esporteRepository.deleteById(id);

        logger.info(
                "[SERVICE] Esporte deletado com sucesso."
        );
    }

    @Override
    @Transactional
    public Esporte update(Long id, Esporte esporte) {

        logger.info(
                "[SERVICE] Atualizando esporte ID: {}",
                id
        );

        Esporte esporteExistente =
                esporteRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Esporte não encontrado."
                                )
                        );

        if (esporteRepository.existsByNomeAndIdNot(
                esporte.getNome(),
                id
        )) {

            logger.warn(
                    "[SERVICE] Nome do esporte já cadastrado: {}",
                    esporte.getNome()
            );

            throw new BusinessException(
                    "Já existe um esporte cadastrado com esse nome."
            );
        }

        esporteExistente.setNome(
                esporte.getNome()
        );

        esporteExistente.setDescricao(
                esporte.getDescricao()
        );

        esporteExistente.setQuantidadeJogadores(
                esporte.getQuantidadeJogadores()
        );

        Esporte esporteAtualizado =
                esporteRepository.save(esporteExistente);

        logger.info(
                "[SERVICE] Esporte atualizado com sucesso."
        );

        return esporteAtualizado;
    }

    /*
     * Método auxiliar responsável por validar
     * a unicidade do nome do esporte.
     */
    private void validarNomeUnico(Esporte esporte) {

        if (esporteRepository.existsByNome(
                esporte.getNome()
        )) {

            logger.warn(
                    "[SERVICE] Nome do esporte já cadastrado: {}",
                    esporte.getNome()
            );

            throw new BusinessException(
                    "Já existe um esporte cadastrado com esse nome."
            );
        }
    }

}