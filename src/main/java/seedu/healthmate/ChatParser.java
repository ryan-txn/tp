package seedu.healthmate;

import seedu.healthmate.command.Command;
import seedu.healthmate.command.commands.LogMealsCommand;
import seedu.healthmate.command.commands.SaveMealCommand;
import seedu.healthmate.command.commands.ListCommandsCommand;
import seedu.healthmate.command.commands.AddMealEntryCommand;
import seedu.healthmate.command.commands.DeleteMealCommand;
import seedu.healthmate.command.commands.DeleteMealEntryCommand;
import seedu.healthmate.command.commands.MealMenuCommand;
import seedu.healthmate.command.commands.UpdateUserDataCommand;
import seedu.healthmate.command.CommandMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    public ChatParser(){
        Logging.setupLogger(logger, ChatParser.class.getName());
        this.historyTracker = new HistoryTracker();
        logger.log(Level.INFO, "Initializing HistoryTracker");
        UI.printSeparator();
        this.mealEntries = historyTracker.loadMealEntries();
        logger.log(Level.INFO, "Loaded MealEntries");
        this.mealOptions = historyTracker.loadMealOptions();
        logger.log(Level.INFO, "Loaded MealOptions");
        UI.printSeparator();
    }

    /**
     * Reads in user input from the command line
     * and initiates the parsing process steered by one-token and two-token-based user prompts.
     */
    public void run() {
        // check for health goal file existence and create file if none exists
        logger.log(Level.INFO, "Checking if user data exists");
        User user = User.checkForUserData(this.historyTracker);

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
                    this.multiCommandParsing(userInput, user);
                    logger.log(Level.INFO, "User input contains more than 1 token");
                } catch (ArrayIndexOutOfBoundsException a) {
                    logger.log(Level.WARNING, "Invalid command", a);
                    UI.printReply("Invalid command", "Retry: ");
                }
            }
        }
    }

    /**
     * Steers the activation of features offered to the user via two-token commands
     * @param userInput String user input from the command line
     */
    public void multiCommandParsing(String userInput, User user) {

        String[] inputTokens = userInput.split(" ");
        String commandToken1 = inputTokens[0].strip();
        String commandToken2 = inputTokens[1].strip();
        String command = commandToken1 + " " + commandToken2;
        logger.log(Level.INFO, "User command is: " + command);

        switch (command) {
        case MealMenuCommand.COMMAND:
            logger.log(Level.INFO, "Executing meal menu command to show meal options");
            UI.printMealOptions(this.mealOptions);
            break;
        case SaveMealCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to save meal to meal options");
            mealOptions.extractAndAppendMeal(userInput, command, mealOptions, user);
            historyTracker.saveMealOptions(mealOptions);
            break;
        case DeleteMealCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to delete a meal from meal options");
            mealOptions.extractAndRemoveMeal(userInput, command);
            historyTracker.saveMealOptions(mealOptions);
            break;
        case DeleteMealEntryCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to delete a meal from mealEntries");
            mealEntries.removeMealWithFeedback(userInput, command, user);
            historyTracker.saveMealEntries(mealEntries);
            break;
        case AddMealEntryCommand.COMMAND:
            logger.log(Level.INFO, "Executing command to add a meal to mealEntries");
            mealEntries.extractAndAppendMeal(userInput, command, mealOptions, user);
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
            User currentUser = User.askForUserData();
            historyTracker.saveUserDataFile(currentUser);
            break;
        default:
            UI.printReply("Use a valid command", "Retry: ");
            break;
        }
    }

    public HistoryTracker getHistoryTracker() {
        return this.historyTracker;
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

}
