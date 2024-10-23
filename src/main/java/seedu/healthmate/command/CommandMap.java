package seedu.healthmate.command;

import seedu.healthmate.command.commands.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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
        COMMANDSMAP.put(UpdateUserDataCommand.COMMAND, new UpdateUserDataCommand());
        COMMANDSMAP.put(DailyCalorieProgressBarCommand.COMMAND, new DailyCalorieProgressBarCommand());
    }

    // Retrieve a command by its name
    public static List<Command> getCommands(String userInput, String command) {
        if(userInput.equals(command)) {
            return  getAllCommands();
        }
        String commandToFind = userInput.substring(command.length()).trim();  // Get the part after "list commands"

        // If arguments exist, handle them (e.g., return specific commands or additional functionality)

        List<Command> commands = new ArrayList<>();
        if(!COMMANDSMAP.containsKey(commandToFind)) {
            return getAllCommands();
        }
        commands.add(getCommandByName(commandToFind));
        return commands;  // Handle case like "list commands add mealEntry"

    }

    private static Command getCommandByName(String commandName) {
        return COMMANDSMAP.get(commandName);
    }

    // Retrieve all commands with assertions and logging
    private static List<Command> getAllCommands() {
        // Assert that the command map is not empty
        assert !COMMANDSMAP.isEmpty() : "Command map should not be empty";
        List<Command> commands = new ArrayList<>(COMMANDSMAP.values());

        // Log the number of commands retrieved
        logger.info("Retrieved " + commands.size() + " commands from the CommandMap");

        return commands;
    }
}
