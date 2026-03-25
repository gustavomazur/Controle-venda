package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.dto.VendaDTO;
import br.com.contadora.contadora_api.mapper.VendaMapper;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.Produto.Produto;
import br.com.contadora.contadora_api.model.venda.Venda;
import br.com.contadora.contadora_api.repository.ClienteRepository;
import br.com.contadora.contadora_api.repository.ProdutoRepository;
import br.com.contadora.contadora_api.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public VendaDTO realizarVenda(VendaDTO dto) {
        // converte dto para entidade usando mapper
        Venda venda = VendaMapper.paraEntidade(dto);

        venda.setData(LocalDateTime.now());

        //valida o cliente buscando pelo ID se foi informado
        if (dto.clienteId() != null) {
            var cliente = clienteRepository.findById(dto.clienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            venda.setCliente(cliente);
        }

        // processa os Itens e Baixa o Estoque
        venda.getItens().forEach(item -> {
            //busca o produto real pra pegar o preço atual e o estoque
            var produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encotrado: ID" + item.getProduto().getId()));

            // olha estoque verifica
            if (produto.getQuantidade() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto " + produto.getNome());

            }

            // atualizar estoque
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            produtoRepository.save(produto);

            // Garante que o preço no item seja o preço de venda padrão,
            // SOMENTE se o vendedor não enviou um preço customizado (com desconto) no JSON.
            if (item.getPreco() == null) {
                item.setPreco(produto.getPrecoVenda());
            }

        });
        // salvar a venda
        Venda vendaSalva = vendaRepository.save(venda);

        // retorna dto para o front
        return VendaMapper.paraDTO(vendaSalva);

    }
    // historico geral tudo que ja foi vendido
    public List<VendaDTO> listarHisotricoGeral() {
        return vendaRepository.findAll().stream()
                .map(VendaMapper::paraDTO)
                .collect(Collectors.toList());
    }
    // historico do dia
    public List<VendaDTO> listarVendaDeHoje() {
        LocalDateTime inicioDoDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fimDoDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        return vendaRepository.findByDataBetween(inicioDoDia, fimDoDia).stream()
                .map(VendaMapper::paraDTO)
                .collect(Collectors.toList());
    }

    // historico oque cliente ja comprou
    public List<VendaDTO> buscarVendasPorCliente(Long clienteId) {
        return vendaRepository.findByClienteId(clienteId).stream()
                .map(VendaMapper::paraDTO)
                .collect(Collectors.toList());

    }
    public BigDecimal calcularLucroDoDia() {
        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fim = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        List<Venda> vendasDeHoje = vendaRepository.findByDataBetween(inicio, fim);

        BigDecimal lucroTotal = BigDecimal.ZERO;

        for (Venda venda : vendasDeHoje) {
            for (var item : venda.getItens()) {
                BigDecimal precoCompra = item.getProduto().getPrecoCompra();
                BigDecimal precoVendaNoMomento = item.getPreco(); // Preço que foi vendido
                Integer qtd = item.getQuantidade();

                // Lucro por item = (Venda - Compra) * Quantidade
                BigDecimal lucroItem = precoVendaNoMomento.subtract(precoCompra)
                        .multiply(new BigDecimal(qtd));

                lucroTotal = lucroTotal.add(lucroItem);
            }
        }
        return lucroTotal;
    }



}
