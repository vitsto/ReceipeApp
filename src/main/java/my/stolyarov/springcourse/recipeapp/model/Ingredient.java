package my.stolyarov.springcourse.recipeapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;


@Data
@ToString
@EqualsAndHashCode
public class Ingredient {
    private final String title;
    @EqualsAndHashCode.Exclude
    private int count;
    private String measureUnit;

    public Ingredient(String title, int count, String measureUnit) {
        if (StringUtils.isBlank(title)) {
            throw new RuntimeException("Invalid input found for field \"title\"");
        } else {
            this.title = title;
        }
        this.count = count;
        this.measureUnit = measureUnit;
    }
}
