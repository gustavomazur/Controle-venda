package br.com.contadora.contadora_api.controller;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.dto.ProdutoDTO;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.Produto.Produto;
import br.com.contadora.contadora_api.service.ClienteService;
import br.com.contadora.contadora_api.service.ProdutoService;
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
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        List<ProdutoDTO> produtos = service.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscaPorId(@PathVariable Long id) {
        ProdutoDTO produto = service.findById(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ProdutoDTO> buscarPorNome(@PathVariable String nome) {
        ProdutoDTO produto = service.findByNome(nome);
        return ResponseEntity.ok(produto);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> criar(@RequestPart("produto") @Valid ProdutoDTO produtoDTO,
                                      @RequestPart("arquivo") MultipartFile arquivo) throws IOException {

        var produtoSalva = service.insert(produtoDTO, arquivo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(produtoSalva.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        produto.setId(id);
        service.atualizaProduto(produto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
