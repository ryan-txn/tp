package seedu.healthmate.services;

import seedu.healthmate.command.Command;
import seedu.healthmate.command.CommandPair;
import seedu.healthmate.command.commands.LogMealsCommand;
import seedu.healthmate.command.commands.SaveMealCommand;
import seedu.healthmate.command.commands.ListCommandsCommand;
import seedu.healthmate.command.commands.AddMealEntryCommand;
import seedu.healthmate.command.commands.DeleteMealCommand;
import seedu.healthmate.command.commands.DeleteMealEntryCommand;
import seedu.healthmate.command.commands.MealMenuCommand;
import seedu.healthmate.command.commands.UpdateUserDataCommand;
import seedu.healthmate.command.commands.TodayCalorieProgressCommand;
import seedu.healthmate.command.commands.HistoricCalorieProgressCommand;
import seedu.healthmate.command.CommandMap;
import seedu.healthmate.core.Meal;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.MealList;
import seedu.healthmate.core.User;
import seedu.healthmate.core.UserHistoryTracker;
import seedu.healthmate.utils.Logging;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;


/**
 * Encapsulates the main logic of the application by parsing user input into objects
 * and storing them respectively.
 */
public class ChatParser {

    public static final String CALORIE_SIGNALLER = "/c";

    private static Logger logger = Logger.getLogger(ChatParser.class.getName());
    private MealEntriesList mealEntries;
    private MealList mealOptions;
    private final HistoryTracker historyTracker;
    private final UserHistoryTracker userHistoryTracker;

    public ChatParser(){
        Logging.setupLogger(logger, ChatParser.class.getName());
        this.historyTracker = new HistoryTracker();
        logger.log(Level.INFO, "Initializing HistoryTracker");
        UI.printSeparator();
        this.mealEntries = historyTracker.loadMealEntries();
        logger.log(Level.INFO, "Loaded MealEntries");
        this.mealOptions = historyTracker.loadMealOptions();
        logger.log(Level.INFO, "Loaded MealOptions");
        this.userHistoryTracker = new UserHistoryTracker();
        logger.log(Level.INFO, "Initializing UserHistoryTracker");
        UI.printSeparator();
    }

    /**
     * Reads in user input from the command line
     * and initiates the parsing process steered by one-token and two-token-based user prompts.
     */
    public void run() {
        // check for health goal file existence and create file if none exists
        logger.log(Level.INFO, "Checking if user data exists");
        User userEntry = userHistoryTracker.checkForUserData(this.userHistoryTracker);
        parseUserInput(userEntry);
    }

    private void parseUserInput(User userEntry) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("bye")) {
            logger.log(Level.INFO, "Getting next user input line");
            userInput = scanner.nextLine().strip();
            switch (userInput) {
            case "bye":
                logger.log(Level.INFO, "User closes application");
                UI.printFarewell();
                break;
            default:
                try {
                    this.multiCommandParsing(userInput, userEntry);
                    logger.log(Level.INFO, "User input contains more than 1 token");
                } catch (ArrayIndexOutOfBoundsException a) {
                    logger.log(Level.WARNING, "Invalid command", a);
                    UI.printReply("Invalid command", "Retry: ");
                }
            }
        }
    }

    /**
     * Steers the activation of features offered to the userEntry via two-token commands
     * @param userInput String userEntry input from the command line
     */

    public void multiCommandParsing(String userInput, User userEntry) {

        User currentUser = userEntry; //create snapshot in case user is updated
        CommandPair commandPair = getCommandFromInput(userInput);
        String command = commandPair.getMainCommand();
        logger.log(Level.INFO, "User commands are: " + commandPair);

        switch (command) {
        case MealMenuCommand.COMMAND:
            logger.log(Level.INFO, "Executing meal menu command to show meal options");
            UI.printMealOptions(this.mealOptions);
            break;
        case SaveMealCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to save meal to meal options");
            MealSaver mealSaver = new MealSaver(historyTracker);
            Optional<Meal> mealToSave = mealSaver.extractMealFromUserInput(userInput);
            mealToSave.ifPresent(meal -> mealSaver.saveMeal(meal, mealOptions));
            break;
        case DeleteMealCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to delete a meal from meal options");
            mealOptions.extractAndRemoveMeal(userInput, command);
            historyTracker.saveMealOptions(mealOptions);
            break;
        case DeleteMealEntryCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to delete a meal from mealEntries");
            mealEntries.removeMealWithFeedback(userInput, command, currentUser);
            historyTracker.saveMealEntries(mealEntries);
            break;
        case AddMealEntryCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to add a meal to mealEntries");
            mealEntries.extractAndAppendMeal(userInput, command, mealOptions, currentUser);
            historyTracker.saveMealEntries(mealEntries);
            break;
        case LogMealsCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to show meal history");
            UI.printMealEntries(this.mealEntries);
            break;
        case ListCommandsCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to show all available commands");
            List<Command> commands= CommandMap.getCommands(userInput, command);
            UI.printCommands(commands);
            break;
        case UpdateUserDataCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to update user data");
            User newUser = User.askForUserData();
            userHistoryTracker.printAllUserEntries();
            break;
        case TodayCalorieProgressCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to print daily progress bar");
            mealEntries.printDaysConsumptionBar(currentUser, LocalDateTime.now());;
            break;
        case HistoricCalorieProgressCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to print Historic calorie bar");
            Optional<Integer> pastDays = parseDaysFromCommand(commandPair, 0);
            pastDays.ifPresent(days -> mealEntries.printHistoricConsumptionBars(currentUser, days));
            break;
        default:
            UI.printReply("Use a valid command", "Retry: ");
            break;
        }
    }

    /**
     * Takes in user input and structures it into a preprocessed pair of a main command and additional commands.
     * @param userInput
     * @return CommandPair
     */
    private CommandPair getCommandFromInput(String userInput) {
        String[] inputTokens = userInput.split(" ");
        String commandToken1 = inputTokens[0].strip();
        String commandToken2 = inputTokens[1].strip();
        String twoTokenCommand = commandToken1 + " " + commandToken2;
        String[] additionalCommands = IntStream.range(Math.min(2, inputTokens.length), inputTokens.length)
                .boxed()
                .map(index -> inputTokens[index])
                .map(token -> token.strip())
                .toArray(String[]::new);

        return new CommandPair(twoTokenCommand, additionalCommands);
    }

    public UserHistoryTracker getUserHistoryTracker() {
        return this.userHistoryTracker;
    }

    public String getMealOptionsStringWithNewMeal(String newMealString) {
        return UI.toMealOptionsString(this.mealOptions, newMealString);
    }

    public void cleanMealLists() {
        this.mealEntries = this.historyTracker.loadEmptyMealEntries();
        this.mealOptions = this.historyTracker.loadEmptyMealOptions();
        historyTracker.saveMealOptions(mealOptions);
        historyTracker.saveMealEntries(mealEntries);
    }

    public void printTodayCalorieProgress() {
        User currentUser = userHistoryTracker.checkForUserData(userHistoryTracker);
        mealEntries.printDaysConsumptionBar(currentUser, LocalDateTime.now());
    }

    /**
     * Tries to parse a certain command token of the customer into an integer
     * @param commandPair The considered commands
     * @param index The index of the command in the additionalCommands Array
     * @return Number of days (Integer)
     */
    private Optional<Integer> parseDaysFromCommand(CommandPair commandPair, int index) {
        try {
            int days =  Integer.parseInt(commandPair.getCommandByIndex(index));
            return Optional.of(days);
        } catch (NumberFormatException e) {
            UI.printReply(commandPair.getCommandByIndex(index), "The following is not a valid number: ");
        } catch (IndexOutOfBoundsException s) {
            UI.printReply("Specify the number of days you want to look into the past", "Missing input: ");
        }
        return Optional.empty();
    }

}
