package br.com.contadora.contadora_api.dto;

import jakarta.validation.constraints.*;

public record DadosCadastrarProdutoDTO(

        @NotBlank
        String nome,

        @NotBlank
        String descricao,

        @Min(0)
        int quantidade,

        @NotBlank
        String categoria,

        @PositiveOrZero
        double precoquepaguei,

        @Positive
        double precovenda,

        @NotBlank
        String imagem

) {
}
