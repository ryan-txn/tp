package seedu.healthmate;

import org.junit.jupiter.api.Test;
import seedu.healthmate.recommender.Goals;
import seedu.healthmate.recommender.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {

    // A concrete subclass of Recipe to test its functionality
    private static class TestRecipe extends Recipe {

        public TestRecipe(String name, int calories, int protein, int carbs, int fat, int fiber, String recipe,
                          Goals goal) {
            super(name, calories, protein, carbs, fat, fiber, recipe, goal);
        }
    }

    @Test
    public void testGetCalories() {
        Recipe recipe = new TestRecipe("Test Recipe", 300, 20, 40, 10, 5, "Ingredient 1\nIngredient 2", Goals.BULKING);
        assertEquals(300, recipe.getCalories(), "The calories should be 300.");
    }

    @Test
    public void testGetGoal() {
        Recipe recipe = new TestRecipe("Test Recipe", 300, 20, 40, 10, 5, "Ingredient 1\nIngredient 2", Goals.BULKING);
        assertEquals(Goals.BULKING, recipe.getGoal(), "The goal should be BULKING.");
    }

    @Test
    public void testToString() {
        Recipe recipe = new TestRecipe("Test Recipe", 300, 20, 40, 10, 5,
                "Ingredient 1\nIngredient 2", Goals.BULKING);
        String expectedString = """
                Test Recipe: 300 calories
                      Protein: 20g
                      Carbs: 40g
                      Fat: 10g
                   \
                   Fiber: 5g
                      Ingredient 1
                      Ingredient 2
                """;
        assertEquals(expectedString, recipe.toString(), "The toString method should return the expected " +
                "formatted string.");
    }

    @Test
    public void testGetCaloriesZero() {
        Recipe recipe = new TestRecipe("Zero Calorie Recipe", 0, 0, 0, 0, 0, "No ingredients", Goals.STEADY_STATE);
        assertEquals(0, recipe.getCalories(), "The calories should be 0.");
    }
}
