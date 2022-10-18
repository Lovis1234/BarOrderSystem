package nl.belastingdienst.barordersystem.Repositories;


import net.bytebuddy.TypeCache;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query(value = "SELECT price FROM ingredients AS i WHERE i.id = ?1", nativeQuery = true)
    List<Ingredient> findIngredientPriceById(String id);

    @Query(value = "SELECT ingredients_id FROM drinks_ingredients AS i WHERE i.drink_id = ?1", nativeQuery = true)
    List<Ingredient> findIngredientByDrinkId(Long id);


}
