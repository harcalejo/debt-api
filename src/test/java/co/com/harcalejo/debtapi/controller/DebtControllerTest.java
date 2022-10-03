package co.com.harcalejo.debtapi.controller;

import co.com.harcalejo.debtapi.service.DebtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DebtController.class)
class DebtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DebtService debtService;

    @BeforeEach
    void setup() {

    }

    @Test
    void shouldReturnLoanBalance() throws Exception {
        //given
        Long loanId = 1L;

        //when
        when(debtService.calculateDebtLoan(loanId))
                .thenReturn(10000.00);

        //then
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/debts/loan/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance")
                        .value(10000.00));
    }

}