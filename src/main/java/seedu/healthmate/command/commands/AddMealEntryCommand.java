package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

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
}
