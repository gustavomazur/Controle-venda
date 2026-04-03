package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.dto.ClienteDTO;
import br.com.contadora.contadora_api.dto.ProdutoDTO;
import br.com.contadora.contadora_api.mapper.ClienteMapper;
import br.com.contadora.contadora_api.mapper.ProdutoMapper;
import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.Produto.Produto;
import br.com.contadora.contadora_api.repository.ProdutoRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

        private final ProdutoRepository repository;
        private final Cloudinary cloudinary;

        public ProdutoService(ProdutoRepository repository, Cloudinary cloudinary) {
            this.repository = repository;
            this.cloudinary = cloudinary;
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
        public @Valid ProdutoDTO insert(@Valid ProdutoDTO produtoDTO, MultipartFile arquivo) throws IOException {
            
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(arquivo.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            String urlImagem = (String) uploadResult.get("secure_url");
            
            Produto produto = ProdutoMapper.paraProduto(produtoDTO);
            produto.setImagem(urlImagem);
            
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

        public List<ProdutoDTO> listarTodos() {
            return repository.findAll().stream()
                    .map(ProdutoMapper::paraDTO)
                    .collect(Collectors.toList());
        }

        public void deleteById(Long id) {
            repository.deleteById(id.longValue());
        }
    }
