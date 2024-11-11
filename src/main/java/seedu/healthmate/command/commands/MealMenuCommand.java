package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealList;
import seedu.healthmate.services.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to display the menu of previously saved food items along with their calorie counts.
 */
public class MealMenuCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "meal menu";
    public static final String COMMAND_LOWER = "meal menu";
    /** Command format for displaying the meal menu. */
    private static final String FORMAT = "meal menu";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Displays the menu of previously saved food along with their calories";

    /**
     * Constructs a {@code MealMenuCommand} object with a predefined command keyword,
     * format, and description.
     */
    public MealMenuCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the meal menu command by displaying the list of saved food options along with their calorie information.
     * Logs the command execution and asserts that the meal options list is not null.
     *
     * @param mealOptions The list of meal options to display.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(MealList mealOptions, Logger logger) {
        assert mealOptions != null : "Meal options list should not be null";

        logger.log(Level.INFO, "Executing meal menu command to show meal options. ");
        UI.printMealOptions(mealOptions);
        logger.log(Level.INFO, "Executing meal menu command to show meal options");
    }
}
