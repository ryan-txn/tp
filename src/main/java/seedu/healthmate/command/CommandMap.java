package seedu.healthmate.command;

import seedu.healthmate.command.commands.AddMealEntryCommand;
import seedu.healthmate.command.commands.DeleteMealCommand;
import seedu.healthmate.command.commands.DeleteMealEntryCommand;
import seedu.healthmate.command.commands.HistoricCalorieProgressCommand;
import seedu.healthmate.command.commands.ListCommandsCommand;
import seedu.healthmate.command.commands.MealLogCommand;
import seedu.healthmate.command.commands.MealMenuCommand;
import seedu.healthmate.command.commands.SaveMealCommand;
import seedu.healthmate.command.commands.TodayCalorieProgressCommand;
import seedu.healthmate.command.commands.UpdateUserDataCommand;
import seedu.healthmate.command.commands.CurrentUserDataCommand;
import seedu.healthmate.command.commands.MealRecommendationsCommand;
import seedu.healthmate.command.commands.WeightTimelineCommand;
import seedu.healthmate.command.commands.ByeCommand;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class CommandMap {
    private static final Logger logger = Logger.getLogger(CommandMap.class.getName());
    private static final Map<String, Command> COMMANDSMAP = new LinkedHashMap<>();

    static {
        COMMANDSMAP.put(UpdateUserDataCommand.COMMAND_LOWER, new UpdateUserDataCommand());
        COMMANDSMAP.put(CurrentUserDataCommand.COMMAND_LOWER, new CurrentUserDataCommand());

        COMMANDSMAP.put(ListCommandsCommand.COMMAND_LOWER, new ListCommandsCommand());

        COMMANDSMAP.put(MealLogCommand.COMMAND_LOWER, new MealLogCommand());
        COMMANDSMAP.put(AddMealEntryCommand.COMMAND_LOWER, new AddMealEntryCommand());
        COMMANDSMAP.put(DeleteMealEntryCommand.COMMAND_LOWER, new DeleteMealEntryCommand());

        COMMANDSMAP.put(MealMenuCommand.COMMAND_LOWER, new MealMenuCommand());
        COMMANDSMAP.put(SaveMealCommand.COMMAND_LOWER, new SaveMealCommand());
        COMMANDSMAP.put(DeleteMealCommand.COMMAND_LOWER, new DeleteMealCommand());

        COMMANDSMAP.put(TodayCalorieProgressCommand.COMMAND_LOWER, new TodayCalorieProgressCommand());
        COMMANDSMAP.put(HistoricCalorieProgressCommand.COMMAND_LOWER, new HistoricCalorieProgressCommand());

        COMMANDSMAP.put(MealRecommendationsCommand.COMMAND_LOWER, new MealRecommendationsCommand());
        COMMANDSMAP.put(WeightTimelineCommand.COMMAND_LOWER, new WeightTimelineCommand());

        COMMANDSMAP.put(ByeCommand.COMMAND_LOWER, new ByeCommand());
    }

    /**
     * Retrieves a list of commands based on user input and specified command.
     * If the user input matches the command exactly, returns all commands.
     * Otherwise, returns a list with a single command matching the user input,
     * or all commands if no specific command is found.
     *
     * @param userInput The input provided by the user.
     * @param command The command to check against.
     * @return A list of matching commands.
     */
    // Retrieve a command by its name
    public static List<Command> getCommands(String userInput, String command) {
        if(userInput.equalsIgnoreCase(command)) {
            return  getAllCommands();
        }
        String commandToFind = userInput.substring(command.length()).trim();
        List<Command> commands = new ArrayList<>();
        if(!COMMANDSMAP.containsKey(commandToFind.toLowerCase())) {
            return commands;
        }
        commands.add(getCommandByName(commandToFind.toLowerCase()));
        return commands;

    }

    private static Command getCommandByName(String commandName) {
        return COMMANDSMAP.get(commandName);
    }

    private static List<Command> getAllCommands() {
        assert !COMMANDSMAP.isEmpty() : "Command map should not be empty";
        List<Command> commands = new ArrayList<>(COMMANDSMAP.values());

        logger.info("Retrieved " + commands.size() + " commands from the CommandMap");

        return commands;
    }
}
