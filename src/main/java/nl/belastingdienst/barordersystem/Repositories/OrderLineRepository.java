package nl.belastingdienst.barordersystem.Repositories;


import nl.belastingdienst.barordersystem.Models.OrderLine;
import nl.belastingdienst.barordersystem.Models.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findAllByStatusNotLike(Status status);

    List<OrderLine> findAllByCustomer_Id(Long id);

}
