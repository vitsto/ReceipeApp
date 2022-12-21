package my.stolyarov.springcourse.recipeapp.service.impl;

import my.stolyarov.springcourse.recipeapp.model.Recipe;
import my.stolyarov.springcourse.recipeapp.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long id = 0;
    private final Map<Long,Recipe> recipes = new HashMap<>();

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipes.containsValue(recipe)) {
            throw new RuntimeException("This recipe has already been created.");
        }
        return recipes.put(id++, recipe);
    }

    @Override
    public Recipe getRecipe(long id) {
        Recipe returnedRecipe = recipes.get(id);
        if (returnedRecipe == null) {
            throw new RuntimeException("The recipe with id: " + id + " not found");
        }
        return returnedRecipe;
    }
}
