package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.User;
import seedu.healthmate.services.HistoryTracker;
import seedu.healthmate.services.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to delete a meal entry from the user's meal log.
 * The meal entry is deleted based on its specified index in the meal log.
 */
public class DeleteMealEntryCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "delete mealEntry";
    public static final String COMMAND_LOWER = "delete mealentry";
    /** Command format for deleting a meal entry by specifying its index in the meal log. */
    private static final String FORMAT = COMMAND + " {index of meal in the meal log}";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Used to delete a meal at a specified index in the meal log";

    /**
     * Constructs a {@code DeleteMealEntryCommand} object with a predefined command keyword,
     * format, and description.
     */
    public DeleteMealEntryCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the delete meal entry command by removing a meal entry from the user's meal log
     * at the specified index. Saves the updated meal entries list to the history tracker.
     *
     * @param historyTracker The history tracker to save the updated meal entries.
     * @param mealEntries The list of meal entries from which the specified meal entry will be deleted.
     * @param user The user whose meal entry is being deleted.
     * @param userInput The input provided by the user, containing the index of the meal entry to be deleted.
     * @param command The specific command keyword issued by the user.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(
            HistoryTracker historyTracker, MealEntriesList mealEntries,
            User user, String userInput, String command, Logger logger) {

        assert mealEntries != null : "Meal entries list should not be null";

        logger.log(Level.INFO, "Executing command to delete a tracked meal." + System.lineSeparator() +
                "Number of meals tracked is: " + mealEntries.size());

        if (mealEntries.size() <= 0) {
            UI.printReply("No Meal Entries", "Error: ");
            return;
        }

        // Removes the specified meal entry from the meal log and updates history
        mealEntries.extractAndRemoveMeal(userInput, command, user);
        historyTracker.saveMealEntries(mealEntries);

        logger.log(Level.INFO, "Finish executing command to delete a tracked meal." + System.lineSeparator() +
                "Number of meals tracked is: " + mealEntries.size());
    }
}
