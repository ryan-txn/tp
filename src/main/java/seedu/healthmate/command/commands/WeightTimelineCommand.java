package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class WeightTimelineCommand extends Command {
    public static final String COMMAND = "weight timeline";
    private static final String FORMAT = "weight timeline";
    private static final String DESCRIPTION = "View a timeline of the last 10 weight updates";

    public WeightTimelineCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
