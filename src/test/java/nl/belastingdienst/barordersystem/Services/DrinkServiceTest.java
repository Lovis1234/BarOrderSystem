package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.CreateDrinkDto;
import nl.belastingdienst.barordersystem.Dto.DrinkDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import nl.belastingdienst.barordersystem.Repositories.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DrinkServiceTest {

    Drink drink;

    DrinkDto drinkDto;
    Ingredient ingredient;
    Ingredient ingredient2;
    @InjectMocks
    DrinkService drinkService;

    private ServletWebServerApplicationContext context1 = new ServletWebServerApplicationContext();

    private List<Ingredient> list;
    private List<Ingredient> listFilled;
    @Mock
    DrinkRepository drinkRepository;
    @Mock
    IngredientService ingredientService;

    @BeforeEach
    public void setup(){
        drink = new Drink(1L,"Red Bull",0.0,null,null,true);
        drinkDto = new DrinkDto(1L,"Red Bull",0.0,null);
        list = new ArrayList<>();
        listFilled = new ArrayList<>();
        listFilled.add(ingredient);

        ingredient = new Ingredient(2L,"Red Bull",3.5);
        ingredient2 = new Ingredient(3L,"Vodka",4);

    }

    @Test
    void getAllDrinks() {
        //arange
        DrinkDto expectedDrinkDto = new DrinkDto(1L,"Red Bull",0.0,null);
        List<DrinkDto> expected = new ArrayList<>();
        expected.add(expectedDrinkDto);

        //act
        Mockito

        .when(drinkRepository.findAll())
                .thenReturn(List.of(drink));

        List<DrinkDto> actual = drinkService.getAllDrinks();
        //assert
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).contains(expectedDrinkDto);
    }

    @Test
    void getDrinkByIdGoodFlowAndDrinkNotFoundError() {
        //arange
        DrinkDto expected = new DrinkDto(1L,"Red Bull",0.0,null);
        //act
        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drink));

        DrinkDto actual = drinkService.getDrinkById(drink.getId());
        //assert
        assertEquals(actual.getId(),expected.getId());
        assertEquals(actual.getPrice(),expected.getPrice());
        assertEquals(actual.getIngredients(),expected.getIngredients());
        assertEquals(actual.getName(),expected.getName());
        assertThrows(RecordNotFoundException.class,
                ()->{drinkService.getDrinkById(drink.getId()+1);},
                "Drink not found");
    }

    @Test
    void getDrinkPriceEqualsExpected() {
        drink.setIngredients(listFilled);
        Double expected = drink.getPrice();

        Mockito
                .when(drinkRepository.findPriceDrink(drink.getId()))
                .thenReturn(drink.getPrice());

        Double actual = drinkService.getDrinkPrice(drink.getId());

        assertEquals(actual,expected);

    }

    @Test
    void createDrinkReturnsExpected() {
        CreateDrinkDto input = new CreateDrinkDto("Red Bull",null);
        drink = new Drink(null,"Red Bull - Custom drink",null,null,null,false);
        Drink drinkSaved = drink;
        Long[] longArray = new Long[1];
        longArray[0] = null;
        input.setIngredients(longArray);

        Drink expected = new Drink(null,"Red Bull",null,null,null,true);
        List<Ingredient> listIngredient = new ArrayList<>();
        listIngredient.add(null);
        expected.setIngredients(listIngredient);

        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drinkSaved));

        Drink actual = drinkService.createDrink(input);

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getPermanent(),actual.getPermanent());
        assertEquals(expected.getIngredients(),actual.getIngredients());
        assertEquals(expected.getPrice(),actual.getPrice());
        assertEquals(expected.getName(),actual.getName());
        assertEquals(expected.getPicture(),actual.getPicture());
    }

    @Test
    void deleteCustomDrinksReturnsExpectedId() {
        Mockito
                .when(drinkRepository.deleteByPermanent(false))
                .thenReturn(drink.getId());

        Long actual = drinkService.deleteCustomDrinks();

        assertEquals(drink.getId(), actual);
    }

    @Test
    void createCustomDrinkReturnsExpectedDrink() {
        // arange
        CreateDrinkDto input = new CreateDrinkDto("Red Bull",null);
        drink = new Drink(null,"Red Bull - Custom drink",null,null,null,false);
        Drink drinkSaved = drink;
        Long[] longArray = new Long[1];
        longArray[0] = null;
        input.setIngredients(longArray);


        Drink expected = new Drink(null,"Red Bull - Custom drink",null,null,null,false);
        List<Ingredient> listIngredient = new ArrayList<>();
        listIngredient.add(null);
        expected.setIngredients(listIngredient);
        //act

        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drinkSaved));

        Drink actual = drinkService.createCustomDrink(input);
        //assert

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getPermanent(),actual.getPermanent());
        assertEquals(expected.getIngredients(),actual.getIngredients());
        assertEquals(expected.getPrice(),actual.getPrice());
        assertEquals(expected.getName(),actual.getName());
        assertEquals(expected.getPicture(),actual.getPicture());




    }
    @Test
    void addIngredientSavesIngredientAndByWrongIdReturnsCustomError() {
    Drink drinkExpected = new Drink(1L,"Red Bull Vodka",0.0,null,null,null);

    drink.setIngredients(listFilled);
    drinkExpected.setIngredients(list);

        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drink));
        Mockito
                .when(ingredientService.getIngredientById(ingredient.getId()))
                .thenReturn(ingredient);
        Mockito
                .when(drinkRepository.save(drink))
                .thenReturn(drink);

        drinkService.addIngredient(drink.getId(), ingredient.getId());

        Mockito.verify(drinkRepository, Mockito.times(1)).save(drink);
        assertThrows(RecordNotFoundException.class,
                ()->{drinkService.addIngredient(drink.getId()+1,ingredient.getId());},
                "Drink not found");
    }
    @Test
    void removeIngredientAndSaveUpdatedDrink() {
        drink.setIngredients(listFilled);


        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drink));
        Mockito
                .when(ingredientService.getIngredientById(ingredient.getId()))
                .thenReturn(ingredient);
        Mockito
                .when(drinkRepository.save(drink))
                .thenReturn(drink);

        drinkService.removeIngredient(drink.getId(), ingredient.getId());

        Mockito.verify(drinkRepository, Mockito.times(1)).save(drink);
        assertThrows(RecordNotFoundException.class,
                () -> {
                    drinkService.removeIngredient(drink.getId() + 1, ingredient.getId());
                },
                "Drink not found");
    }
    @Test
    void deleteDrinkReachesDeleteAndThrowsRightError(){

        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drink));


        drinkService.deleteDrink(drink.getId());

        Mockito.verify(drinkRepository, Mockito.times(1)).delete(drink);
        assertThrows(RecordNotFoundException.class,
                ()->{drinkService.deleteDrink(drink.getId()+1);},
                "Drink not found");
    }

    @Test
    void updateDrinkAndThrowsRightError(){
        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drink));


        drinkService.updateDrink(drink.getId(),drinkDto);

        Mockito.verify(drinkRepository, Mockito.times(1)).save(drink);
        assertThrows(RecordNotFoundException.class,
                ()->{drinkService.deleteDrink(drink.getId()+1);},
                "Drink not found");
    }




}