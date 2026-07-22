package br.com.ifba.grupoesportivo.service;

import br.com.ifba.grupoesportivo.entity.GrupoEsportivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrupoEsportivoIService {

    /*
     * Salva um novo grupo esportivo no banco de dados.
     *
     * Este método realiza a validação de
     * unicidade do nome antes de persistir.
     */
    GrupoEsportivo save(GrupoEsportivo grupoEsportivo, Long administradorId);

    /*
     * Retorna uma lista com todos
     * os grupos esportivos cadastrados usando paginação.
     */
    Page<GrupoEsportivo> findAll(Pageable pageable);

    /*
     * Retorna o grupo esportivo que contém
     * o ID informado.
     */
    GrupoEsportivo findById(Long id);

    /*
     * Remove um grupo esportivo do banco
     * através do ID informado.
     */
    void delete(Long id);

    /*
     * Atualiza os dados de um grupo esportivo.
     *
     * O método verifica:
     * - se o grupo existe;
     * - duplicidade do nome.
     */
    GrupoEsportivo update(Long id, GrupoEsportivo grupoEsportivo);

    // Novos métodos de permissão e associação
    void solicitarEntrada(Long grupoId, Long atletaId);
    void aceitarAtleta(Long grupoId, Long atletaId, Long adminIdLogado);
}