package nl.belastingdienst.barordersystem.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
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
}
