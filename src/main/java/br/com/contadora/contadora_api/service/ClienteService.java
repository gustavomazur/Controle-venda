package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.mapper.ClienteMapper;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.endereco.Endereco;
import br.com.contadora.contadora_api.repository.ClienteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final Cloudinary cloudinary;

    public ClienteService(ClienteRepository repository, Cloudinary cloudinary) {
        this.repository = repository;
        this.cloudinary = cloudinary;
    }

    public ClienteDTO findById(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o ID: " + id));
        return ClienteMapper.paraDTO(cliente);
    }

    public ClienteDTO findByNome(String nome) {
        Cliente cliente = repository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Usuário " + nome + " não encontrado"));
        return ClienteMapper.paraDTO(cliente);
    }

    public ClienteDTO insert(@Valid ClienteDTO clienteDTO, MultipartFile arquivo) throws IOException {

        String urlFoto = null;

        // Fazer upload da foto se fornecida
        if (arquivo != null && !arquivo.isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader()
                    .upload(arquivo.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            urlFoto = (String) uploadResult.get("secure_url");
        }

        // Criar cliente e salvar
        Cliente novoCliente = ClienteMapper.paraCliente(clienteDTO);
        novoCliente.setFoto(urlFoto);
        novoCliente.setId(null);
        novoCliente = repository.save(novoCliente);
        
        return ClienteMapper.paraDTO(novoCliente);
    }

    /**
     * Adicionar endereços a um cliente existente
     * @param clienteId ID do cliente
     * @param enderecos Lista de endereços a adicionar
     * @return ClienteDTO atualizado
     */
    public ClienteDTO adicionarEnderecos(Long clienteId, List<Endereco> enderecos) {
        Cliente cliente = repository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + clienteId));

        if (cliente.getEnderecos() == null) {
            cliente.setEnderecos(enderecos);
        } else {
            cliente.getEnderecos().addAll(enderecos);
        }

        cliente = repository.save(cliente);
        return ClienteMapper.paraDTO(cliente);
    }

    public void atualizaCliente(Cliente cliente) {
        if (!repository.existsById(cliente.getId().longValue())) {
            throw new EntityNotFoundException("Cliente não encontrado para atualizar");
        }
        repository.save(cliente);
    }

    /**
     * Atualizar cliente existente
     * @param id ID do cliente
     * @param clienteDTO Dados atualizados
     * @return ClienteDTO atualizado
     */
    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));

        if (clienteDTO.nome() != null) cliente.setNome(clienteDTO.nome());
        if (clienteDTO.telefone() != null) cliente.setTelefone(clienteDTO.telefone());
        if (clienteDTO.cpf() != null) cliente.setCpf(clienteDTO.cpf());
        if (clienteDTO.tamanho() != null) cliente.setTamanho(clienteDTO.tamanho());
        if (clienteDTO.endereco() != null) cliente.setEnderecos(clienteDTO.endereco());

        cliente = repository.save(cliente);
        return ClienteMapper.paraDTO(cliente);
    }

    public List<ClienteDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ClienteMapper::paraDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        repository.deleteById(id.longValue());
    }
}