package br.com.contadora.contadora_api.dto;

import br.com.contadora.contadora_api.model.Produto;

public record DadosListagemProdutoDTO(
        String nome,
        String descricao,
        int quantidade,
        String categoria,
        double precoquepaguei,
        double precodevenda,
        String imagem
) {

    public DadosListagemProdutoDTO (Produto produto) {
        this(produto.getNome(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getCategoria(),
                produto.getPrecoquepaguei(),
                produto.getPrecodevenda(),
                produto.getImagem());
    }
}

