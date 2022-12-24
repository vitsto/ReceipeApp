package my.stolyarov.springcourse.recipeapp.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Recipe {
    private String title;
    private int cookingTime;
    private final List<Ingredient> ingredients;
    private final List<String> cookingSteps;

    public Recipe(String title, int cookingTime, List<Ingredient> ingredients, List<String> cookingSteps) {
        if (title.isEmpty() || title.isBlank() || title == null) {
            throw new RuntimeException("Invalid input found for field \"title\"");
        } else {
            this.title = title;
        }
        this.cookingTime = cookingTime;
        if (ingredients == null) {
            throw new RuntimeException("Invalid input found for field \"ingredients\"");
        } else {
            this.ingredients = ingredients;
        }

        if (cookingSteps == null) {
            throw new RuntimeException("Invalid input found for field \"cookingSteps\"");
        } else {
            this.cookingSteps = cookingSteps;
        }
    }

    public Recipe() {
        this("Заготовка рецепта", 0,  new LinkedList<>(),  new LinkedList<>());
    }

    public String getTitle() {
        return title;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getCookingSteps() {
        return cookingSteps;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(title, recipe.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", cookingTime=" + cookingTime +
                ", ingredients=" + ingredients +
                ", cookingSteps=" + cookingSteps +
                '}';
    }
}
