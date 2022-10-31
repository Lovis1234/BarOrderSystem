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
@RequestMapping(value = "/drink")
public class DrinkController {

    DrinkService drinkService;

    public DrinkController(DrinkService drinkService){
        this.drinkService = drinkService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DrinkDto>> getAllDrinks(){
        List<DrinkDto> drinkDtos = drinkService.getAllDrinks();
        return ResponseEntity.ok(drinkDtos);
    }

    @GetMapping(value = "/price/{id}")
    public ResponseEntity<Double> getDrinkPrice(@PathVariable Long id){
        System.out.println("jemoeder" + id);
        Double price = drinkService.getDrinkPrice(id);
        return ResponseEntity.ok(price);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkDto> getOneDrink(@PathVariable Long id){
        DrinkDto drinkDto = drinkService.getDrinkById(id);
        return ResponseEntity.ok(drinkDto);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createDrink(@Valid @RequestBody DrinkDto drinkDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if(br.hasErrors()){
            for(FieldError error : br.getFieldErrors()){
                sb.append(error.getField() + ": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            DrinkDto newDrinkDto = drinkService.createDrink(drinkDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newDrinkDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }



}