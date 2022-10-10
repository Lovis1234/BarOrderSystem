package nl.belastingdienst.barordersystem.Controllers;
import nl.belastingdienst.barordersystem.Dto.DrinkDto;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import nl.belastingdienst.barordersystem.Services.DrinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping(value = "/employee")
public class DrinkController {

    DrinkService employeeService;

    public DrinkController(DrinkService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DrinkDto>> getAllDrinks(){
        List<DrinkDto> employeeDtos = employeeService.getAllDrinks();
        return ResponseEntity.ok(employeeDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkDto> getOneDrink(@PathVariable Long id){
        DrinkDto employeeDto = employeeService.getDrinkById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createDrink(@Valid @RequestBody DrinkDto employeeDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if(br.hasErrors()){
            for(FieldError error : br.getFieldErrors()){
                sb.append(error.getField() + ": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            DrinkDto newDrinkDto = employeeService.createDrink(employeeDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newDrinkDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }



}