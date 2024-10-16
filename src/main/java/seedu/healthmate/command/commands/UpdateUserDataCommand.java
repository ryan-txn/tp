package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class UpdateUserDataCommand extends Command{
    public static final String COMMAND = "update userdata";
    private static final String FORMAT = COMMAND;
    private static final String DESCRIPTION = "Triggers prompts for asking height, weight, gender, age and healthgoal";

    public UpdateUserDataCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

}
