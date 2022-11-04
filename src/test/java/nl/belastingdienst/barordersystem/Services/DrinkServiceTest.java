package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.CreateDrinkDto;
import nl.belastingdienst.barordersystem.Dto.DrinkDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.config.saml2.RelyingPartyRegistrationsBeanDefinitionParser;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
class DrinkServiceTest {

    Drink drink;
    Drink drinkWithIngredient;
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
    @Mock
    IngredientRepository ingredientRepository;

    @BeforeEach
    public void setup(){
        drink = new Drink(1L,"Red Bull",0.0,null,null,true);
        list = new ArrayList<>();
        listFilled = new ArrayList<>();
        listFilled.add(ingredient);

        ingredient = new Ingredient(2L,"Red Bull",3.5);
        ingredient2 = new Ingredient(3L,"Vodka",4);

    }

    @Test
    void getAllDrinks() {
        DrinkDto expectedDrinkDto = new DrinkDto(1L,"Red Bull",0.0,null);
        List<DrinkDto> expected = new ArrayList<>();
        expected.add(expectedDrinkDto);

        Mockito

        .when(drinkRepository.findAll())
                .thenReturn(List.of(drink));

        List<DrinkDto> actual = drinkService.getAllDrinks();
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).contains(expectedDrinkDto);
    }

    @Test
    void getDrinkById() {
        DrinkDto expected = new DrinkDto(1L,"Red Bull",0.0,null);

        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drink));

        DrinkDto actual = drinkService.getDrinkById(drink.getId());

        assertEquals(actual.getId(),expected.getId());
        assertEquals(actual.getPrice(),expected.getPrice());
        assertEquals(actual.getIngredients(),expected.getIngredients());
        assertEquals(actual.getName(),expected.getName());
        assertThrows(RecordNotFoundException.class,
                ()->{drinkService.getDrinkById(drink.getId()+1);},
                "Drink not found");
    }

    @Test
    void getDrinkPriceTest() {
        drink.setIngredients(listFilled);
        Double expected = drink.getPrice();

        Mockito
                .when(drinkRepository.findPriceDrink(drink.getId()))
                .thenReturn(drink.getPrice());

        Double actual = drinkService.getDrinkPrice(drink.getId());

        assertEquals(actual,expected);

    }

    @Test
    void createDrink() {
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
    void deleteCustomDrinks() {
        Mockito
                .when(drinkRepository.deleteByPermanent(false))
                .thenReturn(drink.getId());

        Long actual = drinkService.deleteCustomDrinks();

        assertEquals(drink.getId(), actual);
    }

    @Test
    void createCustomDrink() {
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

        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drinkSaved));

        Drink actual = drinkService.createCustomDrink(input);

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getPermanent(),actual.getPermanent());
        assertEquals(expected.getIngredients(),actual.getIngredients());
        assertEquals(expected.getPrice(),actual.getPrice());
        assertEquals(expected.getName(),actual.getName());
        assertEquals(expected.getPicture(),actual.getPicture());




    }
    @Test
    void addIngredient() {
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
    void removeIngredient() {
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
    void deleteDrink(){

        Mockito
                .when(drinkRepository.findById(drink.getId()))
                .thenReturn(Optional.of(drink));


        drinkService.deleteDrink(drink.getId());

        Mockito.verify(drinkRepository, Mockito.times(1)).delete(drink);
        assertThrows(RecordNotFoundException.class,
                ()->{drinkService.deleteDrink(drink.getId()+1);},
                "Drink not found");
    }




}