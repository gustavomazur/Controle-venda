package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.dto.ProdutoDTO;
import br.com.contadora.contadora_api.mapper.ClienteMapper;
import br.com.contadora.contadora_api.mapper.ProdutoMapper;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.Produto.Produto;
import br.com.contadora.contadora_api.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

        private final ProdutoRepository repository;

        public ProdutoService(ProdutoRepository repository) {
            this.repository = repository;
        }


        public ProdutoDTO findById(Long id) {
            Produto produto = repository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o ID: " + id));
            return ProdutoMapper.paraDTO(produto);
        }
        public ProdutoDTO findByNome(String nome){
            Produto produto = repository.findByNome(nome)
                    .orElseThrow(() -> new RuntimeException("Produto " + nome + " não encontrado"));
            return ProdutoMapper.paraDTO(produto);
        }
        public @Valid ProdutoDTO insert(@Valid ProdutoDTO produtoDTO) {
            Produto produto = ProdutoMapper.paraProduto(produtoDTO);
            produto.setId(null);
            produto = repository.save(produto);
            return ProdutoMapper.paraDTO(produto);
        }

        public void atualizaProduto (Produto produto) {
            if (!repository.existsById(produto.getId().longValue())) {
                throw new EntityNotFoundException("Produto não encontrado para atualizar");
            }
            repository.save(produto);
        }

        public void deleteById(Long id) {
            repository.deleteById(id.longValue());
        }
    }

