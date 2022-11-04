package nl.belastingdienst.barordersystem.Controllers;


import nl.belastingdienst.barordersystem.FileUploadResponse.FileUploadResponse;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Services.DatabaseService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
public class UploadDownloadWithDatabaseController {

    private final DatabaseService databaseService;

    public UploadDownloadWithDatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("/upload")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type, @RequestParam("destinationId") Long id) throws IOException {
        FileDocument fileDocument = databaseService.uploadFileDocument(file,type,id);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(Objects.requireNonNull(fileDocument.getFileName())).toUriString();
        String contentType = file.getContentType();

        return new FileUploadResponse(fileDocument.getFileName(), contentType, url );
    }


    @GetMapping("/download/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        return databaseService.singleFileDownload(fileName, request);
    }

    @GetMapping("/getAll")
    public Collection<FileDocument> getAllFiles(){
            return databaseService.getALlFromDB();
    }

    @GetMapping("/drinkimage/{id}")
    ResponseEntity<byte[]> getDrinkImageByDrinkId(@PathVariable Long id, HttpServletRequest request) {
        return databaseService.getDrinkImage(id, request);
    }

    @GetMapping("/getInvoices/{id}")
    public void getAllInvoices(@PathVariable Long id, HttpServletResponse response) throws IOException {
        String[] list = databaseService.getALlFromCustomer(id);
        databaseService.getZipDownload(list,response);
    }
}
