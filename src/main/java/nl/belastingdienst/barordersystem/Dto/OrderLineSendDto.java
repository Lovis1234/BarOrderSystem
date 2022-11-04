package nl.belastingdienst.barordersystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.belastingdienst.barordersystem.Models.Barkeeper;
import nl.belastingdienst.barordersystem.Models.Customer;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.Status;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineSendDto {

    private Long id;
    private Long[] drinkList;
    private Long customer;
    private Barkeeper barkeeper;
    private double price;
    private Status status;
}
