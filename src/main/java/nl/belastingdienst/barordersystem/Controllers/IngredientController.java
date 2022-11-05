package nl.belastingdienst.barordersystem.Controllers;

import nl.belastingdienst.barordersystem.Dto.IngredientDto;
import nl.belastingdienst.barordersystem.Services.IngredientService;
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
@RequestMapping(value = "/ingredient")
public class IngredientController {

    IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<IngredientDto>> getAllIngredients(){
        List<IngredientDto> ingredientDtos = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredientDtos);
    }



    @PostMapping(value = "/create")
    public ResponseEntity<Object> createIngredient(@Valid @RequestBody IngredientDto ingredientDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if(br.hasErrors()){
            for(FieldError error : br.getFieldErrors()){
                sb.append(error.getField() + ": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            IngredientDto newIngredientDto = ingredientService.createIngredient(ingredientDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newIngredientDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }



}