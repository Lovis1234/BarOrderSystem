package nl.belastingdienst.barordersystem.Repositories;


import nl.belastingdienst.barordersystem.Models.Drink;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
