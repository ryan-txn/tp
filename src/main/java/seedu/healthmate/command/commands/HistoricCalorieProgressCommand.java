package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.command.CommandPair;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.User;
import seedu.healthmate.services.UI;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to display historical calorie progress by showing calorie progress bars and statistics
 * for a specified number of days including today.
 */
public class HistoricCalorieProgressCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "show historicCalories";
    public static final String COMMAND_LOWER = "show historiccalories";
    /** Command format specifying the number of days to include in the historic calorie progress. */
    private static final String FORMAT = "show historicCalories {Number of Days inclu. Today}";

    /** Description of the command functionality. */
    private static final String DESCRIPTION =
            "Prints Calorie Progress Bars & Various Stats to represent Historical Calorie Progress";

    /**
     * Constructs a {@code HistoricCalorieProgressCommand} object with a predefined command keyword,
     * format, and description.
     */
    public HistoricCalorieProgressCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the historic calorie progress command by displaying calorie progress bars and statistics
     * for a specified number of days including today. If a valid number of days is specified, the command
     * shows historical calorie data.
     *
     * @param mealEntries The list of meal entries to use for displaying calorie progress.
     * @param commandPair The command pair containing the main command and additional parameters.
     * @param user The user whose calorie progress is being displayed.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(
            MealEntriesList mealEntries, CommandPair commandPair, User user, Logger logger) {

        assert mealEntries != null : "Meal entries list should not be null";

        logger.log(Level.INFO, "Executing command to print historic calorie bar");

        Optional<Integer> pastDays = parseDaysFromCommand(commandPair, 0);

        pastDays.ifPresent(days -> mealEntries.printHistoricConsumptionBars(user, days));

        logger.log(Level.INFO, "Finish executing command to print historic calorie bar" + System.lineSeparator() +
                "Number of past days entered: " + pastDays.map(integer -> integer.toString()).orElse(""));
    }

    /**
     * Attempts to parse a specific command token as an integer, representing the number of days for
     * which calorie progress should be displayed.
     *
     * @param commandPair The command pair containing the commands and additional parameters.
     * @param index The index of the parameter within the additional commands array.
     * @return An {@code Optional<Integer>} representing the number of days, if parsed successfully.
     */
    private static Optional<Integer> parseDaysFromCommand(CommandPair commandPair, int index) {
        assert commandPair != null : "CommandPair should not be null";
        assert index >= 0 : "Index should be non-negative";

        try {
            int days = Integer.parseInt(commandPair.getCommandByIndex(index));
            if (days <= 0) {
                throw new NumberFormatException();
            }
            return Optional.of(days);
        } catch (NumberFormatException e) {
            UI.printReply(commandPair.getCommandByIndex(index), "The following is not a valid day count: ");
        } catch (IndexOutOfBoundsException s) {
            UI.printReply("Specify the number of days you want to look into the past", "Missing input: ");
        }
        return Optional.empty();
    }
}
