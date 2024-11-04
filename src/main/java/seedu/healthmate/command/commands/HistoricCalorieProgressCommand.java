package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.command.CommandPair;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.User;
import seedu.healthmate.services.UI;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoricCalorieProgressCommand extends Command {
    public static final String COMMAND = "show historicCalories";
    private static final String FORMAT = "show historicCalories {Number of Days inclu. Today}";
    private static final String DESCRIPTION
            = "Prints Calorie Progress Bars & Various Stats to represent Historical Calorie Progress";

    public HistoricCalorieProgressCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(
            MealEntriesList mealEntries, CommandPair commandPair, User user, Logger logger) {
        assert mealEntries != null : "Meal entries list should not be null";
        logger.log(Level.INFO, "Executing command to print historic calorie bar");
        Optional<Integer> pastDays = parseDaysFromCommand(commandPair, 0);
        assert pastDays.isPresent() : "Past days should be specified";
        pastDays.ifPresent(days -> mealEntries.printHistoricConsumptionBars(user, days));
    }

    /**
     * Tries to parse a certain command token of the customer into an integer
     * @param commandPair The considered commands
     * @param index The index of the command in the additionalCommands Array
     * @return Number of days (Integer)
     */
    private static Optional<Integer> parseDaysFromCommand(CommandPair commandPair, int index) {
        assert commandPair != null : "CommandPair should not be null";
        assert index >= 0 : "Index should be non-negative";
        try {
            int days =  Integer.parseInt(commandPair.getCommandByIndex(index));
            return Optional.of(days);
        } catch (NumberFormatException e) {
            UI.printReply(commandPair.getCommandByIndex(index), "The following is not a valid number: ");
        } catch (IndexOutOfBoundsException s) {
            UI.printReply("Specify the number of days you want to look into the past", "Missing input: ");
        }
        return Optional.empty();
    }
}
