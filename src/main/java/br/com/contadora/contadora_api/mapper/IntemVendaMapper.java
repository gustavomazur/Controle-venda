package br.com.contadora.contadora_api.mapper;

import br.com.contadora.contadora_api.dto.ItemVendaDTO;
import br.com.contadora.contadora_api.model.Produto.Produto;
import br.com.contadora.contadora_api.model.venda.ItemVenda;

public class IntemVendaMapper {

    // Entidade para DTO (Para mostrar a venda pronta)
    public static ItemVendaDTO paraDTO(ItemVenda item) {
        if (item == null) return null;
        return  new ItemVendaDTO(
                item.getProduto().getId(),
                item.getQuantidade(),
                item.getPrecoVendido()
        );
    }
    public static ItemVenda paraEntidade(ItemVendaDTO dto) {
        if (dto == null) return null;

        ItemVenda item = new ItemVenda();

        //Criamos um "proxy" do produto apenas com o ID
        Produto produto = new Produto();
        produto.setId(dto.produtoId());

        item.setProduto(produto);
        item.setQuantidade(dto.quantidade());
        item.setPrecoVendido(dto.preco());
        return  item;
    }
}
