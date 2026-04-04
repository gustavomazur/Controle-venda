package br.com.contadora.contadora_api.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Cloudinary para upload de imagens
 * As credenciais devem ser definidas no arquivo .env
 */
@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(String.format(
            "cloudinary://%s:%s@%s",
            apiKey,
            apiSecret,
            cloudName
        ));
    }
}

