package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.User;
import seedu.healthmate.services.HistoryTracker;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteMealEntryCommand extends Command {
    public static final String COMMAND = "delete mealEntry";
    private static final String FORMAT = COMMAND + " {index of meal in the meal log}";
    private static final String DESCRIPTION = "Used to delete a meal at a specified index in the meal log";

    public DeleteMealEntryCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(
            HistoryTracker historyTracker, MealEntriesList mealEntries,
            User user, String userInput, String command, Logger logger) {
        assert mealEntries != null : "Meal entries list should not be null";
        logger.log(Level.INFO, "Executing command to delete a meal from mealEntries");
        mealEntries.removeMealWithFeedback(userInput, command, user);
        historyTracker.saveMealEntries(mealEntries);
    }
}
