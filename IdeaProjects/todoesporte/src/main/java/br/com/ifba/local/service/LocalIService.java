package br.com.ifba.local.service;

import br.com.ifba.local.entity.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocalIService {

    /*
     * Salva um novo local.
     */
    Local save(Local local);

    /*
     * Retorna todos os locais
     * utilizando paginação.
     */
    Page<Local> findAll(Pageable pageable);

    /*
     * Busca um local pelo ID.
     */
    Local findById(Long id);

    /*
     * Remove um local.
     */
    void delete(Long id);

    /*
     * Atualiza um local.
     */
    Local update(Long id, Local local);

}