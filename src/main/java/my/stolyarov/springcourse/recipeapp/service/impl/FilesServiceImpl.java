package my.stolyarov.springcourse.recipeapp.service.impl;

import my.stolyarov.springcourse.recipeapp.exception.ReadFromFileException;
import my.stolyarov.springcourse.recipeapp.service.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.data.file}")
    private String pathToFile;

    @Override
    public boolean saveToFile(String fileName, String json) {
        try {
            cleanDataFile(fileName);
            Files.writeString(Path.of(pathToFile, fileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(pathToFile, fileName));
        } catch (IOException e) {
            throw new ReadFromFileException(e);
        }
    }

    @Override
    public boolean cleanDataFile(String fileName) {
        try {
            Path path = Path.of(pathToFile, fileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile(String filename) {
        return new File(pathToFile + "/" + filename);
    }
}
