package nl.belastingdienst.barordersystem.Repositories;

import nl.belastingdienst.barordersystem.Models.Barkeeper;
import nl.belastingdienst.barordersystem.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarkeeperRepository extends JpaRepository<Barkeeper, String> {
    Optional<Barkeeper> findById(Long id);
}
