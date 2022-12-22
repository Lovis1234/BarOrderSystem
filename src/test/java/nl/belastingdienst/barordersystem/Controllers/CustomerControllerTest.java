package nl.belastingdienst.barordersystem.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.belastingdienst.barordersystem.Dto.CustomerDto;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {
    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createCustomerAndExpectResponseCreated() throws Exception {
        //arrange
        List<FileDocument> invoices = new ArrayList<>();
        CustomerDto newCustomerDto = new CustomerDto(1L, "Test", invoices);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(newCustomerDto);
        String requestJsonError = ow.writeValueAsString(invoices);
        //act
        Mockito
                .when(customerService.createCustomer(newCustomerDto))
                .thenReturn(newCustomerDto);

        //assert good flow
        mockMvc
                .perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        //assert error
        mockMvc
                .perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonError))
                .andExpect(status().isBadRequest());


    }
}