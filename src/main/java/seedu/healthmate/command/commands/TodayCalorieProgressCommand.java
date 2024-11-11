package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.User;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to display today's calorie progress by printing a calorie progress bar
 * that reflects the user's calorie consumption for the current day.
 */
public class TodayCalorieProgressCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "show todayCalories";
    public static final String COMMAND_LOWER = "show todaycalories";
    /** Command format for displaying today's calorie progress. */
    private static final String FORMAT = "show todayCalories";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Prints a Calorie Progress Bar to represent Today Calorie Progress";

    /**
     * Constructs a {@code TodayCalorieProgressCommand} object with a predefined command keyword,
     * format, and description.
     */
    public TodayCalorieProgressCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the today calorie progress command by displaying a calorie progress bar that
     * shows the user's calorie consumption for the current day. Logs the command execution and
     * asserts that the meal entries list is not null.
     *
     * @param mealEntries The list of meal entries used to calculate today's calorie consumption.
     * @param user The user whose calorie progress is being displayed.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommands(MealEntriesList mealEntries, User user, Logger logger) {
        assert mealEntries != null : "Meal entries list should not be null";

        logger.log(Level.INFO, "Executing command to print daily progress bar");
        mealEntries.printDaysConsumptionBar(user, LocalDateTime.now());
        logger.log(Level.INFO, "Finish executing command to print daily progress bar");
    }
}
