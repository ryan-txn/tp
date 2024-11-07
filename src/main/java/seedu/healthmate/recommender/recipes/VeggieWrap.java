package seedu.healthmate.recommender.recipes;

import seedu.healthmate.recommender.Goals;
import seedu.healthmate.recommender.Recipe;

public class VeggieWrap extends Recipe {
    //recipe from https://www.eatingwell.com/veggie-wraps-8690591
    public static final String RECIPE_NAME = "Veggie Wrap with Hummus";
    private static final int CALORIES = 361; // per serving
    private static final int PROTEIN = 12; // grams
    private static final int CARBS = 50; // grams
    private static final int FAT = 14; // grams
    private static final int FIBER = 8; // grams
    private static final Goals GOAL = Goals.WEIGHT_LOSS;

    private static final String RECIPE = """
            1 teaspoon extra-virgin olive oil
            1/2 small zucchini, sliced
            1/2 medium red bell pepper, sliced
            1/4 small red onion, sliced
            1/2 teaspoon dried oregano
            Pinch of salt
            2 whole-grain wraps
            1/4 cup hummus
            1/2 cup baby spinach
            2 tablespoons crumbled feta cheese
            4 black olives, sliced
            """;

    public VeggieWrap() {
        super(RECIPE_NAME, CALORIES, PROTEIN, CARBS, FAT, FIBER, RECIPE, GOAL);
    }
}
