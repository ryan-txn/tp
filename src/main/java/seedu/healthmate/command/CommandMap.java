package seedu.healthmate.command;

import seedu.healthmate.command.commands.AddMealEntryCommand;
import seedu.healthmate.command.commands.DeleteMealCommand;
import seedu.healthmate.command.commands.DeleteMealEntryCommand;
import seedu.healthmate.command.commands.ListCommandsCommand;
import seedu.healthmate.command.commands.LogMealsCommand;
import seedu.healthmate.command.commands.MealMenuCommand;
import seedu.healthmate.command.commands.SaveMealCommand;
import seedu.healthmate.command.commands.SetHealthGoalCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CommandMap {
    private static final Logger logger = Logger.getLogger(CommandMap.class.getName());
    private static final Map<String, Command> COMMANDSMAP = new HashMap<>();

    static {
        COMMANDSMAP.put(LogMealsCommand.COMMAND, new LogMealsCommand());
        COMMANDSMAP.put(AddMealEntryCommand.COMMAND, new AddMealEntryCommand());
        COMMANDSMAP.put(DeleteMealEntryCommand.COMMAND, new DeleteMealEntryCommand());
        COMMANDSMAP.put(DeleteMealCommand.COMMAND, new DeleteMealCommand());
        COMMANDSMAP.put(ListCommandsCommand.COMMAND, new ListCommandsCommand());
        COMMANDSMAP.put(MealMenuCommand.COMMAND, new MealMenuCommand());
        COMMANDSMAP.put(SaveMealCommand.COMMAND, new SaveMealCommand());
        COMMANDSMAP.put(SetHealthGoalCommand.COMMAND, new SetHealthGoalCommand());
    }

    // Retrieve a command by its name
    public Command getCommandByName(String commandName) {
        return COMMANDSMAP.get(commandName);
    }

    // Retrieve all commands with assertions and logging
    public static List<Command> getAllCommands() {
        // Assert that the command map is not empty
        assert !COMMANDSMAP.isEmpty() : "Command map should not be empty";
        List<Command> commands = new ArrayList<>(COMMANDSMAP.values());

        // Log the number of commands retrieved
        logger.info("Retrieved " + commands.size() + " commands from the CommandMap");

        return commands;
    }
}
