package br.com.contadora.contadora_api.mapper;
import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.dto.ProdutoDTO;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.Produto.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProdutoMapper {

    public static ProdutoDTO paraDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getQuantidade(),
                produto.getCategoria(),
                produto.getDescricao(),
                produto.getPrecoDeCompra(),
                produto.getPrecoDeVenda(),
                produto.getImagem(),
                produto.getBarraDoProduto()
        );
    }
    public static Produto paraProduto(ProdutoDTO produtoDTO) {
        return new Produto(
                produtoDTO.id(),
                produtoDTO.nome(),
                produtoDTO.quantidade(),
                produtoDTO.categoria(),
                produtoDTO.descricao(),
                produtoDTO.precoCompra(),
                produtoDTO.precoVenda(),
                produtoDTO.imagem(),
                produtoDTO.barraDoProduto()
        );

    }
}

