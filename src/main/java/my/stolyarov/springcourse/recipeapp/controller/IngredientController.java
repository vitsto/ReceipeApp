package my.stolyarov.springcourse.recipeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.stolyarov.springcourse.recipeapp.model.Ingredient;
import my.stolyarov.springcourse.recipeapp.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты", description = "CRUD-операции и другие эндпоинты для работы с ингредиентами")
public class IngredientController {
    IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @Operation(
            summary = "Получение всех ингредиентов",
            description = "Можно получить все ингредиенты"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все ингредиенты",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Collection<Ingredient>> getAllIngredients() {
        Collection<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение ингредиента по id",
            description = "Передав id в теле запроса, вы сможете получить ингредиент"
    )
    @Parameter(name = "id", example = "0")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> getIngredient(@PathVariable long id) {
        Ingredient ingredient = this.ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping
    @Operation(
            summary = "Добавление нового ингридиента",
            description = "С помощью этого метода вы можете добавить новый ингридиент к списку"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient newIngredient) {
        Ingredient ingredient = this.ingredientService.addIngredient(newIngredient);
        return ResponseEntity.ok(ingredient);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение существующего ингредиента",
            description = "С помощью этого метода вы можете изменить существующий ингридиент"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "0")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент с таким id не найден"
            )
    })
    public ResponseEntity<Ingredient> editIngredient(@PathVariable long id, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @Operation(
            summary = "Удаление ингридиента",
            description = "Передав id ингредиента в теле запроса, метод удалит его"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
