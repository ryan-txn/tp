package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class MealMenuCommand extends Command {
    public static final String COMMAND = "meal menu";
    private static final String FORMAT = "meal menu";
    private static final String DESCRIPTION = "Displays the menu of previously saved food along with their calories";

    public MealMenuCommand(){
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
