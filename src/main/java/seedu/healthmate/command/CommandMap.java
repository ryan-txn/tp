package seedu.healthmate.command;

import seedu.healthmate.command.commands.AddMealEntryCommand;
import seedu.healthmate.command.commands.DeleteMealCommand;
import seedu.healthmate.command.commands.DeleteMealEntryCommand;
import seedu.healthmate.command.commands.HistoricCalorieProgressCommand;
import seedu.healthmate.command.commands.ListCommandsCommand;
import seedu.healthmate.command.commands.LogMealsCommand;
import seedu.healthmate.command.commands.MealMenuCommand;
import seedu.healthmate.command.commands.SaveMealCommand;
import seedu.healthmate.command.commands.TodayCalorieProgressCommand;
import seedu.healthmate.command.commands.UpdateUserDataCommand;
import seedu.healthmate.command.commands.CurrentUserDataCommand;
import seedu.healthmate.command.commands.MealRecommendationsCommand;
import seedu.healthmate.command.commands.WeightTimelineCommand;
import seedu.healthmate.command.commands.ByeCommand;

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
        COMMANDSMAP.put(CurrentUserDataCommand.COMMAND, new CurrentUserDataCommand());
        COMMANDSMAP.put(TodayCalorieProgressCommand.COMMAND, new TodayCalorieProgressCommand());
        COMMANDSMAP.put(HistoricCalorieProgressCommand.COMMAND, new HistoricCalorieProgressCommand());
        COMMANDSMAP.put(MealRecommendationsCommand.COMMAND, new MealRecommendationsCommand());
        COMMANDSMAP.put(WeightTimelineCommand.COMMAND, new WeightTimelineCommand());
        COMMANDSMAP.put(ByeCommand.COMMAND, new ByeCommand());
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
        if(userInput.equals(command)) {
            return  getAllCommands();
        }
        String commandToFind = userInput.substring(command.length()).trim();
        List<Command> commands = new ArrayList<>();
        if(!COMMANDSMAP.containsKey(commandToFind)) {
            return getAllCommands();
        }
        commands.add(getCommandByName(commandToFind));
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
