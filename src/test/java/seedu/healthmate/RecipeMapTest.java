package seedu.healthmate;

import seedu.healthmate.recommender.Goals;
import seedu.healthmate.recommender.RecipeMap;
import seedu.healthmate.recommender.Recipe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeMapTest {
    @Test
    public void testGetRecipesByGoalReturnsFilteredRecipes() {
        Goals userGoal = Goals.BULKING;
        List<Recipe> recipes = RecipeMap.getRecipesByGoal(userGoal);
        for (Recipe recipe : recipes) {
            assertEquals(userGoal, recipe.getGoal());
        }

        userGoal = Goals.STEADY_STATE;
        recipes = RecipeMap.getRecipesByGoal(userGoal);
        for (Recipe recipe : recipes) {
            assertEquals(userGoal, recipe.getGoal());
        }

        userGoal = Goals.WEIGHT_LOSS;
        recipes = RecipeMap.getRecipesByGoal(userGoal);
        for (Recipe recipe : recipes) {
            assertEquals(userGoal, recipe.getGoal());
        }
    }

}
