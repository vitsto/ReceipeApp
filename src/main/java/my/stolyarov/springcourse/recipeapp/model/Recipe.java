package my.stolyarov.springcourse.recipeapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode
@ToString
public class Recipe {
    private String title;
    @EqualsAndHashCode.Exclude
    private int cookingTime;
    @EqualsAndHashCode.Exclude
    private final List<Ingredient> ingredients;
    @EqualsAndHashCode.Exclude
    private final List<String> cookingSteps;

    public Recipe(String title, int cookingTime, List<Ingredient> ingredients, List<String> cookingSteps) {
        if (StringUtils.isBlank(title)) {
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
}
