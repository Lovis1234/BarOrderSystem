package nl.belastingdienst.barordersystem.Repositories;


import nl.belastingdienst.barordersystem.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findById(Long customerId);
}
