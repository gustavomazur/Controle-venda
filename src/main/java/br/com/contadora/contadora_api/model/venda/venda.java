package br.com.contadora.contadora_api.model.venda;

import br.com.contadora.contadora_api.model.Cliente.Cliente;
import br.com.contadora.contadora_api.model.Produto.Produto;
import br.com.contadora.contadora_api.model.desconto.Desconto;
import br.com.contadora.contadora_api.model.tipo.TipoDeVenda;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "venda")
@Entity(name = "Venda")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private Produto produto;
    @Transient
    private Cliente cliente;
    private String vendedor;
    private TipoDeVenda tipoDeVenda;
    private Desconto desconto;


}
