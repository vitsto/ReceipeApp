package my.stolyarov.springcourse.recipeapp.service.impl;

import my.stolyarov.springcourse.recipeapp.model.Ingredient;
import my.stolyarov.springcourse.recipeapp.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long id = 0;
    private final Map<Long, Ingredient> ingredients = new HashMap<>();

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredients.containsValue(ingredient)) {
            throw new RuntimeException("This ingredient has already been created.");
        }
        return ingredients.put(id++, ingredient);
    }

    @Override
    public Collection<Ingredient> getAllIngredients() {
        return ingredients.values();
    }

    @Override
    public Ingredient getIngredient(long id) {
        Ingredient returnedIngredient = ingredients.get(id);
        if (returnedIngredient == null) {
            throw new RuntimeException("The ingredient with id: " + id + " not found");
        }
        return returnedIngredient;
    }

    @Override
    public Ingredient editIngredient(long id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            return ingredient;
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(long id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            return true;
        }
        return false;
    }
}
