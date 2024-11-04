package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealList;
import seedu.healthmate.services.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MealMenuCommand extends Command {
    public static final String COMMAND = "meal menu";
    private static final String FORMAT = "meal menu";
    private static final String DESCRIPTION = "Displays the menu of previously saved food along with their calories";

    public MealMenuCommand(){
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(MealList mealOptions, Logger logger) {
        assert mealOptions != null : "Meal options list should not be null";
        logger.log(Level.INFO, "Executing meal menu command to show meal options");
        UI.printMealOptions(mealOptions);
    }
}
