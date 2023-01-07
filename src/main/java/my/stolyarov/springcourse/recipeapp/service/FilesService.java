package my.stolyarov.springcourse.recipeapp.service;

import java.io.File;

public interface FilesService {
    boolean saveToFile(String fileName, String json);

    String readFromFile(String fileName);

    boolean cleanDataFile(String fileName);

    File getDataFile(String filename);
}
