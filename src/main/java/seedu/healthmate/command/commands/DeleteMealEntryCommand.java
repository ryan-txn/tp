package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class DeleteMealEntryCommand extends Command {
    public static final String COMMAND = "delete mealEntry";
    private static final String FORMAT = COMMAND + " {index of meal in the meal log}";
    private static final String DESCRIPTION = "Used to delete a meal at a specified index in the meal log";

    public DeleteMealEntryCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
