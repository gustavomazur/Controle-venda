package br.com.contadora.contadora_api.model.Produto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "produto")
@Entity(name = "Produto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nome;
    private String categoria;
    private String descricao;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private String imagem;
    private String barraProduto;
}
