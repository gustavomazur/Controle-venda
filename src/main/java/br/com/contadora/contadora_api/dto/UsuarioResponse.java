package br.com.contadora.contadora_api.dto;

public record UsuarioResponse(
        Long id,
        String nome,
        String email
) {
}
