package no.sample.pricecalculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PriceCalculatorApplication.class)
@AutoConfigureMockMvc
class PriceCalculatorApplicationIT {

    @Autowired
    private MockMvc mvc;

    @Test
    void testCheckout() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/checkout")
                        .contentType("application/json")
                        .content("[\"001\", \"002\",\"001\",\"004\",\"001\",\"001\",\"003\"]"))
                .andExpect(status().isOk()).andExpect(content().string("{\"price\":460}"));
    }

    @Test
    void testInvalidInput() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/checkout")
                                .contentType("application/json")
                                .content("[\"\"]"))
                .andExpect(status().isBadRequest()).andExpect(content().string("{\"errors\":[{\"errorCode\":\"invalid input\",\"errorMessage\":\"Invalid watch id supplied\"}]}"));
    }

    @Test
    void testWrongWatchId() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/checkout")
                                .contentType("application/json")
                                .content("[\"101\"]"))
                .andExpect(status().isBadRequest()).andExpect(content().string("{\"errors\":[{\"errorCode\":\"invalid\",\"errorMessage\":\"Invalid watch id supplied\"}]}"));
    }

}