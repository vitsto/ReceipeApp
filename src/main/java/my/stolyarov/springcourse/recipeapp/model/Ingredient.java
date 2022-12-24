package my.stolyarov.springcourse.recipeapp.model;

import java.util.Objects;

public class Ingredient {
    private final String title;
    private int count;
    private String measureUnit;

    public Ingredient(String title, int count, String measureUnit) {
        if (title.isEmpty() || title.isBlank() || title == null) {
            throw new RuntimeException("Invalid input found for field \"title\"");
        } else {
            this.title = title;
        }
        this.count = count;
        this.measureUnit = measureUnit;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return count == that.count && Objects.equals(title, that.title) && Objects.equals(measureUnit, that.measureUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, count, measureUnit);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "title='" + title + '\'' +
                ", count=" + count +
                ", measureUnit='" + measureUnit + '\'' +
                '}';
    }
}
