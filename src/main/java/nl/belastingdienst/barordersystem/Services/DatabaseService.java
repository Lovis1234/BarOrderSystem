package nl.belastingdienst.barordersystem.Services;


import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Customer;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.Enums.TypeDocument;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Repositories.CustomerRepository;
import nl.belastingdienst.barordersystem.Repositories.DocFileRepository;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DatabaseService {
    private final DocFileRepository doc;
    private CustomerRepository customerRepository;
    private DrinkRepository drinkRepository;

    public DatabaseService(DocFileRepository doc, CustomerRepository customerRepository, DrinkRepository drinkRepository) {
        this.doc = doc;
        this.customerRepository = customerRepository;
        this.drinkRepository = drinkRepository;
    }

    public Collection<FileDocument> getALlFromDB() {
        return doc.findAll();
    }

    public String[] getALlFromCustomer(Long id) {
        List<FileDocument> invoices = doc.findAllByCustomer(id);
        String[] filenames = new String[invoices.size()];
        int i = 0;
            for (FileDocument invoice : invoices) {
                filenames[i] = invoice.getFileName();
                i++;
        }
        return filenames;
    }


    public FileDocument uploadFileDocument(MultipartFile file, TypeDocument type, Long destinationId) throws IOException {
        Long count = doc.count();
        String name = StringUtils.cleanPath(count + file.getOriginalFilename());
        FileDocument fileDocument = new FileDocument();
        fileDocument.setFileName(name);
        fileDocument.setDocFile(file.getBytes());
        doc.save(fileDocument);

        if (type == TypeDocument.INVOICE){
            insertInvoice(destinationId,fileDocument);
        } else if (type == TypeDocument.DRINKPICTURE) {
            if (drinkRepository.findById(destinationId).isPresent()){
                Drink drink = drinkRepository.findById(destinationId).get();
                drink.setPicture(fileDocument);
                drinkRepository.save(drink);
        } else throw new RecordNotFoundException("Drink not found!");

        }


        return fileDocument;

    }
    private void insertInvoice(Long customerId, FileDocument file) {
        Customer customer = customerRepository.findById(customerId).get();
        if (customer.getInvoices() == null) {
            List<FileDocument> list = null;
            list.add(file);
            customer.setInvoices(list);
            customerRepository.save(customer);
        } else {
            List<FileDocument> list = customer.getInvoices();
            list.add(file);
            customerRepository.save(customer);

        }
    }

    public ResponseEntity<byte[]> singleFileDownload(String fileName, HttpServletRequest request){

       FileDocument document = doc.findByFileName(fileName);

        String mimeType = request.getServletContext().getMimeType(document.getFileName());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());

    }

    public void getZipDownload(String[] files, HttpServletResponse response) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            Arrays.stream(files).forEach(file -> {
                try {
                    createZipEntry(file, zos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            zos.finish();

            response.setStatus(200);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipfile");
        }
    }

    public Resource downLoadFileDatabase(String fileName) {

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/db/").path(fileName).toUriString();

        Resource resource;

        try {
            resource = new UrlResource(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }
        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

    public void createZipEntry(String file, ZipOutputStream zos) throws IOException {

            Resource resource = downLoadFileDatabase(file);
                ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(resource.getFilename()));
                try {
                    zipEntry.setSize(resource.contentLength());
                    zos.putNextEntry(zipEntry);

                    StreamUtils.copy(resource.getInputStream(), zos);

                    zos.closeEntry();
                } catch (IOException e) {
                    System.out.println("some exception while zipping");
                }

    }
    public void preload(File file)
            throws IOException
    {
        Long count = doc.count();
        String name = StringUtils.cleanPath(Objects.requireNonNull(count + file.getName()));
        FileDocument fileDocument = new FileDocument();
        fileDocument.setFileName(name);
        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];
        fl.read(arr);
        fl.close();
        fileDocument.setDocFile(arr);
        doc.save(fileDocument);
    }
    public ResponseEntity<byte[]> getDrinkImage(Long drinkId, HttpServletRequest request){
        FileDocument document = drinkRepository.findById(drinkId).get().getPicture();
        String mimeType = request.getServletContext().getMimeType(document.getFileName());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());

    }
    }
