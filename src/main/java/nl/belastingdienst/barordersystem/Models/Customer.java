package nl.belastingdienst.barordersystem.Models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Person{
    @OneToMany
    private List<Bill> bills;

}
