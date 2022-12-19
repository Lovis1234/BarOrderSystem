package nl.belastingdienst.barordersystem.Util;

import nl.belastingdienst.barordersystem.Repositories.DocFileRepository;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import nl.belastingdienst.barordersystem.Services.DatabaseService;
import nl.belastingdienst.barordersystem.Services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DataLoader {
    private final DatabaseService databaseService;

    public DataLoader(DatabaseService databaseService) throws IOException {
        this.databaseService = databaseService;
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
