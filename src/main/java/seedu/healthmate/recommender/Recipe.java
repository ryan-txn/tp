package seedu.healthmate.recommender;

public abstract class Recipe {
    private final String INDENTATION = "      ";
    public final String recipeName;
    private final int calories;
    private final String recipe;
    private final String goal;

    public Recipe(String name, int calories, String recipe, String goal) {
        this.recipeName = name;
        this.calories = calories;
        this.recipe = recipe;
        this.goal = goal;
    }

    @Override
    public String toString() {
        return recipeName + ":" + calories + "calories\n" + INDENTATION + recipe + "\n";
    }

    public String getRecipeName() {
        return recipeName;
    }
}
