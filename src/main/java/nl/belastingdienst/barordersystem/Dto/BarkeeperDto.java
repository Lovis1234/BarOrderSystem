package nl.belastingdienst.barordersystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.belastingdienst.barordersystem.Models.FileDocument;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarkeeperDto {
    private Long id;
    private String name;
    private List<FileDocument> invoices;

}
