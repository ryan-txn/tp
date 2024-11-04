package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealList;
import seedu.healthmate.services.HistoryTracker;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteMealCommand extends Command {
    public static final String COMMAND = "delete meal";
    private static final String FORMAT = COMMAND + " {index of meal in meal menu}";
    private static final String DESCRIPTION = "Deletes meal option at the specified index from the meal menu";

    public DeleteMealCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(
            HistoryTracker historyTracker, MealList mealOptions,
            String userInput, String command, Logger logger) {
        assert mealOptions != null : "Meal options list should not be null";
        logger.log(Level.INFO, "Executing command to delete a meal from meal options");
        mealOptions.extractAndRemoveMeal(userInput, command);
        historyTracker.saveMealOptions(mealOptions);
    }
}
