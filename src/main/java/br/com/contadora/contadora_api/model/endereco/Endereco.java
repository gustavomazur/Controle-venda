package br.com.contadora.contadora_api.model.endereco;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "endereco")
@Entity(name = "Endereco")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cep;
    private String rua;
    private String numero;
    private String referecncia;
}
