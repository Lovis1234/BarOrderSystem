package nl.belastingdienst.barordersystem.Repositories;


import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;


public interface DrinkRepository extends JpaRepository<Drink, Long> {
    @Query(value = "SELECT SUM(i.price) FROM ingredients AS i inner join drinks_ingredients di on i.id = di.ingredients_id WHERE di.drink_id = ?1", nativeQuery = true)
    Double findPriceDrink(Long id);
    @Transactional
    Long deleteByPermanent(boolean permanent);

    Drink getById(Long id);
}
