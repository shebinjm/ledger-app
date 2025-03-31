package co.uk.simpleleger.ledger.controller;


import co.uk.simpleleger.ledger.model.Account;
import co.uk.simpleleger.ledger.service.AccountService;
import co.uk.simpleleger.ledger.service.LedgerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private LedgerService ledgerService;

    @Test
    void shouldCreateAccount() throws Exception {
        Account mockAccount = new Account("acc1", "John", 0.0);
        Mockito.when(accountService.createAccount("acc1", "John")).thenReturn(mockAccount);

        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"acc1\",\"name\":\"John\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("acc1"))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {
        Mockito.when(accountService.getAllAccounts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
