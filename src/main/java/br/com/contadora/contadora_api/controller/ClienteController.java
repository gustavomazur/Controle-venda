package br.com.contadora.contadora_api.controller;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {


    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = service.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscaPorId(@PathVariable Long id) {
        ClienteDTO clienete = service.findById(id);
        return ResponseEntity.ok(clienete);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ClienteDTO> buscarPorNome(@PathVariable String nome) {
        ClienteDTO cliente = service.findByNome(nome);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> criar(@RequestPart("cliente") @Valid ClienteDTO clienteDTO,
                                      @RequestPart("arquivo") MultipartFile arquivo) throws IOException {

        var clienteSalva = service.insert(clienteDTO, arquivo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteSalva.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        service.atualizaCliente(cliente);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}



