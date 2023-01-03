package my.stolyarov.springcourse.recipeapp.service;

public interface FilesService {
    boolean saveToFile(String fileName, String json);

    String readFromFile(String fileName);
}
