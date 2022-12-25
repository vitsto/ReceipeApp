package my.stolyarov.springcourse.recipeapp.service;

import my.stolyarov.springcourse.recipeapp.model.Ingredient;
import my.stolyarov.springcourse.recipeapp.model.Recipe;

import java.util.Collection;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);

    Collection<Recipe> getAllRecipes();

    Recipe getRecipe(long id);

    Recipe editRecipe(long id, Recipe recipe);

    boolean deleteRecipe(long id);

    Collection<Recipe> findByIngredient(Ingredient ingredient);
}
