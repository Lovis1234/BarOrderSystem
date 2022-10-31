package nl.belastingdienst.barordersystem.Util;

import nl.belastingdienst.barordersystem.Models.Customer;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.FileDocument;
import nl.belastingdienst.barordersystem.Repositories.CustomerRepository;
import nl.belastingdienst.barordersystem.Repositories.DocFileRepository;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import nl.belastingdienst.barordersystem.Services.CustomerService;
import nl.belastingdienst.barordersystem.Services.DatabaseService;
import nl.belastingdienst.barordersystem.Services.DrinkService;
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
    private final DatabaseService databaseService;
    private final DrinkService drinkService;
    @Autowired
    private final DrinkRepository drinkRepository;
    @Autowired
    private final DocFileRepository docFileRepository;

    public DataLoader(DatabaseService databaseService, DrinkService drinkService, DrinkRepository drinkRepository, DocFileRepository docFileRepository) throws IOException {
        this.databaseService = databaseService;
        this.drinkService = drinkService;
        this.drinkRepository = drinkRepository;
        this.docFileRepository = docFileRepository;
        loadDummyInvoice();
        loadDummyPicture();

    }

    private void loadDummyInvoice() throws IOException {
        Path path = Paths.get("src/main/resources/DataLoadFiles/2022100001.pdf");
        File file = path.toAbsolutePath().toFile();
        databaseService.preload(file);


    }

    private void loadDummyPicture() throws IOException {
        Path path2 = Paths.get("src/main/resources/DataLoadFiles/Berenburg Cola.jpeg");
        File file2 = path2.toAbsolutePath().toFile();
        databaseService.preload(file2);


    }
}
