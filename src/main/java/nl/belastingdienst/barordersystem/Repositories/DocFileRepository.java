package nl.belastingdienst.barordersystem.Repositories;

import nl.belastingdienst.barordersystem.Models.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface DocFileRepository extends JpaRepository<FileDocument, Long> {
    FileDocument findByFileName(String fileName);
}
