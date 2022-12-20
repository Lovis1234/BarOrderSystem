package nl.belastingdienst.barordersystem.Dto;

import lombok.*;
import nl.belastingdienst.barordersystem.Models.Ingredient;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkGetDto {
    private Long id;
    private String name;
    private Double price;
    private List<IngredientByDrinkDto> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrinkGetDto that = (DrinkGetDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, ingredients);
    }
}