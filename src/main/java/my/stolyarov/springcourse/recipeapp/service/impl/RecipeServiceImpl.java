package my.stolyarov.springcourse.recipeapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.stolyarov.springcourse.recipeapp.model.Ingredient;
import my.stolyarov.springcourse.recipeapp.model.Recipe;
import my.stolyarov.springcourse.recipeapp.service.FilesService;
import my.stolyarov.springcourse.recipeapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final FilesService filesService;
    private static long id = 0;
    private Map<Long,Recipe> recipes = new HashMap<>();
    @Value("${name.of.data.file.recipe}")
    private String fileName;

    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
        id = recipes.size();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (recipes.containsValue(recipe)) {
            throw new RuntimeException("This recipe has already been created.");
        }
        Recipe addedRecipe = recipes.put(id++, recipe);
        saveToFile();
        return addedRecipe;
    }

    @Override
    public Collection<Recipe> getAllRecipes() {
        return recipes.values();
    }

    @Override
    public Recipe getRecipe(long id) {
        Recipe returnedRecipe = recipes.get(id);
        if (returnedRecipe == null) {
            throw new RuntimeException("The recipe with id: " + id + " not found");
        }
        return returnedRecipe;
    }

    @Override
    public Recipe editRecipe(long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public Collection<Recipe> findByIngredient(Ingredient ingredient) {
        Collection<Recipe> foundRecipes = new LinkedList<>();
        for (Recipe recipe : recipes.values()) {
            if (recipe.getIngredients().contains(ingredient)) {
                foundRecipes.add(recipe);
            }
        }
        return foundRecipes;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveToFile(fileName, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile(fileName);
        try {
            recipes = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
