package co.uk.simpleleger.ledger.controller;


import co.uk.simpleleger.ledger.service.LedgerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LedgerController.class)
class LedgerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LedgerService ledgerService;

    @Test
    void shouldDepositMoney() throws Exception {
        mockMvc.perform(post("/api/v1/ledger/deposit/acc1?amount=100.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposited 100.0 to account acc1"));

        Mockito.verify(ledgerService).deposit("acc1", 100.0);
    }

    @Test
    void shouldWithdrawMoney() throws Exception {
        mockMvc.perform(post("/api/v1/ledger/withdraw/acc1?amount=50.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Withdrew 50.0 from account acc1"));

        Mockito.verify(ledgerService).withdraw("acc1", 50.0);
    }

    @Test
    void shouldReturnTransactions() throws Exception {
        Mockito.when(ledgerService.getTransactions("acc1")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/ledger/transactions/history/acc1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
