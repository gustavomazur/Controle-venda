package br.com.contadora.contadora_api.repository;

import br.com.contadora.contadora_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
