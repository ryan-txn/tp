package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealList;
import seedu.healthmate.services.HistoryTracker;
import seedu.healthmate.services.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to delete a meal option from the meal menu.
 * The meal is deleted based on its specified index in the meal menu.
 */
public class DeleteMealCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "delete meal";
    public static final String COMMAND_LOWER = "delete meal";
    /** Command format for deleting a meal option by specifying its index. */
    private static final String FORMAT = COMMAND + " {index of meal in meal menu}";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Deletes meal option at the specified index from the meal menu";

    /**
     * Constructs a {@code DeleteMealCommand} object with a predefined command keyword,
     * format, and description.
     */
    public DeleteMealCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the delete meal command by removing a meal option from the meal menu at the specified index.
     * Saves the updated meal options list to the history tracker.
     *
     * @param historyTracker The history tracker to save the updated meal options.
     * @param mealOptions The list of meal options from which the specified meal will be deleted.
     * @param userInput The input provided by the user, containing the index of the meal to be deleted.
     * @param command The specific command keyword issued by the user.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(
            HistoryTracker historyTracker, MealList mealOptions,
            String userInput, String command, Logger logger) {

        assert mealOptions != null : "Meal options list should not be null";

        logger.log(Level.INFO, "Executing command to delete a meal from meal options." + System.lineSeparator() +
                "Number of meal options is: " + mealOptions.size());

        if (mealOptions.size() <= 0) {
            UI.printReply("No Meal Options", "Error: ");
            return;
        }

        // Removes the specified meal from the meal options and updates history
        mealOptions.extractAndRemoveMeal(userInput, command);
        historyTracker.saveMealOptions(mealOptions);
        logger.log(Level.INFO, "Finished executing command to delete a meal." + System.lineSeparator() +
                "Number of meal options is: " + mealOptions.size());
    }
}
