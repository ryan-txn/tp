package seedu.healthmate.recommender.recipes;

import seedu.healthmate.recommender.Goals;
import seedu.healthmate.recommender.Recipe;

public class HealthySandwich extends Recipe {
    //https://www.eatingwell.com/high-protein-veggie-sandwich-formula-8714142
    public static final String RECIPE_NAME = "Healthy Turkey Avocado Sandwich";
    private static final int CALORIES = 400;
    private static final int PROTEIN = 30; // grams
    private static final int CARBS = 45; // grams
    private static final int FAT = 15; // grams
    private static final int FIBER = 10; // grams
    private static final Goals GOAL = Goals.STEADY_STATE;

    private static final String RECIPE = """
            2 slices of whole grain bread
            100g sliced turkey breast
            1/2 avocado, sliced
            1 handful of mixed greens (lettuce, spinach)
            2 slices of tomato
            1 tablespoon mustard or hummus
            """;

    public HealthySandwich() {
        super(RECIPE_NAME, CALORIES, PROTEIN, CARBS, FAT, FIBER, RECIPE, GOAL);
    }
}
