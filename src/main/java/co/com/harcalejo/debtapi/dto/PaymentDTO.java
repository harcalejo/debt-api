package co.com.harcalejo.debtapi.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    private Long loanId;
    private double amount;
    private String registerDate;
}
