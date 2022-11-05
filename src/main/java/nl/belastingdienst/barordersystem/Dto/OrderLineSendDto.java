package nl.belastingdienst.barordersystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.belastingdienst.barordersystem.Models.Barkeeper;
import nl.belastingdienst.barordersystem.Models.Status;

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
