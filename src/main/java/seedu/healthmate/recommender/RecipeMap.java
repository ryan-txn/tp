package seedu.healthmate.recommender;

import seedu.healthmate.command.Command;
import seedu.healthmate.command.commands.*;
import seedu.healthmate.recommender.recipes.BulkingOatmeal;
import seedu.healthmate.recommender.recipes.FruitSmoothie;
import seedu.healthmate.recommender.recipes.HealthySandwich;
import seedu.healthmate.recommender.recipes.VeggieWrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMap {
    private static final Map<String, Recipe> RECIPEMAP = new HashMap<>();
    static {
        RECIPEMAP.put(FruitSmoothie.RECIPE_NAME, new FruitSmoothie());
        RECIPEMAP.put(BulkingOatmeal.RECIPE_NAME, new BulkingOatmeal());
        RECIPEMAP.put(HealthySandwich.RECIPE_NAME, new HealthySandwich());
        RECIPEMAP.put(VeggieWrap.RECIPE_NAME, new VeggieWrap());
    }
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

    private static Recipe getRecipeByName(String recipeName) {
        return RECIPEMAP.get(recipeName);
    }

    // Retrieve all commands with assertions and logging
    private static List<Recipe> getAllRecipes() {
        // Assert that the command map is not empty
        assert !RECIPEMAP.isEmpty() : "Command map should not be empty";

        // Log the number of commands retrieved
        return new ArrayList<>(RECIPEMAP.values());
    }

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
