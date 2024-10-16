package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class DeleteMealCommand extends Command {
    public static final String COMMAND = "delete meal";
    private static final String FORMAT = COMMAND + " {index of meal in meal menu}";
    private static final String DESCRIPTION = "Deletes meal option at the specified index from the meal menu";

    public DeleteMealCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
