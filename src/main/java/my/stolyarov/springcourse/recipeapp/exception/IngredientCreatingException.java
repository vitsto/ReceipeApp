package my.stolyarov.springcourse.recipeapp.exception;

public class IngredientCreatingException extends RuntimeException {
    public IngredientCreatingException(String message) {
        super(message);
    }
}
