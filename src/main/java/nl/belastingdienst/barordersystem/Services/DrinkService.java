package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.*;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Customer;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import nl.belastingdienst.barordersystem.Repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {
    DrinkRepository drinkRepository;

    IngredientRepository ingredientRepository;

    IngredientService ingredientService;

    public DrinkService(DrinkRepository drinkRepository, IngredientRepository ingredientRepository, IngredientService ingredientService) {
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
    }

    public List<DrinkGetDto> getAllDrinks() {
        List<Drink> drinks = drinkRepository.findAll();
        List<DrinkGetDto> drinkDtos = new ArrayList<>();
        for(Drink drink : drinks){
            drinkDtos.add(fromDrinktoDrinkGetDto(drink));
        }
        return drinkDtos;
    }
    public DrinkGetDto getDrinkById(Long id) {
        Optional<Drink> DrinkOptional = drinkRepository.findById(id);
        if (DrinkOptional.isEmpty()) {
            throw new RecordNotFoundException("No drink found");
        } else {
            Drink drink = DrinkOptional.get();
            return fromDrinktoDrinkGetDto(drink);
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
        drinkSaved.setPrice(this.getDrinkPrice(drinkSaved.getId()));
        drinkRepository.save(drinkSaved);
        return drink;

    }

    public DrinkGetDto fromDrinktoDrinkGetDto(Drink drink){
        DrinkGetDto drinkDto = new DrinkGetDto();
        drinkDto.setId(drink.getId());
        drinkDto.setName(drink.getName());
        drinkDto.setIngredients(fromIngredientList(drink.getIngredients()));
        drinkDto.setPrice(drink.getPrice());
        return drinkDto;
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
    private void updatePrice(Long id){
        Optional<Drink> drinkOptional = drinkRepository.findById(id);
        if (drinkOptional.isPresent()) {
            Drink drink = drinkOptional.get();
            drink.setPrice(getDrinkPrice(drink.getId()));
            drinkRepository.save(drink);
        }
    }

    public void addIngredient(Long drinkId, Long ingredientId) {
        Optional<Drink> drinkOptional = drinkRepository.findById(drinkId);
        if (drinkOptional.isPresent()){
            Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
                Drink drink = drinkOptional.get();
                drink.addIngredient(ingredient);
                drinkRepository.save(drink);
                updatePrice(drinkId);
        } else throw new RecordNotFoundException("Drink not found");

    }

    public void removeIngredient(Long drinkId, Long ingredientId) {
        Optional<Drink> drinkOptional = drinkRepository.findById(drinkId);
        if (drinkOptional.isPresent()){
            Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
            Drink drink = drinkOptional.get();
            drink.removeIngredient(ingredient);
            drinkRepository.save(drink);
            updatePrice(drinkId);
        } else throw new RecordNotFoundException("Drink not found");

    }

    public void deleteDrink(Long id) {
        Optional<Drink> drinkOptional = drinkRepository.findById(id);
        if (drinkOptional.isPresent()) {
            drinkRepository.delete(drinkOptional.get());
        } else throw new RecordNotFoundException("Drink not found");
    }

    public void updateDrink(Long id, DrinkDto dto) {
        if (drinkRepository.findById(id).isEmpty()) throw new RecordNotFoundException("Drink not found");
        Drink drink = drinkRepository.findById(id).get();
        drink.setName(dto.getName());
        drink.setId(dto.getId());
        drink.setIngredients(dto.getIngredients());
        drink.setPrice(dto.getPrice());
        drinkRepository.save(drink);
    }

    public List<DrinkGetDto> fromDrinkList(List<Drink> drinkList){
        List<DrinkGetDto> drinkGetDtos = new ArrayList<>();
        for (Drink drink : drinkList) {
            DrinkGetDto drinkDto = new DrinkGetDto();
            drinkDto.setId(drink.getId());
            drinkDto.setName(drink.getName());
            drinkDto.setIngredients(fromIngredientList(drink.getIngredients()));
            drinkDto.setPrice(drink.getPrice());
            drinkGetDtos.add(drinkDto);
        }
        return drinkGetDtos;
    }

    private List<IngredientByDrinkDto> fromIngredientList(List<Ingredient> ingredientList){
        List<IngredientByDrinkDto> ingredientByDrinkDtoList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            IngredientByDrinkDto ingredientByDrinkDto = new IngredientByDrinkDto();
            ingredientByDrinkDto.setName(ingredient.getName());
            ingredientByDrinkDtoList.add(ingredientByDrinkDto);
        }
        return ingredientByDrinkDtoList;
    }
}

