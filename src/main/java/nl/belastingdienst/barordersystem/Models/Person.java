package nl.belastingdienst.barordersystem.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@Getter
@Setter
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue
    protected Long id;
    protected String name;
}
