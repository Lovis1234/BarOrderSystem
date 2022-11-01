package nl.belastingdienst.barordersystem.Dto;

import javax.persistence.*;
import lombok.*;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Models.Ingredient;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDto {
    private Long id;
    private String name;
    private List<Ingredient> ingredients;


}