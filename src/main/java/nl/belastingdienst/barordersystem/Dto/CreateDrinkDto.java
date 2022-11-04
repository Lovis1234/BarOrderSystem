package nl.belastingdienst.barordersystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDrinkDto {
    private String name;
    private Long[] ingredients;

    @Override
    public String toString() {
        return "CreateDrinkDto{" +
                "name='" + name + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                '}';
    }
}
