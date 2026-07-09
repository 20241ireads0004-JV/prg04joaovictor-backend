package br.com.ifba.classificacao.repository;

import br.com.ifba.classificacao.entity.Classificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificacaoRepository
        extends JpaRepository<Classificacao, Long> {

}