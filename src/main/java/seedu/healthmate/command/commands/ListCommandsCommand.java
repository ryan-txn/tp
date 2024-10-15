package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class ListCommandsCommand extends Command {
    public static final String COMMAND = "list commands";
    private static final String FORMAT = "list commands";
    private static final String DESCRIPTION = "Lists out all available commands";

    public ListCommandsCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
