package seedu.healthmate.recommender.recipes;

import seedu.healthmate.recommender.Goals;
import seedu.healthmate.recommender.Recipe;

public class BulkingOatmeal extends Recipe {
    //https://bonytobeastly.com/bulking-meals/#6-high-protein-oatmeal-second-breakfast
    public static final String RECIPE_NAME = "High-Protein Oatmeal (Second Breakfast)";
    private static final int CALORIES = 850;
    private static final int PROTEIN = 55;
    private static final int CARBS = 115;
    private static final int FAT = 22;
    private static final int FIBER = 10;
    private static final Goals GOAL = Goals.BULKING;

    private static final String RECIPE = """
            1 cup oats (such as quick oats)
            2 cups whole milk (or soy milk, low-fat milk, etc)
            1 diced peach (or apple, mangoes, berries, etc)
            1 tbsp honey
            1 scoop protein powder (e.g., whey isolate)
            A pinch of salt
            1 tsp cinnamon
            1/4 tsp vanilla
            1 tsp ashwagandha powder (optional)
            """;

    public BulkingOatmeal() {
        super(RECIPE_NAME, CALORIES, PROTEIN, CARBS, FAT, FIBER, RECIPE, GOAL);
    }
}
