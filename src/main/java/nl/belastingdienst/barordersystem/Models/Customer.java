package nl.belastingdienst.barordersystem.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
@Getter
@Setter
@Entity
public class Customer extends Person{
    @OneToMany
    private List<FileDocument> invoices;

    public Customer(Long id, String name, List<FileDocument> invoices) {
        super(id, name);
        this.invoices = invoices;
    }

    public Customer() {
    }
}
