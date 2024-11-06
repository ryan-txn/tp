package seedu.healthmate.recommender;

import seedu.healthmate.recommender.recipes.BulkingOatmeal;
import seedu.healthmate.recommender.recipes.FruitSmoothie;
import seedu.healthmate.recommender.recipes.HealthySandwich;
import seedu.healthmate.recommender.recipes.VeggieWrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class that manages a collection of recipes and provides methods to access them.
 * Contains a static map of recipe names to Recipe objects and methods to retrieve recipes
 * based on different criteria.
 */
public class RecipeMap {
    private static final Map<String, Recipe> RECIPEMAP = new HashMap<>();
    static {
        RECIPEMAP.put(FruitSmoothie.RECIPE_NAME, new FruitSmoothie());
        RECIPEMAP.put(BulkingOatmeal.RECIPE_NAME, new BulkingOatmeal());
        RECIPEMAP.put(HealthySandwich.RECIPE_NAME, new HealthySandwich());
        RECIPEMAP.put(VeggieWrap.RECIPE_NAME, new VeggieWrap());
    }

    /**
     * Retrieves all recipes that match a specific fitness goal.
     *
     * @param userGoal The fitness goal to filter recipes by
     * @return A list of recipes that match the specified goal
     */
    public static List<Recipe> getRecipesByGoal(Goals userGoal) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe recipe : RECIPEMAP.values()) {
            if (recipe.getGoal() == userGoal) {
                filteredRecipes.add(recipe);
            }
        }
        return filteredRecipes;
    }
}
