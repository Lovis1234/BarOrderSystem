package nl.belastingdienst.barordersystem.Controllers;
import nl.belastingdienst.barordersystem.Dto.CustomerDto;
import nl.belastingdienst.barordersystem.Services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    CustomerService employeeService;

    public CustomerController(CustomerService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<CustomerDto> employeeDtos = employeeService.getAllCustomers();
        return ResponseEntity.ok(employeeDtos);
    }

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<CustomerDto> getOneCustomer(@PathVariable Long id){
//        CustomerDto employeeDto = employeeService.getCustomerById(id);
//        return ResponseEntity.ok(employeeDto);
//    }

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