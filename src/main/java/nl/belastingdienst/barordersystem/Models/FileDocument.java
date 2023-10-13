package nl.belastingdienst.barordersystem.Models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

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
    @Column(columnDefinition = "LONGBLOB")
    private byte[] docFile;
}
