package co.uk.simpleleger.ledger.integration;

import co.uk.simpleleger.ledger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LedgerAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private boolean skipTeardown = false;

    private final String BASE_ACCOUNTS = "/api/v1/accounts";
    private final String BASE_LEDGER = "/api/v1/ledger";

    @BeforeEach
    void setup() throws Exception {
        Account account = new Account("1", "Sam", 0.0);
        mockMvc.perform(post(BASE_ACCOUNTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isCreated());
    }

    @AfterEach
    void teardown() throws Exception {
        if (!skipTeardown) {
            mockMvc.perform(delete(BASE_ACCOUNTS + "/1"))
                    .andExpect(status().isOk());
        }
        skipTeardown = false;
    }

    @Test
    void fullFlowTest() throws Exception {
        mockMvc.perform(post(BASE_LEDGER + "/deposit/1?amount=100"))
                .andExpect(status().isOk());

        mockMvc.perform(post(BASE_LEDGER + "/withdraw/1?amount=40"))
                .andExpect(status().isOk());

        mockMvc.perform(get(BASE_LEDGER + "/balance/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("60.0"));


        mockMvc.perform(get(BASE_LEDGER + "/transactions/history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void withdrawWithoutDepositShouldFail() throws Exception {
        mockMvc.perform(post(BASE_LEDGER + "/withdraw/1?amount=10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No transactions found for account: 1"));
    }

    @Test
    void depositInvalidAmountShouldFail() throws Exception {
        mockMvc.perform(post(BASE_LEDGER + "/deposit/acc1?amount=0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void withdrawInsufficientFundsShouldFail() throws Exception {
        mockMvc.perform(post(BASE_LEDGER + "/deposit/1?amount=50"))
                .andExpect(status().isOk());

        mockMvc.perform(post(BASE_LEDGER + "/withdraw/1?amount=100"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds"));
    }

    @Test
    void deleteAccountAndLedgerTest() throws Exception {
        mockMvc.perform(delete(BASE_ACCOUNTS + "/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get(BASE_LEDGER + "/balance/1"))
                .andExpect(status().isBadRequest());

        skipTeardown = true;
    }
}
