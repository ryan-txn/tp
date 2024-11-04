package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.MealList;
import seedu.healthmate.core.User;
import seedu.healthmate.services.HistoryTracker;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddMealEntryCommand extends Command {
    public static final String COMMAND = "add mealEntry";
    private static final String FORMAT =
            "add mealEntry {meal name from menu} | or | add mealEntry {meal name} /c{number of " + "calories}";
    private static final String DESCRIPTION = "Adds a meal entry to the meal log either from a pre-existing meal in " +
            "the" +
            " meal menu or" + " a" + " new meal with a specified amount of calories";

    public AddMealEntryCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }


    public static void executeCommand(
            HistoryTracker historyTracker, MealList mealOptions, MealEntriesList mealEntries,
            User user, String userInput, String command, Logger logger){
        assert mealOptions != null : "Meal options list should not be null";
        assert mealEntries != null : "Meal entries list should not be null";
        logger.log(Level.INFO, "Executing command to add a meal to mealEntries");
        mealEntries.extractAndAppendMeal(userInput, command, mealOptions, user);
        historyTracker.saveMealEntries(mealEntries);
    }
}
