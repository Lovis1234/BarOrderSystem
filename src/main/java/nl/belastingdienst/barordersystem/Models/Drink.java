package nl.belastingdienst.barordersystem.Models;

import javax.persistence.*;
import lombok.*;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import nl.belastingdienst.barordersystem.Repositories.IngredientRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Drinks")
public class Drink {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String type;
    @ManyToOne
    private FileDocument picture;


    @ManyToMany
    private List<Ingredient> ingredients;



}
