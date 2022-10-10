package nl.belastingdienst.barordersystem.Dto;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDto {
    private Long id;
    private String name;
    private double price;
    private String type;


}