package br.com.contadora.contadora_api.dto;

public record VendaResumoDTO(String nomeProduto,
                             int quantidadeVendida,
                             double custoUnitario,
                             double precoVendaUnitario,
                             double lucroUnitario,

                             double custoTotal,
                             double valorVendaTotal,
                             double lucroTotal) {
}
