package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.User;
import seedu.healthmate.recommender.Recipe;
import seedu.healthmate.recommender.RecipeMap;
import seedu.healthmate.services.UI;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MealRecommendationsCommand extends Command {
    public static final String COMMAND = "meal recommendations";
    private static final String FORMAT = "meal recommendations";
    private static final String DESCRIPTION = "Displays meal recommendations based on User's health goal";

    public MealRecommendationsCommand(){
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(User user, Logger logger) {
        assert user.getHealthGoal() != null : "User health goal should not be null";
        List<Recipe> recipes = RecipeMap.getRecipesByGoal(user.getHealthGoal());
        assert recipes != null && !recipes.isEmpty() : "Recipes should not be null or empty";
        logger.log(Level.INFO, "Executing command to list meal recommendation");
        UI.printRecommendation(recipes);
    }
}
