package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.dto.UsuarioRequest;
import br.com.contadora.contadora_api.model.usuario.Usuario;
import br.com.contadora.contadora_api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Usuario cadastrar(UsuarioRequest request) {

        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("E-mail já está cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));

        return usuarioRepository.save(usuario);
    }

    public String login(UsuarioRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new RuntimeException("E-mail ou senha incorretos");
        }

        return jwtService.gerarToken(usuario);
    }
}
