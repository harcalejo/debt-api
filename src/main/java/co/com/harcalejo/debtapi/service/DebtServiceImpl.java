package co.com.harcalejo.debtapi.service;

import co.com.harcalejo.debtapi.config.DebtProperties;
import co.com.harcalejo.debtapi.dto.LoanDTO;
import co.com.harcalejo.debtapi.dto.PaymentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

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
public class DebtServiceImpl implements DebtService {

    /**
     * Constante para realizar el redondeo de un double a dos cifras decimales
     *
     * {@code Math.round(value * 100.0) / 100.0}
     */
    private static final double ROUND_DOUBLE_CONSTANT = 100.0;

    /**
     * Bean de configuración para el manejo de parametros de negocio
     */
    private final DebtProperties debtProperties;

    /**
     * Bean para el consumo de servicios rest
     */
    private final RestTemplate restTemplate;

    public DebtServiceImpl(DebtProperties debtProperties, RestTemplate restTemplate) {
        this.debtProperties = debtProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * Calcula el balance de la deuda, obteniendo el valor del
     * prestamo y restando el valor de los pagos realizados
     *
     * @param loanId identificador unico del prestamo
     * @return balance de la deuda
     * @throws JsonProcessingException en caso de que los String
     * no puedan ser transformados
     */
    @Override
    public Double calculateDebtLoan(Long loanId, LocalDate before)
            throws JsonProcessingException {
        final double loanAmount = getLoanAmount(loanId);
        final double paymentsAmount =
                getPaymentsAmount(loanId, before);

        return Math.round(
                (loanAmount - paymentsAmount)
                        * ROUND_DOUBLE_CONSTANT)/ROUND_DOUBLE_CONSTANT;
    }

    /**
     * Permite obtener el monto del prestamo usando la API de prestamos
     *
     * @param loanId identificador unico del prestamo
     * @return monto del prestamo
     */
    private double getLoanAmount(Long loanId) {
        final ResponseEntity<LoanDTO> response = restTemplate.getForEntity(
                debtProperties.getLoanApiUrl() + "/" + loanId, LoanDTO.class);

        return response
                .getBody().getAmount();
    }

    /**
     * Permite obtener la sumatoria de los montos pagados relacionados
     * a un prestamo
     *
     * @param loanId identificador unico del prestamo
     * @param before fecha para conocer la deuda antes de esta
     * @return valor total de los pagos realizados a un prestamo
     */
    private double getPaymentsAmount(Long loanId, LocalDate before) throws JsonProcessingException {
        final String response = restTemplate.getForObject(
                debtProperties
                        .getPaymentApiUrl()
                        + "/" + loanId + "?before=" + before,
                String.class);

        List<PaymentDTO> payments = transformJsonToPaymentList(response);

        return payments.stream()
                .mapToDouble(PaymentDTO::getAmount)
                .sum();
    }

    /**
     * Recibe un json con la lista de pagos y la transforma a un Lista java
     *
     * @param response json obtenido de la API consumida
     * @return Lista de pagos
     * @throws JsonProcessingException en caso que el objeto no pueda ser
     * parseado.
     */
    private List<PaymentDTO> transformJsonToPaymentList(String response)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        final CollectionType javaType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, PaymentDTO.class);

        return objectMapper.readValue(response, javaType);
    }
}
