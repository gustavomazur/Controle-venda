package br.com.contadora.contadora_api.model.Produto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "produto")
@Entity(name = "Produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer quantidade;
    private String categoria;
    private String descricao;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;

    @Column(length = 1000)
    private String imagem;
    private String barraDoProduto;
}







