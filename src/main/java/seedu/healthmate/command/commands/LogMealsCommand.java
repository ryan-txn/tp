package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class LogMealsCommand extends Command {
    public static final String COMMAND = "log meals";
    private static final String FORMAT = "log meals";
    private static final String DESCRIPTION = "Displays the log of all meal entries along with their Timestamp in Date Time format";

    public LogMealsCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

}
