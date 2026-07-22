package br.com.ifba.grupoesportivo.service;

import br.com.ifba.administrador.entity.Administrador;
import br.com.ifba.administrador.repository.AdministradorRepository;
import br.com.ifba.atleta.entity.Atleta;
import br.com.ifba.atleta.repository.AtletaRepository;
import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import br.com.ifba.grupoesportivo.repository.GrupoEsportivoRepository;
import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GrupoEsportivoService implements GrupoEsportivoIService {

    private static final Logger logger = LoggerFactory.getLogger(GrupoEsportivoService.class);

    private final GrupoEsportivoRepository grupoEsportivoRepository;
    private final AdministradorRepository administradorRepository;
    private final AtletaRepository atletaRepository;

    @Override
    @Transactional
    public GrupoEsportivo save(GrupoEsportivo grupoEsportivo, Long administradorId) {
        logger.info("[SERVICE] Iniciando cadastro do grupo esportivo: {}", grupoEsportivo.getNome());

        validarNomeUnico(grupoEsportivo);

        // Busca o Administrador no banco
        Administrador admin = administradorRepository.findById(administradorId)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado."));

        grupoEsportivo.setAdministrador(admin);

        if (grupoEsportivo.getDataCriacao() == null) {
            grupoEsportivo.setDataCriacao(LocalDate.now());
        }

        try {
            GrupoEsportivo grupoSalvo = grupoEsportivoRepository.save(grupoEsportivo);
            logger.info("[SERVICE] Grupo esportivo cadastrado com sucesso por Admin ID: {}", administradorId);
            return grupoSalvo;
        } catch (Exception e) {
            logger.error("[SERVICE] Erro ao salvar grupo esportivo: {}", e.getMessage());
            throw new BusinessException("Erro interno ao realizar cadastro do grupo esportivo.");
        }
    }

    // REGRA: Atleta solicita entrada no grupo
    @Transactional
    public void solicitarEntrada(Long grupoId, Long atletaId) {
        logger.info("[SERVICE] Atleta ID {} solicitando entrada no Grupo ID {}", atletaId, grupoId);

        GrupoEsportivo grupo = grupoEsportivoRepository.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo esportivo não encontrado."));

        Atleta atleta = atletaRepository.findById(atletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));

        // Se o atleta não estiver na lista de pendentes e não for membro, adiciona a solicitação
        if (!grupo.getSolicitacoesPendentes().contains(atleta) && !grupo.getAtletas().contains(atleta)) {
            grupo.getSolicitacoesPendentes().add(atleta);
            grupoEsportivoRepository.save(grupo);
        }
    }

    // REGRA: Administrador aceita solicitação de atleta
    @Transactional
    public void aceitarAtleta(Long grupoId, Long atletaId, Long adminIdLogado) {
        logger.info("[SERVICE] Admin ID {} tentando aprovar Atleta ID {} no Grupo ID {}", adminIdLogado, atletaId, grupoId);

        GrupoEsportivo grupo = grupoEsportivoRepository.findById(grupoId)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo esportivo não encontrado."));

        // VALIDAÇÃO DE SEGURANÇA: Apenas o Administrador do grupo pode aceitar
        if (!grupo.getAdministrador().getId().equals(adminIdLogado)) {
            logger.warn("[SERVICE] Negado: Usuário ID {} não é o admin do grupo {}", adminIdLogado, grupoId);
            throw new BusinessException("Apenas o Administrador deste grupo pode aceitar atletas.");
        }

        Atleta atleta = atletaRepository.findById(atletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Atleta não encontrado."));

        // Remove das solicitações e adiciona aos membros do grupo
        grupo.getSolicitacoesPendentes().remove(atleta);
        if (!grupo.getAtletas().contains(atleta)) {
            grupo.getAtletas().add(atleta);
        }

        grupoEsportivoRepository.save(grupo);
        logger.info("[SERVICE] Atleta ID {} aceito com sucesso no Grupo ID {}", atletaId, grupoId);
    }

    @Override
    public Page<GrupoEsportivo> findAll(Pageable pageable) {
        return grupoEsportivoRepository.findAll(pageable);
    }

    @Override
    public GrupoEsportivo findById(Long id) {
        return grupoEsportivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo esportivo não encontrado."));
    }

    @Override
    public void delete(Long id) {
        if (!grupoEsportivoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grupo esportivo não encontrado.");
        }
        grupoEsportivoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public GrupoEsportivo update(Long id, GrupoEsportivo grupoEsportivo) {
        GrupoEsportivo grupoExistente = findById(id);

        if (grupoEsportivoRepository.existsByNomeAndIdNot(grupoEsportivo.getNome(), id)) {
            throw new BusinessException("Já existe um grupo esportivo cadastrado com esse nome.");
        }

        grupoExistente.setNome(grupoEsportivo.getNome());
        grupoExistente.setDescricao(grupoEsportivo.getDescricao());

        return grupoEsportivoRepository.save(grupoExistente);
    }

    private void validarNomeUnico(GrupoEsportivo grupoEsportivo) {
        if (grupoEsportivoRepository.existsByNome(grupoEsportivo.getNome())) {
            throw new BusinessException("Já existe um grupo esportivo cadastrado com esse nome.");
        }
    }
}