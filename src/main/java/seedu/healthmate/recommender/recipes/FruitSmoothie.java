package seedu.healthmate.recommender.recipes;

import seedu.healthmate.recommender.Goals;
import seedu.healthmate.recommender.Recipe;

public class FruitSmoothie extends Recipe {
    //https://bonytobeastly.com/bulking-meals/#2-generic-bulking-smoothie-snackbreakfast
    public static final String RECIPE_NAME = "Green Bulking Smoothie";
    private static final int CALORIES = 500;
    private static final int PROTEIN = 40;
    private static final int CARBS = 55;
    private static final int FAT = 20;
    private static final int FIBER = 20;
    private static final Goals GOAL = Goals.BULKING;

    private static final String RECIPE = """
            1 banana (frozen or fresh)
            1 handful fresh spinach
            1 tablespoon of almond butter
            4 frozen strawberries
            1 tablespoon flax or chia seeds
            225ml of cold milk, soy milk, or oat milk
            1 scoop of unflavoured protein powder
            """;

    public FruitSmoothie() {
        super(RECIPE_NAME, CALORIES, PROTEIN, CARBS, FAT, FIBER, RECIPE, GOAL);
    }
}
