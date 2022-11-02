package nl.belastingdienst.barordersystem.Controllers;
import nl.belastingdienst.barordersystem.Dto.IngredientDto;
import nl.belastingdienst.barordersystem.Services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/ingredient")
public class IngredientController {

    IngredientService employeeService;

    public IngredientController(IngredientService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<IngredientDto>> getAllIngredients(){
        List<IngredientDto> employeeDtos = employeeService.getAllIngredients();
        return ResponseEntity.ok(employeeDtos);
    }



    @PostMapping(value = "/create")
    public ResponseEntity<Object> createIngredient(@Valid @RequestBody IngredientDto employeeDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if(br.hasErrors()){
            for(FieldError error : br.getFieldErrors()){
                sb.append(error.getField() + ": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            IngredientDto newIngredientDto = employeeService.createIngredient(employeeDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newIngredientDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }



}