package br.com.contadora.contadora_api.mapper;

import br.com.contadora.contadora_api.dto.VendaDTO;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.venda.ItemVenda;
import br.com.contadora.contadora_api.model.venda.Venda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendaMapper {


    public static VendaDTO paraDTO(Venda venda) {
        if (venda == null) return null;

        return new VendaDTO(
                venda.getId(),
                venda.getData(),
                venda.getCliente() != null ? venda.getCliente().getId() : null,
                venda.getCliente() != null ? venda.getCliente().getNome() : "Consumidor Final",
                venda.getVendedor(),
                venda.getTipoPagamento(),
                venda.getDesconto(),
                venda.getValorTotal(),
                venda.getLucroTotal(),
                venda.getItens() != null ?
                        venda.getItens().stream().map(IntemVendaMapper::paraDTO).toList() : new ArrayList<>()

        );
    }
    public static Venda paraEntidade(VendaDTO dto) {
        if (dto == null) return null;

        Venda venda = new Venda();
        venda.setId(dto.id());
        venda.setVendedor(dto.vendedor());
        venda.setDesconto(dto.desconto());
        venda.setData(dto.data());

        if (dto.clienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.clienteId());
            venda.setCliente(cliente);
        }

        if (dto.itens() != null) {
            List<ItemVenda> itens = dto.itens().stream()
                    .map(IntemVendaMapper::paraEntidade)
                    .collect(Collectors.toList());

            itens.forEach(item -> item.setVenda(venda));
            venda.setItens(itens);
        }
        return venda;

    }
    private static String VendedorTratado(String vendedor) {
        return vendedor != null ? vendedor : "Não informado";
    }
}
