package my.stolyarov.springcourse.recipeapp.service;

import my.stolyarov.springcourse.recipeapp.model.Recipe;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);
    Recipe getRecipe(long id);
}
