package br.com.contadora.contadora_api.controller;

import br.com.contadora.contadora_api.dto.UsuarioRequest;
import br.com.contadora.contadora_api.dto.UsuarioResponse;
import br.com.contadora.contadora_api.model.usuario.Usuario;
import br.com.contadora.contadora_api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequest request) {
        try {
            Usuario usuario = authService.cadastrar(request);

            UsuarioResponse response = new UsuarioResponse(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequest request) {
        try {
            String token = authService.login(request);
            UsuarioRequest usuario = new UsuarioRequest(null, request.email(), null);

            return ResponseEntity.ok(Map.of(
                "token", token,
                "id", 0,
                "nome", "",
                "email", request.email()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
