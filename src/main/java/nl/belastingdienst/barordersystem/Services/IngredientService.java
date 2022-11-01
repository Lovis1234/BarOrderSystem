package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.IngredientDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import nl.belastingdienst.barordersystem.Repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientDto> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<IngredientDto> ingredientDtos = new ArrayList<>();
        for(Ingredient ingredient : ingredients){
            ingredientDtos.add(fromIngredient(ingredient));
        }
        return ingredientDtos;
    }
    public IngredientDto getIngredientById(Long id) {
        Optional<Ingredient> IngredientOptional = ingredientRepository.findById(id);
        if (!IngredientOptional.isPresent()) {
            throw new RecordNotFoundException("Ingredient not found");
        } else {
            Ingredient ingredient = IngredientOptional.get();
            IngredientDto ingredientDto = fromIngredient(ingredient);
            return ingredientDto;
        }
    }
    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = toIngredient(ingredientDto);
        Ingredient newIngredient = ingredientRepository.save(ingredient);
        IngredientDto dto = fromIngredient(newIngredient);
        return dto;

    }


    private IngredientDto fromIngredient(Ingredient ingredient){
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName());
//        ingredientDto.setPrice(ingredient.getPrice());
        return ingredientDto;
    }

    private Ingredient toIngredient(IngredientDto ingredientDto){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredient.getId());
        ingredient.setName(ingredientDto.getName());
//        ingredient.setPrice(ingredientDto.getPrice());
        return ingredient;
    }
}

