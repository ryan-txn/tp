package seedu.healthmate.recommender;

/**
 * Represents a recipe with nutritional information and a set of ingredients.
 * A Recipe includes details about the recipe name, calories, macronutrients,
 * fiber content, a list of ingredients, and an associated goal.
 */
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

    /**
     * Constructs a Recipe instance with the specified details.
     *
     * @param name Name of the recipe.
     * @param calories Caloric value of the recipe.
     * @param protein Protein content of the recipe.
     * @param carbs Carbohydrate content of the recipe.
     * @param fat Fat content of the recipe.
     * @param fiber Fiber content of the recipe.
     * @param recipe List of ingredients and instructions for the recipe.
     * @param goal Health or fitness goal associated with the recipe.
     */
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

    /**
     * Returns a formatted string representation of the recipe, including the
     * recipe name, calories, macronutrients, and list of ingredients.
     *
     * @return A formatted string representing the recipe details.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(recipeName).append(": ").append(calories).append(" calories\n")
                .append(INDENTATION).append("Protein: ").append(protein).append("g\n")
                .append(INDENTATION).append("Carbs: ").append(carbs).append("g\n")
                .append(INDENTATION).append("Fat: ").append(fat).append("g\n")
                .append(INDENTATION).append("Fiber: ").append(fiber).append("g\n");

        String[] ingredients = recipe.split("\n");
        for (String ingredient : ingredients) {
            sb.append(INDENTATION).append(ingredient).append("\n");
        }

        return sb.toString();
    }


    /**
     * Returns the caloric value of the recipe.
     *
     * @return The calories of the recipe.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Returns the goal associated with this recipe.
     *
     * @return The health or fitness goal for this recipe.
     */
    public Goals getGoal() {
        return goal;
    }
}
