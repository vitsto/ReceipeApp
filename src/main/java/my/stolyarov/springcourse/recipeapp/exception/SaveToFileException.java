package my.stolyarov.springcourse.recipeapp.exception;

public class SaveToFileException extends RuntimeException{
    public SaveToFileException(Throwable cause) {
        super(cause);
    }
}
