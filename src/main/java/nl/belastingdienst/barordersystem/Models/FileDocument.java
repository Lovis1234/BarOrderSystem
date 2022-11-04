package nl.belastingdienst.barordersystem.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FileDocument {

    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @Lob
    private byte[] docFile;

    @Override
    public String toString() {
        return "FileDocument{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
