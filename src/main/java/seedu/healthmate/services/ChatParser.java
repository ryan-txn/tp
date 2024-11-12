package seedu.healthmate.services;

import seedu.healthmate.command.CommandPair;
import seedu.healthmate.command.commands.MealLogCommand;
import seedu.healthmate.command.commands.SaveMealCommand;
import seedu.healthmate.command.commands.ListCommandsCommand;
import seedu.healthmate.command.commands.AddMealEntryCommand;
import seedu.healthmate.command.commands.DeleteMealCommand;
import seedu.healthmate.command.commands.DeleteMealEntryCommand;
import seedu.healthmate.command.commands.MealMenuCommand;
import seedu.healthmate.command.commands.UpdateUserDataCommand;
import seedu.healthmate.command.commands.ClearUserDataCommand;
import seedu.healthmate.command.commands.TodayCalorieProgressCommand;
import seedu.healthmate.command.commands.HistoricCalorieProgressCommand;
import seedu.healthmate.command.commands.CurrentUserDataCommand;
import seedu.healthmate.command.commands.MealRecommendationsCommand;
import seedu.healthmate.command.commands.WeightTimelineCommand;
import seedu.healthmate.command.commands.ByeCommand;

import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.MealList;
import seedu.healthmate.core.User;
import seedu.healthmate.utils.Logging;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Encapsulates the main logic of the application by parsing user input into objects
 * and storing them respectively.
 */
public class ChatParser {

    private static Logger logger = Logger.getLogger(ChatParser.class.getName());
    private MealEntriesList mealEntries;
    private MealList mealOptions;
    private final HistoryTracker historyTracker;
    private final UserHistoryTracker userHistoryTracker;

    public ChatParser(){
        Logging.setupLogger(logger, ChatParser.class.getName());

        UI.printSeparator();

        this.historyTracker = new HistoryTracker();
        logger.log(Level.INFO, "Initialized HistoryTracker");

        this.mealEntries = historyTracker.loadMealEntries(false);
        logger.log(Level.INFO, "Loaded MealEntries");

        this.mealOptions = historyTracker.loadMealOptions(false);
        logger.log(Level.INFO, "Loaded MealOptions");

        this.userHistoryTracker = new UserHistoryTracker();
        logger.log(Level.INFO, "Initializing UserHistoryTracker");

        this.assertCheckParserInit();
        logger.log(Level.INFO, "ChatParser correctly initialized.");

        UI.printSeparator();
    }

    /**
     * Reads in user input from the command line
     * and initiates the parsing process steered by one-token and two-token-based user prompts.
     */
    public void run() {

        logger.log(Level.INFO, "Checking if user data exists");
        User user = this.userHistoryTracker.checkForUserData();
        UI.printHelpReminder();
        assert user != null : "User entry should not be null";
        logger.log(Level.INFO, "User is: " + user);

        parseUserInput(user);
    }

    /**
     * Function simulating the above run() method with a User stub for testing.
     */
    public void simulateRunWithStub(User userStub) {
        assert userStub != null : "User stub should not be null";
        parseUserInput(userStub);
    }

    /**
     * Reads in user input via a scanner and maintains the main loop until the user enters "bye"
     * @param user The user profile connected with the current application run.
     */
    public void parseUserInput(User user) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals(ByeCommand.COMMAND)) {
            logger.log(Level.INFO, "Getting next user input line");
            userInput = scanner.nextLine().strip();
            logger.log(Level.INFO, "User input is: " + userInput);
            if (userInput.equals(ByeCommand.COMMAND)) {
                logger.log(Level.INFO, "User closes application");
                UI.printFarewell();
            } else {
                try {
                    logger.log(Level.INFO, "Start multicCommandParsing");
                    this.multiCommandParsing(userInput, user);
                } catch (ArrayIndexOutOfBoundsException a) {
                    logger.log(Level.WARNING, "Invalid command", a);
                    UI.printReply("Invalid command", "Retry: ");
                }
            }
        }
    }

    /**
     * Steers the execution of features activated by the user via multi-token commands.
     * @param userInput String the user's input from the command line.
     * @param user The user profile connected with the current application run.
     */
    public void multiCommandParsing(String userInput, User user) {
        assert userInput != null && !userInput.isEmpty() : "User input should not be null or empty";
        assert user != null : "User should not be null in multiCommandParsing";
        user = this.userHistoryTracker.checkForUserData();

        this.updateMealLists();
        CommandPair commandPair = getCommandFromInput(userInput);
        assert commandPair != null : "CommandPair should not be null";
        logger.log(Level.INFO, "User commands are: " + commandPair);

        String command = commandPair.getMainCommand();

        switch (command.toLowerCase()) {
        case MealMenuCommand.COMMAND_LOWER:
            MealMenuCommand.executeCommand(mealOptions, logger);
            break;
        case SaveMealCommand.COMMAND_LOWER:
            SaveMealCommand.executeCommand(historyTracker, mealOptions, userInput, logger);
            break;
        case DeleteMealCommand.COMMAND_LOWER:
            DeleteMealCommand.executeCommand(
                    historyTracker, mealOptions, userInput, command, logger);
            break;
        case DeleteMealEntryCommand.COMMAND_LOWER:
            DeleteMealEntryCommand.executeCommand(
                    historyTracker, mealEntries, user, userInput, command, logger);
            break;
        case AddMealEntryCommand.COMMAND_LOWER:
            AddMealEntryCommand.executeCommand(
                    historyTracker, mealOptions, mealEntries, user, userInput, command, logger);
            break;
        case MealLogCommand.COMMAND_LOWER:
            MealLogCommand.executeCommand(mealEntries, logger);
            break;
        case ListCommandsCommand.COMMAND_LOWER:
            ListCommandsCommand.executeCommand(userInput, command, logger);
            break;
        case UpdateUserDataCommand.COMMAND_LOWER:
            UpdateUserDataCommand.executeCommand(userHistoryTracker, logger);
            break;
        case ClearUserDataCommand.COMMAND_LOWER:
            ClearUserDataCommand.executeCommand(userHistoryTracker, logger);
            break;
        case CurrentUserDataCommand.COMMAND_LOWER:
            CurrentUserDataCommand.executeCommand(userHistoryTracker, logger);
            break;
        case TodayCalorieProgressCommand.COMMAND_LOWER:
            TodayCalorieProgressCommand.executeCommands(mealEntries, user, logger);
            break;
        case HistoricCalorieProgressCommand.COMMAND_LOWER:
            HistoricCalorieProgressCommand.executeCommand(mealEntries, commandPair, user, logger);
            break;
        case MealRecommendationsCommand.COMMAND_LOWER:
            MealRecommendationsCommand.executeCommand(user, logger);
            break;
        case WeightTimelineCommand.COMMAND_LOWER:
            WeightTimelineCommand.executeCommand(userHistoryTracker, logger);
            break;
        default:
            logger.log(Level.WARNING, "Invalid command received");
            UI.printReply("Use a valid command", "Retry: ");
            break;
        }
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

    //@@author kennethSty
    /**
     * Takes in user input and structures it into a preprocessed pair of a main command and additional commands.
     * @param userInput The user input from the command line.
     * @return CommandPair containing the main command and any additional commands.
     */
    private CommandPair getCommandFromInput(String userInput) {
        assert userInput != null && !userInput.isEmpty() : "User input should not be null or empty";

        String[] inputTokens = userInput.split(" ");
        assert inputTokens.length > 0 : "Input tokens should not be empty";

        String commandToken1 = inputTokens[0].strip();
        String commandToken2 = (inputTokens.length > 1) ? inputTokens[1].strip() : "";
        String twoTokenCommand = commandToken1 + (commandToken2.isEmpty() ? "" : " " + commandToken2);

        String[] additionalCommands = IntStream.range(2, inputTokens.length)
                .boxed()
                .map(i -> inputTokens[i].strip())
                .toArray(String[]::new);

        return new CommandPair(twoTokenCommand, additionalCommands);
    }
    //@@author


    /**
     * Loads the most recent mealList from the file, to ensure that data is
     * synchronized between multiple running instances of the app
     */
    private void updateMealLists() {
        this.mealOptions = historyTracker.loadMealOptions(true);
        this.mealEntries = historyTracker.loadMealEntries(true);
    }

    private void assertCheckParserInit() {
        assert this.historyTracker != null: "History Tracker should not be null";
        assert this.userHistoryTracker != null: "User History Tracker should not be null";
        assert this.mealEntries != null : "Meal entries list should not be null";
        assert this.mealOptions != null : "Meal options list should not be null";
    }




}
