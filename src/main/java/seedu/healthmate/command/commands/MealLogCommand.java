package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.services.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to display the log of all meal entries.
 * Each meal entry is shown with its timestamp in date-time format.
 */
public class MealLogCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "meal log";
    public static final String COMMAND_LOWER = "meal log";
    /** Command format for displaying the meal log. */
    private static final String FORMAT = "meal log";

    /** Description of the command functionality. */
    private static final String DESCRIPTION =
            "Displays the log of all meal entries along with their Timestamp in Date Time format";

    /**
     * Constructs a {@code LogMealsCommand} object with a predefined command keyword,
     * format, and description.
     */
    public MealLogCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the log meals command by displaying the user's meal log.
     * Logs the command execution and asserts that the meal entries list is not null.
     *
     * @param mealEntries The list of meal entries to display.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(MealEntriesList mealEntries, Logger logger) {
        assert mealEntries != null : "Meal entries list should not be null";

        logger.log(Level.INFO, "Executing command to show meal history");
        UI.printMealEntries(mealEntries); // Displays the log of all meal entries
        logger.log(Level.INFO, "Finish executing command to show meal history");
    }
}
