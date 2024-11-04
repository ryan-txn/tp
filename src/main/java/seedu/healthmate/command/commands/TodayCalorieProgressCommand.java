package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.User;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TodayCalorieProgressCommand extends Command {
    public static final String COMMAND = "show todayCalories";
    private static final String FORMAT = "show todayCalories";
    private static final String DESCRIPTION = "Prints a Calorie Progress Bar to represent Today Calorie Progress";

    public TodayCalorieProgressCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommands(MealEntriesList mealEntries, User user, Logger logger) {
        assert mealEntries != null : "Meal entries list should not be null";
        logger.log(Level.INFO, "Executing command to print daily progress bar");
        mealEntries.printDaysConsumptionBar(user, LocalDateTime.now());
    }
}


