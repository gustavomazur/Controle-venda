package br.com.contadora.contadora_api.dto;

public record UsuarioRequest(
        String nome,
        String email,
        String senha
) {
}
