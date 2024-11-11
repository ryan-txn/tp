package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;


public class ByeCommand extends Command {
    /** Command keyword to invoke this action. */
    public static final String COMMAND = "bye";
    public static final String COMMAND_LOWER = "bye";

    /** Command format for adding a meal entry with proper parameters. */
    private static final String FORMAT =
            "bye";

    /** Description of the command functionality. */
    private static final String DESCRIPTION =
            "Exits the program";

    /**
     * Constructs an {@code AddMealEntryCommand} object with a predefined command keyword,
     * format, and description.
     */
    public ByeCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }


}
