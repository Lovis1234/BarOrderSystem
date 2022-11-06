package nl.belastingdienst.barordersystem.Repositories;


import nl.belastingdienst.barordersystem.Models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


}
