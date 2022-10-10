package nl.belastingdienst.barordersystem.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bill {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Customer customer;
    private double subtotal;
    private double tip;
    private double taxes;
    private double total;
}
