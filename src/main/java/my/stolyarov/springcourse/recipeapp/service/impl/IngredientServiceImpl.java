package my.stolyarov.springcourse.recipeapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.stolyarov.springcourse.recipeapp.exception.IngredientCreatingException;
import my.stolyarov.springcourse.recipeapp.exception.IngredientNotFoundException;
import my.stolyarov.springcourse.recipeapp.exception.ReadFromFileException;
import my.stolyarov.springcourse.recipeapp.exception.SaveToFileException;
import my.stolyarov.springcourse.recipeapp.model.Ingredient;
import my.stolyarov.springcourse.recipeapp.service.FilesService;
import my.stolyarov.springcourse.recipeapp.service.IngredientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final FilesService filesService;
    @Value("${name.of.data.file.ingredient}")
    private String fileName;
    private static long id = 0;
    private Map<Long, Ingredient> ingredients = new HashMap<>();

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
        id = ingredients.size();
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredients.containsValue(ingredient)) {
            throw new IngredientCreatingException("This ingredient has already been created.");
        }
        Ingredient addedIngredient = ingredients.put(id++, ingredient);
        saveToFile();
        return  addedIngredient;
    }

    @Override
    public Collection<Ingredient> getAllIngredients() {
        return ingredients.values();
    }

    @Override
    public Ingredient getIngredient(long id) {
        Ingredient returnedIngredient = ingredients.get(id);
        if (returnedIngredient == null) {
            throw new IngredientNotFoundException("The ingredient with id: " + id + " not found");
        }
        return returnedIngredient;
    }

    @Override
    public Ingredient editIngredient(long id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            saveToFile();
            return ingredient;
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(long id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveToFile(fileName, json);
        } catch (JsonProcessingException e) {
            throw new SaveToFileException(e);
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile(fileName);
        try {
            ingredients = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new ReadFromFileException(e);
        }
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}
