package nl.belastingdienst.barordersystem.Controllers;
import nl.belastingdienst.barordersystem.Dto.CustomerDto;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Services.CustomerService;
import nl.belastingdienst.barordersystem.Services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    CustomerService employeeService;
    @Autowired
    DatabaseService databaseService;

    public CustomerController(CustomerService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/getInvoices/{id}")
    public void getAllFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
        String[] list = databaseService.getALlFromCustomer(id);
        databaseService.getZipDownload(list,response);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<CustomerDto> employeeDtos = employeeService.getAllCustomers();
        return ResponseEntity.ok(employeeDtos);
    }



    @PostMapping(value = "/create")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerDto employeeDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if(br.hasErrors()){
            for(FieldError error : br.getFieldErrors()){
                sb.append(error.getField() + ": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            CustomerDto newCustomerDto = employeeService.createCustomer(employeeDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newCustomerDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }



}