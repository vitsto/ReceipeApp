package my.stolyarov.springcourse.recipeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.stolyarov.springcourse.recipeapp.model.Ingredient;
import my.stolyarov.springcourse.recipeapp.model.Recipe;
import my.stolyarov.springcourse.recipeapp.service.IngredientService;
import my.stolyarov.springcourse.recipeapp.service.RecipeService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController
@RequestMapping("/recipes")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы с рецептами")
public class RecipeController {
    RecipeService recipeService;
    IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @Operation(
            summary = "Получение всех  рецептов",
            description = "Можно получить все рецепты"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все рецепты",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Collection<Recipe>> getAllRecipes() {
        Collection<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение рецепта по id",
            description = "Передав id в теле запроса, вы сможете получить рецепт"
    )
    @Parameter(name = "id", example = "0")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    @Operation(
            summary = "Добавление нового рецепта",
            description = "С помощью этого метода вы можете добавить новый рецепт к списку"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.addRecipe(newRecipe);
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение существующего рецепта",
            description = "С помощью этого метода вы можете изменить существующий рецепт"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "0")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт с таким id не найден"
            )
    })
    public ResponseEntity<Recipe> editRecipe(@PathVariable long id, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.editRecipe(id, newRecipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта",
            description = "Передав id рецепта в теле запроса, метод удалит его"
    )
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @Operation(
            summary = "Нахождение рецептов по id ингредиента",
            description = "Передав id ингредиента (-ов) в теле запроса, метод удалит его"
    )
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

    @GetMapping("/download")
    public ResponseEntity<Object> downloadAllRecipes() {
        try {
            Path path = recipeService.createGeneralRecipeFile();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"All recipes.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }

    }
}
