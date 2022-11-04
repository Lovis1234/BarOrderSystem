package nl.belastingdienst.barordersystem.Models;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

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
    private Double price;
    @ManyToOne
    private FileDocument picture;


    @ManyToMany
    private List<Ingredient> ingredients;

    private Boolean permanent;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
    public void removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", picture=" + picture +
                ", ingredients=" + ingredients +
                ", permanent=" + permanent +
                '}';
    }
}
