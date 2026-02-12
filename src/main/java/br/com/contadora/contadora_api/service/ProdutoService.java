package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.dto.VendaResumoDTO;
import br.com.contadora.contadora_api.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.contadora.contadora_api.repository.ProdutoRepository;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    // ===============================
    // LUCRO TOTAL DO ESTOQUE (POTENCIAL)
    // ===============================
    public double lucroTotalEstoque() {

        List<Produto> produtos = repository.findAll();
        double total = 0;

        for (Produto p : produtos) {
            total += p.calcularLucro(); // lucro = (venda - custo) * quantidade
        }

        return total;
    }

    // ===============================
    // VENDA DE PRODUTO (LUCRO REAL)
    // ===============================
    public VendaResumoDTO vender(Long id, int quantidadeVendida) {

        Produto p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (quantidadeVendida <= 0) {
            throw new RuntimeException("Quantidade inválida");
        }

        if (p.getQuantidade() < quantidadeVendida) {
            throw new RuntimeException("Estoque insuficiente");
        }

        // 🔹 valores unitários
        double custoUnitario = p.getPrecoquepaguei();
        double precoVendaUnitario = p.getPrecodevenda();
        double lucroUnitario = precoVendaUnitario - custoUnitario;

        // 🔹 valores totais
        double custoTotal = custoUnitario * quantidadeVendida;
        double valorVendaTotal = precoVendaUnitario * quantidadeVendida;
        double lucroTotal = lucroUnitario * quantidadeVendida;

        // 🔹 atualiza estoque
        p.setQuantidade(p.getQuantidade() - quantidadeVendida);
        repository.save(p);

        // 🔹 retorno completo
        return new VendaResumoDTO(
                p.getNome(),
                quantidadeVendida,

                custoUnitario,
                precoVendaUnitario,
                lucroUnitario,

                custoTotal,
                valorVendaTotal,
                lucroTotal
        );
    }

    // ===============================
    // LUCRO UNITÁRIO DE UM PRODUTO
    // ===============================
    public double lucroUnitarioProduto(Long id) {

        Produto p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return p.calcularLucro();
    }
}

