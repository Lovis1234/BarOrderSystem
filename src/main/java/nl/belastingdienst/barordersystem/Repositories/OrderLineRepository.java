package nl.belastingdienst.barordersystem.Repositories;


import nl.belastingdienst.barordersystem.Models.Customer;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Models.OrderLine;
import nl.belastingdienst.barordersystem.Models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findAllByStatusNotLike(Status status);

    List<OrderLine> findAllByCustomer_Id(Long id);

}
