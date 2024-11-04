package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.Meal;
import seedu.healthmate.core.MealList;
import seedu.healthmate.services.HistoryTracker;
import seedu.healthmate.services.MealSaver;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveMealCommand extends Command {
    public static final String COMMAND = "save meal";
    private static final String FORMAT = "save meal {meal name} /c{number of calories}";
    private static final String DESCRIPTION = "Saves a meal to the meal menu for later use" + " with the add " +
            "mealEntry " + "command";

    public SaveMealCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(HistoryTracker historyTracker, MealList mealOptions, String userInput, Logger logger) {
        assert historyTracker != null : "HistoryTracker should not be null";
        MealSaver mealSaver = new MealSaver(historyTracker);
        Optional<Meal> mealToSave = mealSaver.extractMealFromUserInput(userInput);
        assert mealToSave.isPresent() : "Meal to save should be present";
        logger.log(Level.INFO, "Executing save meal command to save meal options");
        mealToSave.ifPresent(meal -> mealSaver.saveMeal(meal, mealOptions));
    }
}
