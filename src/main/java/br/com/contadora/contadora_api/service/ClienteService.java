package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.mapper.ClienteMapper;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
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
    public ClienteDTO findByNome(String nome){
        Cliente cliente = repository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Usuário " + nome + " não encontrado"));
        return ClienteMapper.paraDTO(cliente);
    }
    public @Valid ClienteDTO insert(@Valid ClienteDTO clienteDTO, MultipartFile arquivo) throws IOException {
        
        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult = cloudinary.uploader().upload(arquivo.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        String urlFoto = (String) uploadResult.get("secure_url");
        
        Cliente novoCliente = ClienteMapper.paraCliente(clienteDTO);
        novoCliente.setFoto(urlFoto);
        
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

    public List<ClienteDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ClienteMapper::paraDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        repository.deleteById(id.longValue());
    }
}