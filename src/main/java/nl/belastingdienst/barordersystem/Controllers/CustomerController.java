package nl.belastingdienst.barordersystem.Controllers;

import nl.belastingdienst.barordersystem.Dto.CustomerDto;
import nl.belastingdienst.barordersystem.Dto.CustomerGetDto;
import nl.belastingdienst.barordersystem.Dto.UserDto;
import nl.belastingdienst.barordersystem.Services.CustomerService;
import nl.belastingdienst.barordersystem.Services.DatabaseService;
import nl.belastingdienst.barordersystem.Services.DrinkService;
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

    CustomerService customerService;
    DatabaseService databaseService;
    DrinkService drinkService;

    public CustomerController(CustomerService customerService, DatabaseService databaseService, DrinkService drinkService) {
        this.customerService = customerService;
        this.databaseService = databaseService;
        this.drinkService = drinkService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerDto customerDto, BindingResult br) {
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()) {
            for (FieldError error : br.getFieldErrors()) {
                sb.append(error.getField() + ": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            customerService.createCustomer(customerDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(customerDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CustomerGetDto>> getAllCustomers() {
        List<CustomerGetDto> customerDtos = customerService.getAllCustomers();
        return ResponseEntity.ok(customerDtos);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto dto) {

        customerService.updateCustomer(id, dto);

        return ResponseEntity.noContent().build();
    }


}