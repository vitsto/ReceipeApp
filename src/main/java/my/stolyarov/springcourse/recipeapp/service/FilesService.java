package my.stolyarov.springcourse.recipeapp.service;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    boolean saveToFile(String fileName, String json);

    String readFromFile(String fileName);

    boolean cleanDataFile(String fileName);

    Path createTempFile(String suffix);

    File getDataFile(String filename);
}
