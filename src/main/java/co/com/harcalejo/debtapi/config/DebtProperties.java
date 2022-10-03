package co.com.harcalejo.debtapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Bean de configuracion usado para el mapeo de constantes y carga de
 * properties asociadas a las reglas y comportamientos segun el negocio
 * del dominio de Deuda
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Configuration
@PropertySource("classpath:debt.properties")
@Getter
public class DebtProperties {

    @Value("${api.client.loan.url}")
    private String loanApiUrl;

    @Value("${api.client.payment.url}")
    private String paymentApiUrl;
}
