package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class SaveMealCommand extends Command {
    public static final String COMMAND = "save meal";
    private static final String FORMAT = "save meal {meal name} /c{number of calories}";
    private static final String DESCRIPTION = "Saves a meal to the meal menu for later use" + " with the add mealEntry " +
            "command";

    public SaveMealCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
