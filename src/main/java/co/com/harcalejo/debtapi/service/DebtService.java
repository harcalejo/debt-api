package co.com.harcalejo.debtapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * La interface {@code DebtService} es el componente encargado de definir las
 * capacidades del servicio de Deuda. En este caso la Deuda se debe calcular
 * teniendo en cuenta los o el prestamo solicitado y los pagos registrados.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
public interface DebtService {

    /**
     * Metodo encargado de calcular la deuda de un prestamo teniendo en cuenta
     * le monto del prestamo y los pagos registrados.
     *
     * @param loanId identificador unico del prestamo
     * @return valor de la deuda calculado
     */
    Double  calculateDebtLoan(Long loanId) throws JsonProcessingException;
}
