package seedu.healthmate;

import seedu.healthmate.recommender.Goals;
import seedu.healthmate.recommender.RecipeMap;
import seedu.healthmate.recommender.Recipe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeMapTest {
    @Test
    public void testGetRecipesByGoalReturnsFilteredRecipes() {
        // Assuming you have defined Goals as an enum and the recipes have a goal
        Goals userGoal = Goals.BULKING;  // Example goal
        List<Recipe> recipes = RecipeMap.getRecipesByGoal(userGoal);
        for (Recipe recipe : recipes) {
            assertEquals(userGoal, recipe.getGoal());
        }

        userGoal = Goals.STEADY_STATE;  // Example goal
        recipes = RecipeMap.getRecipesByGoal(userGoal);
        for (Recipe recipe : recipes) {
            assertEquals(userGoal, recipe.getGoal());
        }

        userGoal = Goals.WEIGHT_LOSS;  // Example goal
        recipes = RecipeMap.getRecipesByGoal(userGoal);
        for (Recipe recipe : recipes) {
            assertEquals(userGoal, recipe.getGoal());
        }
    }
}
