package co.com.harcalejo.debtapi.service;

import co.com.harcalejo.debtapi.config.DebtProperties;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * La clase {@code DebtService} es la implementación de la interfaz
 * {@link DebtService}.
 * <p>
 * Service - Anotación del framework Spring que permite definir el bean y
 * su prototipo.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Service
public class DebtServiceImpl implements DebtService{

    /**
     * Bean de configuración para el manejo de parametros de negocio
     */
    private final DebtProperties debtProperties;

    public DebtServiceImpl(DebtProperties debtProperties) {
        this.debtProperties = debtProperties;
    }

    @Override
    public Double calculateDebtLoan(Long loanId) {
        final double loanAmount = getLoanAmount(loanId);

        return loanAmount;
    }

    private double getLoanAmount(Long loanId) {
        RestTemplate restTemplate = new RestTemplate();
        final String result = restTemplate.getForObject(
                debtProperties.getLoanApiUrl() + "/" + loanId, String.class);

        final JSONObject loanJson = new JSONObject(result);
        return loanJson.getDouble("amount");
    }
}
