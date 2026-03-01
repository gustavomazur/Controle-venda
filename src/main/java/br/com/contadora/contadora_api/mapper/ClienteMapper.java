package br.com.contadora.contadora_api.mapper;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.model.Cliente.Cliente;

import java.util.ArrayList;

public class ClienteMapper {

        public static ClienteDTO paraDTO(Cliente cliente) {
            if (cliente == null) {
                return null;
            }
            return new ClienteDTO(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getTelefone(),
                    cliente.getEnderecos() != null ? cliente.getEnderecos() : new ArrayList<>(),
                    cliente.getCpf(),
                    cliente.getTamanho(),
                    cliente.getFoto()
            );
        }


        public static Cliente paraCliente(ClienteDTO clienteDTO) {
            if (clienteDTO == null) {
                return null;
            }

            return new Cliente(
                    clienteDTO.id(),
                    clienteDTO.nome(),
                    clienteDTO.telefone(),
                    clienteDTO.endereco() != null ? clienteDTO.endereco() : new ArrayList<>(),
                    clienteDTO.cpf(),
                    clienteDTO.tamanho(),
                    clienteDTO.foto()
            );

        }
    }

