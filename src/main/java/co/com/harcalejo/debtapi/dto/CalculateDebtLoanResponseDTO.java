package co.com.harcalejo.debtapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase {@code CalculateDebtLoanResponseDTO} nos permite definir los atributos
 * de Balance que queremos transmitir a la capa cliente en la solicitud del Balance
 * de un Prestamo.
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateDebtLoanResponseDTO {
    private double balance;
}
