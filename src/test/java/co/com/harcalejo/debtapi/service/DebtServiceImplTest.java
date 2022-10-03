package co.com.harcalejo.debtapi.service;

import co.com.harcalejo.debtapi.config.DebtProperties;
import co.com.harcalejo.debtapi.dto.LoanDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class DebtServiceImplTest {

    @MockBean
    private DebtProperties debtProperties;

    @MockBean
    private RestTemplate restTemplate;

    private DebtService debtService;

    @BeforeEach
    void setup (){
        this.debtService =
                new DebtServiceImpl(debtProperties, restTemplate);
    }

    @Test
    void shouldReturnLoanBalance() throws JsonProcessingException {
        //given
        Long loanId = 1L;
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loanId);
        loanDTO.setAmount(20000.00);

        LocalDate now = LocalDate.now();

        String paymentsLoanResponse = "[\n" +
                "    {\n" +
                "        \"id\": 220,\n" +
                "        \"loanId\": 1,\n" +
                "        \"amount\": 5000.0,\n" +
                "        \"registerDate\": \"2022-05-05\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 385,\n" +
                "        \"loanId\": 1,\n" +
                "        \"amount\": 5000.0,\n" +
                "        \"registerDate\": \"2021-12-19\"\n" +
                "    }\n" +
                "]";

        //when
        when(restTemplate
                .getForEntity(
                        debtProperties
                                .getLoanApiUrl()+"/1", LoanDTO.class))
                .thenReturn(new ResponseEntity<>(loanDTO, HttpStatus.OK));

        when(restTemplate
                .getForObject(
                        debtProperties
                                .getPaymentApiUrl()
                                + "/" + loanId + "?before=" + now,
                        String.class))
                .thenReturn(paymentsLoanResponse);

        //then
        assertThat(debtService
                .calculateDebtLoan(loanId, now))
                .isEqualTo(10000.00);
    }
}