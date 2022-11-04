package nl.belastingdienst.barordersystem.Repositories;


import nl.belastingdienst.barordersystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
