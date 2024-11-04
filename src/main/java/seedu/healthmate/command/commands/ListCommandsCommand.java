package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.command.CommandMap;
import seedu.healthmate.services.UI;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListCommandsCommand extends Command {
    public static final String COMMAND = "list commands";
    private static final String FORMAT = "list commands";
    private static final String DESCRIPTION = "Lists out all available commands";

    public ListCommandsCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(String userInput, String command, Logger logger) {
        List<Command> commands = CommandMap.getCommands(userInput, command);
        assert commands != null : "Commands list should not be null";
        logger.log(Level.INFO, "Executing command to show all available commands");
        UI.printCommands(commands);
    }
}
