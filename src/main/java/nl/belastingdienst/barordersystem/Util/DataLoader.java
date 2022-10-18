package nl.belastingdienst.barordersystem.Util;

import nl.belastingdienst.barordersystem.Models.Customer;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Repositories.CustomerRepository;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import nl.belastingdienst.barordersystem.Services.CustomerService;
import nl.belastingdienst.barordersystem.Services.DatabaseService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

@Component
public class DataLoader {
    private CustomerRepository customerRepository;
    private final DatabaseService databaseService;
    private CustomerService customerService;

    public DataLoader(CustomerRepository customerRepository, DatabaseService databaseService) throws IOException {
        this.customerRepository = customerRepository;
        this.databaseService = databaseService;
        load();

    }

    private void load() throws IOException {
        Customer customer = new Customer(1001L,"Piebe",null);
        Path path = Paths.get("src/main/resources/DataLoadFiles/2022100001.pdf");
        File file = path.toAbsolutePath().toFile();
        customerRepository.save(customer);
        databaseService.preload(file);


    }
}
