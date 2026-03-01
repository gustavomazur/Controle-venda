package br.com.contadora.contadora_api.dto;

import br.com.contadora.contadora_api.model.endereco.Endereco;

import java.util.List;

public record ClienteDTO(
        Long id,
        String nome,
        String telefone,
        List<Endereco> endereco,
        String cpf,
        String tamanho,
        String foto
) {
}
