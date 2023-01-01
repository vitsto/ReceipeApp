package my.stolyarov.springcourse.recipeapp.controller;

import my.stolyarov.springcourse.recipeapp.model.Ingredient;
import my.stolyarov.springcourse.recipeapp.model.Recipe;
import my.stolyarov.springcourse.recipeapp.service.IngredientService;
import my.stolyarov.springcourse.recipeapp.service.RecipeService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    RecipeService recipeService;
    IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<Collection<Recipe>> getAllRecipes() {
        Collection<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.addRecipe(newRecipe);
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable long id, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.editRecipe(id, newRecipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

//    @GetMapping("/findByIngredientId")
//    public ResponseEntity<Collection<Recipe>> findByIngredientId(@RequestParam long id) {
//        Ingredient ingredient = ingredientService.getIngredient(id);
//        Collection<Recipe> recipes = recipeService.findByIngredient(ingredient);
//        if (recipes == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(recipes);
//    }

    @GetMapping("/findByIngredientId")
    public ResponseEntity<Collection<Recipe>> findByIngredientId(@RequestParam Long[] ids) {
        Set<Recipe> result = new HashSet<>();

        for (long id : ids) {
            Ingredient ingredient = ingredientService.getIngredient(id);
            Collection<Recipe> recipes = recipeService.findByIngredient(ingredient);
            if (recipes != null) {
                result.addAll(recipes);
            }
        }
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
