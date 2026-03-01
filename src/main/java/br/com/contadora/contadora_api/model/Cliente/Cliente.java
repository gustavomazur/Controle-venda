package br.com.contadora.contadora_api.model.Cliente;
import br.com.contadora.contadora_api.model.endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "cliente")
@Entity(name = "Cliente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private List<Endereco> enderecos;
    private String cpf;
    private String tamanho;
    private String foto;

}
