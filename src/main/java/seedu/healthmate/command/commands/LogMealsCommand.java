package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.services.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogMealsCommand extends Command {
    public static final String COMMAND = "log meals";
    private static final String FORMAT = "log meals";
    private static final String DESCRIPTION = "Displays the log of all meal entries along with their Timestamp in " +
            "Date Time format";

    public LogMealsCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(MealEntriesList mealEntries, Logger logger) {
        assert mealEntries != null : "Meal entries list should not be null";
        logger.log(Level.INFO, "Executing command to show meal history");
        UI.printMealEntries(mealEntries);
    }
}
