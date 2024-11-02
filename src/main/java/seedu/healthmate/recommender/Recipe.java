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
        StringBuilder sb = new StringBuilder();
        sb.append(recipeName).append(": ").append(calories).append(" calories\n")
                .append(INDENTATION).append("Protein: ").append(protein).append("g\n")
                .append(INDENTATION).append("Carbs: ").append(carbs).append("g\n")
                .append(INDENTATION).append("Fat: ").append(fat).append("g\n")
                .append(INDENTATION).append("Fiber: ").append(fiber).append("g\n");

        // Split the recipe string into individual lines for ingredients
        String[] ingredients = recipe.split("\n");
        for (String ingredient : ingredients) {
            sb.append(INDENTATION).append(ingredient).append("\n");
        }

        return sb.toString();
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
