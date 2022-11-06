package nl.belastingdienst.barordersystem.Repositories;

import nl.belastingdienst.barordersystem.Models.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface DocFileRepository extends JpaRepository<FileDocument, Long> {
    FileDocument findByFileName(String fileName);
    @Query(value = "SELECT id, doc_file, file_name FROM file_document AS f inner join customer_invoices ci on f.id = ci.invoices_id WHERE ci.customer_id = ?1", nativeQuery = true)
    List<FileDocument> findAllByCustomer(Long id);
}
