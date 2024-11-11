package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.Meal;
import seedu.healthmate.core.MealList;
import seedu.healthmate.services.HistoryTracker;
import seedu.healthmate.services.MealSaver;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to save a meal to the meal menu for future use with the "add mealEntry" command.
 * This command allows users to save meal details, including the name and calorie count.
 */
public class SaveMealCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "save meal";
    public static final String COMMAND_LOWER = "save meal";
    /** Command format for saving a meal with a specified name and calorie count. */
    private static final String FORMAT = "save meal {meal name} /c{number of calories}";

    /** Description of the command functionality. */
    private static final String DESCRIPTION =
            "Saves a meal to the meal menu for later use with the add mealEntry command";

    /**
     * Constructs a {@code SaveMealCommand} object with a predefined command keyword,
     * format, and description.
     */
    public SaveMealCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the save meal command by extracting meal details from the user input and
     * saving the meal to the meal menu. Logs the command execution and asserts that the necessary
     * components are available and valid.
     *
     * @param historyTracker The history tracker used to save the meal to persistent storage.
     * @param mealOptions The list of meal options to which the new meal will be added.
     * @param userInput The input provided by the user, containing the meal name and calorie count.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(
            HistoryTracker historyTracker, MealList mealOptions, String userInput, Logger logger) {

        assert historyTracker != null : "HistoryTracker should not be null";
        logger.log(Level.INFO, "Executing command to save a meal to meal options." + System.lineSeparator() +
                "Number of meal options is: " + mealOptions.size());

        // Initializes MealSaver and extracts meal details from user input
        MealSaver mealSaver = new MealSaver(historyTracker);
        Optional<Meal> mealToSave = mealSaver.extractMealFromUserInput(userInput);

        // Saves the meal to the meal options list if valid
        mealToSave.ifPresent(meal -> mealSaver.saveMeal(meal, mealOptions));
        logger.log(Level.INFO, "Finished executing save meal command to save the (optional) meal: " + mealToSave +
                System.lineSeparator() + "Number of meal options is: " + mealOptions.size());
    }
}
