package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.IngredientDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import nl.belastingdienst.barordersystem.Repositories.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
class IngredientServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    private Ingredient ingredient;

    private IngredientDto ingredientDto;

    @BeforeEach
    void setup() {
        ingredient = new Ingredient(1L, "Test", 10);
        ingredientDto = new IngredientDto(1L, "Test", 10.0);

    }

    @Test
    void getAllIngredients() {
        IngredientDto expectedIngredientDto = new IngredientDto(1L, "Test", 10.0);
        List<IngredientDto> expected = new ArrayList<>();
        expected.add(expectedIngredientDto);

        Mockito

                .when(ingredientRepository.findAll())
                .thenReturn(List.of(ingredient));

        List<IngredientDto> actual = ingredientService.getAllIngredients();
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).contains(expectedIngredientDto);
    }

    @Test
    void getIngredientById() {
        Ingredient expected = new Ingredient(1L, "Test", 10);

        Mockito
                .when(ingredientRepository.findById(ingredient.getId()))
                .thenReturn(Optional.of(ingredient));

        Ingredient actual = ingredientService.getIngredientById(ingredient.getId());

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getPrice(), expected.getPrice());
        assertEquals(actual.getName(), expected.getName());
        assertThrows(RecordNotFoundException.class,
                () -> ingredientService.getIngredientById(ingredient.getId() + 1),
                "Ingredient not found");
    }

    @Test
    void createIngredient() {
        Ingredient expected = new Ingredient(1L, "Test", 10.0);


        Mockito
                .when(ingredientRepository.save(ingredient))
                .thenReturn(ingredient);

        IngredientDto actual = ingredientService.createIngredient(ingredientDto);

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getPrice(), expected.getPrice());
    }

    @Test
    void deleteIngredient() {

        Mockito
                .when(ingredientRepository.findById(ingredient.getId()))
                .thenReturn(Optional.of(ingredient));


        ingredientService.deleteIngredient(ingredient.getId());

        Mockito.verify(ingredientRepository, Mockito.times(1)).delete(ingredient);
        assertThrows(RecordNotFoundException.class,
                () -> ingredientService.deleteIngredient(ingredient.getId() + 1),
                "Ingredient not found");
    }

    @Test
    void updateIngredient() {
        Mockito
                .when(ingredientRepository.findById(ingredient.getId()))
                .thenReturn(Optional.of(ingredient));
        ingredientService.updateIngredient(ingredient.getId(), ingredientDto);

        Mockito.verify(ingredientRepository, Mockito.times(1)).save(ingredient);
        assertThrows(RecordNotFoundException.class,
                () -> ingredientService.deleteIngredient(ingredient.getId() + 1),
                "Ingredient not found");
    }
}