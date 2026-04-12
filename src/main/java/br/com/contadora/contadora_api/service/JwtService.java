package br.com.contadora.contadora_api.service;

import br.com.contadora.contadora_api.model.usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

    @Service
    public class JwtService {

        //Chave secreta (em produção, colocar no application.yml)
        private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        //Token válido por 24 horas
        private static final long EXPIRACAO_TOKEN = 1000 * 60 * 60 * 24;


         //Gera token JWT
        public String gerarToken(Usuario usuario) {

            Date agora = new Date();
            Date expiracao = new Date(agora.getTime() + EXPIRACAO_TOKEN);

            return Jwts.builder()
                    .setSubject(usuario.getEmail())
                    .claim("id", usuario.getId())
                    .claim("nome", usuario.getNome())
                    .claim("email", usuario.getEmail())
                    .setIssuedAt(agora)
                    .setExpiration(expiracao)
                    .signWith(secretKey)
                    .compact();
        }


        //Valida token JWT
        public boolean validarToken(String token) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token);

                return true;
            } catch (Exception e) {
                return false;
            }
        }


         //Extrai usuário do token
        public Usuario extrairUsuario(String token) {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Usuario usuario = new Usuario();
            usuario.setId(claims.get("id", Long.class));
            usuario.setNome(claims.get("nome", String.class));
            usuario.setEmail(claims.get("email", String.class));

            return usuario;
        }
    }

