package co.com.harcalejo.debtapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Clase usada para definir beans necesarios en el API
 * para que sean manejados por el contenedor de Spring
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Configuration
public class DebtConfiguration {

    /**
     * Incluimos el RestTemplate para consumo de API Restfull
     * para que sea gestionado por el contenedor de Spring
     *
     * @return instancia de {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
