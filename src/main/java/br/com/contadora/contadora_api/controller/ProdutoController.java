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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

        private final ProdutoService service;

        public ProdutoController(ProdutoService service) {
            this.service = service;
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

        @PostMapping
        public ResponseEntity<Void> criar(@Valid @RequestBody ProdutoDTO produtoDTO) {
            var produtoSalva = service.insert(produtoDTO);
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

