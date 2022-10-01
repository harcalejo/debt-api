package co.com.harcalejo.debtapi.controller;

import co.com.harcalejo.debtapi.dto.CalculateDebtLoanResponseDTO;
import co.com.harcalejo.debtapi.service.DebtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debts")
public class DebtController {

    private final DebtService debtService;

    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @GetMapping(value = "/loan/{loanId}")
    public ResponseEntity<CalculateDebtLoanResponseDTO> calculateDebtLoan(
            @PathVariable Long loanId) {
        CalculateDebtLoanResponseDTO responseDTO =
                new CalculateDebtLoanResponseDTO();
        responseDTO.setBalance(
                debtService.calculateDebtLoan(loanId));

        return new ResponseEntity<>(
                responseDTO, HttpStatus.CREATED);
    }
}
