package seedu.healthmate.command;

import seedu.healthmate.command.commands.LogMealsCommand;
import seedu.healthmate.command.commands.SaveMealCommand;
import seedu.healthmate.command.commands.ListCommandsCommand;
import seedu.healthmate.command.commands.AddMealEntryCommand;
import seedu.healthmate.command.commands.DeleteMealCommand;
import seedu.healthmate.command.commands.DeleteMealEntryCommand;
import seedu.healthmate.command.commands.MealMenuCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandMap {
    private static final Map<String, Command> COMMANDSMAP = new HashMap<>();

    static {
        COMMANDSMAP.put(LogMealsCommand.COMMAND, new LogMealsCommand());
        COMMANDSMAP.put(AddMealEntryCommand.COMMAND, new AddMealEntryCommand());
        COMMANDSMAP.put(DeleteMealEntryCommand.COMMAND, new DeleteMealEntryCommand());
        COMMANDSMAP.put(DeleteMealCommand.COMMAND, new DeleteMealCommand());
        COMMANDSMAP.put(ListCommandsCommand.COMMAND, new ListCommandsCommand());
        COMMANDSMAP.put(MealMenuCommand.COMMAND, new MealMenuCommand());
        COMMANDSMAP.put(SaveMealCommand.COMMAND, new SaveMealCommand());
    }

    // Retrieve a command by its name
    public Command getCommandByName(String commandName) {
        return COMMANDSMAP.get(commandName);
    }

    public static List<Command> getAllCommands() {
        return new ArrayList<>(COMMANDSMAP.values());
    }
}
