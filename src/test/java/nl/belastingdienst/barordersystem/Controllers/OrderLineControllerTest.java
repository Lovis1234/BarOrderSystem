package nl.belastingdienst.barordersystem.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class OrderLineControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllOrderAuthenticationtest() throws Exception {
        //Assert that not autorizes
        mockMvc
                .perform(get("/order")
                        .with(user("Stan")
                                .roles("CUSTOMER")))
                .andExpect(status().isForbidden());

        mockMvc
                .perform(get("/order")
                        .with(user("Stan")
                                .roles("STAFF")))
                .andExpect(status().isOk());
    }
}