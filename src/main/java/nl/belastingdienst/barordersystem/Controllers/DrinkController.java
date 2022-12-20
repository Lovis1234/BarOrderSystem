package nl.belastingdienst.barordersystem.Controllers;

import nl.belastingdienst.barordersystem.Dto.CreateDrinkDto;
import nl.belastingdienst.barordersystem.Dto.DrinkDto;
import nl.belastingdienst.barordersystem.Dto.DrinkGetDto;
import nl.belastingdienst.barordersystem.Dto.UserDto;
import nl.belastingdienst.barordersystem.Models.Drink;
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
@RequestMapping(value = "/drink")
public class DrinkController {

    DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DrinkGetDto>> getAllDrinks() {
        List<DrinkGetDto> drinkDtos = drinkService.getAllDrinks();
        return ResponseEntity.ok(drinkDtos);
    }

    @GetMapping(value = "/{id}/price")
    public ResponseEntity<Double> getDrinkPrice(@PathVariable Long id) {
        Double price = drinkService.getDrinkPrice(id);
        return ResponseEntity.ok(price);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkGetDto> getOneDrink(@PathVariable Long id) {
        DrinkGetDto drinkDto = drinkService.getDrinkById(id);
        return ResponseEntity.ok(drinkDto);
    }


    @PostMapping(value = "/custom")
    public ResponseEntity<String> createCustomDrink(@Valid @RequestBody CreateDrinkDto drinkDto, BindingResult br) {
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()) {
            for (FieldError error : br.getFieldErrors()) {
                sb.append(error.getField()).append(": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            Drink newDrink = drinkService.createCustomDrink(drinkDto);
            return ResponseEntity.ok(newDrink.getName() + " is now available for order at €" + newDrink.getPrice());
        }
    }

    @DeleteMapping(value = "/custom")
    public ResponseEntity<String> deleteCustomDrinks() {
        Long deleted = drinkService.deleteCustomDrinks();
        return ResponseEntity.ok("Deleted " + deleted + " custom drink(s).");
    }

    @PutMapping(value = "/ingredient")
    public ResponseEntity<String> addIngredient(@RequestParam("drinkId") Long drinkId, @RequestParam("ingredientId") Long ingredientId) {
        drinkService.addIngredient(drinkId, ingredientId);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/ingredient")
    public ResponseEntity<String> removeIngredient(@RequestParam("drinkId") Long drinkId, @RequestParam("ingredientId") Long ingredientId) {
        {
            drinkService.removeIngredient(drinkId, ingredientId);
            return ResponseEntity.ok().build();

        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createDrink(@Valid @RequestBody CreateDrinkDto drinkDto, BindingResult br) {
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()) {
            for (FieldError error : br.getFieldErrors()) {
                sb.append(error.getField()).append(": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            Drink newDrink = drinkService.createDrink(drinkDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newDrink.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteDink(@PathVariable("id") Long id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateDrink(@PathVariable("id") Long id, @RequestBody DrinkDto dto) {

        drinkService.updateDrink(id, dto);
        return ResponseEntity.noContent().build();
    }


}