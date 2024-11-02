package seedu.healthmate.recommender;

public abstract class Recipe {
    private static final String INDENTATION = "      ";
    public final String recipeName;
    private final int calories;
    private final int protein;
    private final int carbs;
    private final int fat;
    private final int fiber;
    private final String recipe;
    private final Goals goal;

    public Recipe(String name, int calories, int protein, int carbs, int fat, int fiber, String recipe, Goals goal) {
        this.recipeName = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.recipe = recipe;
        this.goal = goal;
    }

    @Override
    public String toString() {
        return recipeName + ": " + calories + " calories\n" +
                INDENTATION + "Protein: " + protein + "g\n" +
                INDENTATION + "Carbs: " + carbs + "g\n" +
                INDENTATION + "Fat: " + fat + "g\n" +
                INDENTATION + "Fiber: " + fiber + "g\n" +
                INDENTATION + recipe + "\n";
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getCalories() {
        return calories;
    }

    public Goals getGoal() {
        return goal;
    }
}
