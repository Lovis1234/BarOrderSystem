package nl.belastingdienst.barordersystem.Models;

import javax.persistence.*;
import lombok.*;

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
    private double price;
    private String type;
    @ManyToOne
    private FileDocument picture;

    @ManyToMany
    private List<Ingredient> ingredients;


}
