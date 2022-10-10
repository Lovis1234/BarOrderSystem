package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.DrinkDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {
    DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
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
    public DrinkDto createDrink(DrinkDto drinkDto) {
        Drink drink = toDrink(drinkDto);
        Drink newDrink = drinkRepository.save(drink);
        DrinkDto dto = fromDrink(newDrink);
        return dto;

    }


    private DrinkDto fromDrink(Drink drink){
        DrinkDto drinkDto = new DrinkDto();
        drinkDto.setId(drink.getId());
        drinkDto.setName(drink.getName());
        drinkDto.setPrice(drink.getPrice());
        return drinkDto;
    }

    private Drink toDrink(DrinkDto drinkDto){
        Drink drink = new Drink();
        drink.setId(drink.getId());
        drink.setName(drinkDto.getName());
        drink.setPrice(drinkDto.getPrice());
        return drink;
    }
}

