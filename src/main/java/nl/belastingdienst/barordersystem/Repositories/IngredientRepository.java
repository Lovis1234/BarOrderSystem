package nl.belastingdienst.barordersystem.Repositories;


import net.bytebuddy.TypeCache;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


}
