package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.CreateDrinkDto;
import nl.belastingdienst.barordersystem.Dto.DrinkDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {
    DrinkRepository drinkRepository;
    @Autowired
    IngredientService ingredientService;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public ResponseEntity<byte[]> getDrinkImage(Long drinkId, HttpServletRequest request){

        FileDocument document = drinkRepository.findById(drinkId).get().getPicture();

//        this mediaType decides witch type you accept if you only accept 1 type
//        MediaType contentType = MediaType.IMAGE_JPEG;
//        this is going to accept multiple types

        String mimeType = request.getServletContext().getMimeType(document.getFileName());

//        for download attachment use next line
//        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
//        for showing image in browser
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());

    }

    public List<DrinkDto> getAllDrinks() {
        List<Drink> drinks = drinkRepository.findAll();
        List<DrinkDto> drinkDtos = new ArrayList<>();
        for(Drink drink : drinks){
            drinkDtos.add(fromDrink(drink));
        }
        return drinkDtos;
    }
    public DrinkDto getDrinkById(Long id) {
        Optional<Drink> DrinkOptional = drinkRepository.findById(id);
        if (!DrinkOptional.isPresent()) {
            throw new RecordNotFoundException("Drink not found");
        } else {
            Drink drink = DrinkOptional.get();
            DrinkDto drinkDto = fromDrink(drink);
            return drinkDto;
        }
    }
    public double getDrinkPrice(Long id) {
        return drinkRepository.findPriceDrink(id);
        }

    public Drink createDrink(CreateDrinkDto drinkDto) {
        Drink drink = toCustomDrink(drinkDto);
        drink.setPermanent(true);
        drinkRepository.save(drink);
        Drink drinkSaved = drinkRepository.findById(drink.getId()).get();
        drinkSaved.setPrice(getDrinkPrice(drinkSaved.getId()));
        drinkRepository.save(drinkSaved);
        return drink;

    }

    public Long deleteCustomDrinks(){
        Long deleted = drinkRepository.deleteByPermanent(false);
        return deleted;
    }

    public Drink createCustomDrink(CreateDrinkDto drinkDto) {
        Drink drink = toCustomDrink(drinkDto);
        drink.setPermanent(false);
        drink.setName(drinkDto.getName() + " - Custom drink");
        drinkRepository.save(drink);
        Drink drinkSaved = drinkRepository.findById(drink.getId()).get();
        drinkSaved.setPrice(getDrinkPrice(drinkSaved.getId()));
        drinkRepository.save(drinkSaved);
        return drink;

    }


    private DrinkDto fromDrink(Drink drink){
        DrinkDto drinkDto = new DrinkDto();
        drinkDto.setId(drink.getId());
        drinkDto.setName(drink.getName());
        drinkDto.setIngredients(drink.getIngredients());
        drinkDto.setPrice(drink.getPrice());
        return drinkDto;
    }

    private Drink toDrink(CreateDrinkDto drinkDto){
        Drink drink = new Drink();
        drink.setId(drink.getId());
        drink.setName(drinkDto.getName());
        Long[] ingredientIds = drinkDto.getIngredients();
        List<Ingredient> list = new ArrayList<>();
        for (Long ingredientId : ingredientIds){
            Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
            list.add(ingredient);
        }
        drink.setIngredients(list);
        return drink;
    }

    private Drink toCustomDrink(CreateDrinkDto drinkDto){
        Drink drink = new Drink();
        drink.setId(drink.getId());
        drink.setName(drinkDto.getName());
        Long[] ingredientIds = drinkDto.getIngredients();
        List<Ingredient> list = new ArrayList<>();
        for (Long ingredientId : ingredientIds){
            Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
            list.add(ingredient);
        }
        drink.setIngredients(list);

        return drink;
    }
}

