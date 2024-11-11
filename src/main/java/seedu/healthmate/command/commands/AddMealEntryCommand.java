package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.MealList;
import seedu.healthmate.core.User;
import seedu.healthmate.services.HistoryTracker;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to add a meal entry to the user's meal log.
 * The meal entry can be added from an existing meal in the meal menu or
 * as a new meal with a specified number of calories.
 */
public class AddMealEntryCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "add mealEntry";
    public static final String COMMAND_LOWER = "add mealentry";
    /** Command format for adding a meal entry with proper parameters. */
    private static final String FORMAT =
            "add mealEntry {meal name from menu} OR add mealEntry [{name}] /c{calories} [/p{portions}] [/t{Date in " +
                    "YYYY-MM-DD}]";

    /** Description of the command functionality. */
    private static final String DESCRIPTION =
            "Adds a meal entry to the meal log either from a pre-existing meal in the meal menu \n" +
                    INDENTATION + "or a new meal with a specified amount of calories, portions and date.\n" +
                    INDENTATION + "Optional parameters (name, portions, date) are indicated by []";

    /**
     * Constructs an {@code AddMealEntryCommand} object with a predefined command keyword,
     * format, and description.
     */
    public AddMealEntryCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the add meal entry command by appending a new meal entry to the user's meal log.
     * The meal entry can be an existing meal from the meal options or a new meal entry with specified calories.
     * Saves the updated meal entries to the history tracker.
     *
     * @param historyTracker The history tracker to save the updated meal entries.
     * @param mealOptions The list of predefined meal options.
     * @param mealEntries The list of meal entries to which the new meal will be added.
     * @param user The user for whom the meal entry is being added.
     * @param userInput The input provided by the user, containing the meal name and optional calories.
     * @param command The specific command keyword issued by the user.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(
            HistoryTracker historyTracker, MealList mealOptions, MealEntriesList mealEntries,
            User user, String userInput, String command, Logger logger) {

        assert mealOptions != null : "Meal options list should not be null";
        assert mealEntries != null : "Meal entries list should not be null";

        logger.log(Level.INFO, "Executing command to add a meal to mealEntries" + System.lineSeparator() +
                "Number of tracked meals is: " + mealEntries.size());

        // Adds the meal entry and updates history
        mealEntries.extractAndAppendMeal(userInput, command, mealOptions, user);
        historyTracker.saveMealEntries(mealEntries);

        logger.log(Level.INFO, "Finish executing command to add a meal to mealEntries" + System.lineSeparator() +
                "Number of tracked meals is: " + mealEntries.size());
    }
}
