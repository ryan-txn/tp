package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.User;
import seedu.healthmate.recommender.Recipe;
import seedu.healthmate.recommender.RecipeMap;
import seedu.healthmate.services.UI;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to display meal recommendations based on the user's health goal.
 */
public class MealRecommendationsCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "meal recommendations";
    public static final String COMMAND_LOWER = "meal recommendations";
    /** Command format for displaying meal recommendations. */
    private static final String FORMAT = "meal recommendations";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Displays meal recommendations based on User's health goal";

    /**
     * Constructs a {@code MealRecommendationsCommand} object with a predefined command keyword,
     * format, and description.
     */
    public MealRecommendationsCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the meal recommendations command by displaying meal recommendations that align with
     * the user's health goal. Retrieves relevant recipes from the RecipeMap and displays them.
     * Logs the command execution and asserts that the user's health goal and recipes list are valid.
     *
     * @param user The user for whom meal recommendations are being generated.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(User user, Logger logger) {
        assert user.getHealthGoal() != null : "User health goal should not be null";

        // Retrieves and verifies recipes based on the user's health goal
        List<Recipe> recipes = RecipeMap.getRecipesByGoal(user.getHealthGoal());
        assert recipes != null && !recipes.isEmpty() : "Recipes should not be null or empty";

        logger.log(Level.INFO, "Executing command to list meal recommendation");
        UI.printRecommendation(recipes);
        logger.log(Level.INFO, "Finish executing command to list meal recommendation");
    }
}
