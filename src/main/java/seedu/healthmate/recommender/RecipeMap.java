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
     * Retrieves a list of recipes based on user input and command.
     * If the user input exactly matches the command, returns all recipes.
     * Otherwise, attempts to find a specific recipe by name.
     *
     * @param userInput The input string from the user
     * @param command The command to process
     * @return A list of recipes matching the criteria
     */
    public static List<Recipe> getRecipes(String userInput, String command) {
        if(userInput.equals(command)) {
            return getAllRecipes();
        }
        String recipeToFind = userInput.substring(command.length()).trim();
        List<Recipe> recipes = new ArrayList<>();
        if(!RECIPEMAP.containsKey(recipeToFind)) {
            return getAllRecipes();
        }
        recipes.add(getRecipeByName(recipeToFind));
        return recipes;  // Handle case like "list commands add mealEntry"

    }

    /**
     * Retrieves a recipe by its name.
     *
     * @param recipeName The name of the recipe to retrieve
     * @return The Recipe object corresponding to the given name
     */
    private static Recipe getRecipeByName(String recipeName) {
        return RECIPEMAP.get(recipeName);
    }

    /**
     * Retrieves all recipes from the recipe map.
     * Includes assertions to ensure the map is not empty.
     *
     * @return A list of all available recipes
     */
    private static List<Recipe> getAllRecipes() {
        // Assert that the command map is not empty
        assert !RECIPEMAP.isEmpty() : "Command map should not be empty";

        // Log the number of commands retrieved
        return new ArrayList<>(RECIPEMAP.values());
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
