package co.com.harcalejo.debtapi.dto;

import lombok.Data;

@Data
public class LoanDTO {
    private Long id;
    private double amount;
    private double installment;
    private int term;
    private Long user_id;
    private TargetDTO target;
    private String date;
}
