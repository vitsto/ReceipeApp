package my.stolyarov.springcourse.recipeapp.exception;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException(Throwable cause) {
        super(cause);
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
