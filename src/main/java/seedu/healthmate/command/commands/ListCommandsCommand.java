package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.command.CommandMap;
import seedu.healthmate.services.UI;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to list all available commands in the application.
 */
public class ListCommandsCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "list commands";
    public static final String COMMAND_LOWER = "list commands";
    /** Command format for listing all available commands. */
    private static final String FORMAT = "list commands";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Lists out all available commands";

    /**
     * Constructs a {@code ListCommandsCommand} object with a predefined command keyword,
     * format, and description.
     */
    public ListCommandsCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the list commands command by retrieving and displaying all available commands
     * to the user. Logs the command execution and asserts that the commands list is not null.
     *
     * @param userInput The input provided by the user.
     * @param command The specific command keyword issued by the user.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(String userInput, String command, Logger logger) {
        List<Command> commands = CommandMap.getCommands(userInput, command);
        assert commands != null : "Commands list should not be null";

        logger.log(Level.INFO, "Executing command to show all available commands");
        UI.printCommands(commands);
        logger.log(Level.INFO, "Finish executing command to show all available commands");
    }
}
