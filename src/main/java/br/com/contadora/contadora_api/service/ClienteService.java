package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.mapper.ClienteMapper;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ClienteDTO findById(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o ID: " + id));
        return ClienteMapper.paraDTO(cliente);
    }
    public ClienteDTO findByNome(String nome){
        Cliente cliente = repository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Usuário " + nome + " não encontrado"));
        return ClienteMapper.paraDTO(cliente);
    }
    public @Valid ClienteDTO insert(@Valid ClienteDTO clienteDTO) {
        Cliente novoCliente = ClienteMapper.paraCliente(clienteDTO);
        novoCliente.setId(null);
        novoCliente = repository.save(novoCliente);
        return ClienteMapper.paraDTO(novoCliente);
    }

    public void atualizaCliente(Cliente cliente) {
        if (!repository.existsById(cliente.getId().longValue())) {
            throw new EntityNotFoundException("Cliente não encontrado para atualizar");
        }
        repository.save(cliente);
    }

    public void deleteById(Long id) {
        repository.deleteById(id.longValue());
    }
}