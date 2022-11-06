package nl.belastingdienst.barordersystem.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.belastingdienst.barordersystem.Models.Enums.Status;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderLine {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<Drink> drinkList;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Barkeeper barkeeper;
    private double price;
    private Status status;

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", drinkList=" + drinkList +
                ", customer=" + customer +
                ", barkeeper=" + barkeeper +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
