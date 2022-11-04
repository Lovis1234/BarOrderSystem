package nl.belastingdienst.barordersystem.Dto;

import javax.persistence.*;
import lombok.*;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Models.Ingredient;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDto {
    private Long id;
    private String name;
    private Double price;
    private List<Ingredient> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrinkDto drinkDto = (DrinkDto) o;
        return Objects.equals(id, drinkDto.id) && Objects.equals(name, drinkDto.name) && Objects.equals(price, drinkDto.price) && Objects.equals(ingredients, drinkDto.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, ingredients);
    }
}