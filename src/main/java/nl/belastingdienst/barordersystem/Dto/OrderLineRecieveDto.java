package nl.belastingdienst.barordersystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.belastingdienst.barordersystem.Models.Barkeeper;
import nl.belastingdienst.barordersystem.Models.Enums.Status;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineRecieveDto {
    private Long id;
    private Double price;
    private Status status;
    private Barkeeper barkeeper;
    private List<DrinkGetDto> drinkList;
}
