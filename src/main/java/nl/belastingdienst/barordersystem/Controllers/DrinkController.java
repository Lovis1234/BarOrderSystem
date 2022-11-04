package nl.belastingdienst.barordersystem.Controllers;
import nl.belastingdienst.barordersystem.Dto.CreateDrinkDto;
import nl.belastingdienst.barordersystem.Dto.DrinkDto;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Services.DrinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.text.DecimalFormat;
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
        Double price = drinkService.getDrinkPrice(id);
        return ResponseEntity.ok(price);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkDto> getOneDrink(@PathVariable Long id){
        DrinkDto drinkDto = drinkService.getDrinkById(id);
        return ResponseEntity.ok(drinkDto);
    }


    @PostMapping(value = "/createCustom")
    public ResponseEntity<String> createCustomDrink(@Valid @RequestBody CreateDrinkDto drinkDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if(br.hasErrors()){
            for(FieldError error : br.getFieldErrors()){
                sb.append(error.getField() + ": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            Drink newDrink = drinkService.createCustomDrink(drinkDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newDrink.getId()).toUri();
            DecimalFormat df = new DecimalFormat("###.##");
            return ResponseEntity.ok(newDrink.getName() + " is nu te bestellen voor €" + df.format(newDrink.getPrice()) );
        }
    }

    @DeleteMapping(value = "/deleteCustomDrinks")
    public ResponseEntity<String> deleteCustomDrinks(){
        Long deleted = drinkService.deleteCustomDrinks();
        return ResponseEntity.ok("Deleted " + deleted + " custom drink(s).");
    }
    @PutMapping(value = "/addIngredientToDrink")
    public ResponseEntity<String> addIngredient(@RequestParam("drinkId") Long drinkId, @RequestParam("ingredientId") Long ingredientId)
        {

                drinkService.addIngredient(drinkId, ingredientId);
                return ResponseEntity.ok().build();

        }

    @PutMapping(value = "/removeIngredientFromDrink")
    public ResponseEntity<String> removeIngredient(@RequestParam("drinkId") Long drinkId, @RequestParam("ingredientId") Long ingredientId) {
        {
                drinkService.removeIngredient(drinkId, ingredientId);
                return ResponseEntity.ok().build();

        }
    }
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createDrink(@Valid @RequestBody CreateDrinkDto drinkDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if(br.hasErrors()){
            for(FieldError error : br.getFieldErrors()){
                sb.append(error.getField() + ": ");
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
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteDink(@PathVariable("id") Long id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.noContent().build();
    }



}