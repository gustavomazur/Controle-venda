package br.com.contadora.contadora_api.controller;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.endereco.Endereco;
import br.com.contadora.contadora_api.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

    private final ClienteService service;
    private final ObjectMapper objectMapper;

    public ClienteController(ClienteService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = service.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscaPorId(@PathVariable Long id) {
        ClienteDTO cliente = service.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ClienteDTO> buscarPorNome(@PathVariable String nome) {
        ClienteDTO cliente = service.findByNome(nome);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ClienteDTO> criar(
            @RequestParam String nome,
            @RequestParam String cpf,
            @RequestParam String telefone,
            @RequestParam String tamanho,
            @RequestParam(required = false) MultipartFile foto,
            @RequestParam(required = false) String endereco
    ) throws IOException {

        // Criar ClienteDTO
        ClienteDTO clienteDTO = new ClienteDTO(
                null,
                nome,
                telefone,
                null,
                cpf,
                tamanho,
                null
        );

        // Fazer upload da foto e salvar cliente
        ClienteDTO clienteSalva = service.insert(clienteDTO, foto);

        // Se enviou endereços, adicionar ao cliente
        if (endereco != null && !endereco.isEmpty()) {
            List<Endereco> enderecos = objectMapper.readValue(
                    endereco,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Endereco.class)
            );
            clienteSalva = service.adicionarEnderecos(clienteSalva.id(), enderecos);
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteSalva.id()).toUri();
        return ResponseEntity.created(uri).body(clienteSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteAtualizado = service.atualizar(id, clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}



