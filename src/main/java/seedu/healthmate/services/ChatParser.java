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
import seedu.healthmate.command.commands.MealRecommendationsCommand;
import seedu.healthmate.command.commands.WeightTimelineCommand;

import seedu.healthmate.command.CommandMap;
import seedu.healthmate.core.Meal;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.MealList;
import seedu.healthmate.core.User;
import seedu.healthmate.core.UserEntryList;
import seedu.healthmate.core.UserHistoryTracker;
import seedu.healthmate.core.WeightEntryDisplay;
import seedu.healthmate.recommender.Recipe;
import seedu.healthmate.recommender.RecipeMap;
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
        assert mealEntries != null : "Meal entries list should not be null";
        logger.log(Level.INFO, "Loaded MealEntries");
        this.mealOptions = historyTracker.loadMealOptions();
        assert mealOptions != null : "Meal options list should not be null";
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
        logger.log(Level.INFO, "Checking if user data exists");
        User userEntry = userHistoryTracker.checkForUserData(this.userHistoryTracker);
        assert userEntry != null : "User entry should not be null";
        parseUserInput(userEntry);
    }

    /**
     * Function simulating the above run() method with a User stub for testing.
     */
    public void simulateRunWithStub(User userStub) {
        assert userStub != null : "User stub should not be null";
        logger.log(Level.INFO, "Checking if user data exists");
        parseUserInput(userStub);
    }

    /**
     * Reads in user input via a scanner and maintains the main loop until the user
     * exits the application with "Bye".
     * @param user The user profile connected with the current application run.
     */
    public void parseUserInput(User user) {
        assert user != null : "User should not be null in parseUserInput";
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("bye")) {
            logger.log(Level.INFO, "Getting next user input line");
            userInput = scanner.nextLine().strip();
            if (userInput.equals("bye")) {
                logger.log(Level.INFO, "User closes application");
                UI.printFarewell();
            } else {
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
     * Steers the activation of features offered to the userEntry via two-token commands.
     * @param userInput String userEntry input from the command line.
     * @param user The user profile connected with the current application run.
     */
    public void multiCommandParsing(String userInput, User user) {
        assert userInput != null && !userInput.isEmpty() : "User input should not be null or empty";
        assert user != null : "User should not be null in multiCommandParsing";

        CommandPair commandPair = getCommandFromInput(userInput);
        assert commandPair != null : "CommandPair should not be null";

        String command = commandPair.getMainCommand();
        logger.log(Level.INFO, "User commands are: " + commandPair);

        switch (command) {
            case MealMenuCommand.COMMAND:
                assert mealOptions != null : "Meal options list should not be null";
                logger.log(Level.INFO, "Executing meal menu command to show meal options");
                UI.printMealOptions(this.mealOptions);
                break;

            case SaveMealCommand.COMMAND:
                assert historyTracker != null : "HistoryTracker should not be null";
                MealSaver mealSaver = new MealSaver(historyTracker);
                Optional<Meal> mealToSave = mealSaver.extractMealFromUserInput(userInput);
                assert mealToSave.isPresent() : "Meal to save should be present";
                mealToSave.ifPresent(meal -> mealSaver.saveMeal(meal, mealOptions));
                break;

            case DeleteMealCommand.COMMAND:
                assert mealOptions != null : "Meal options list should not be null";
                logger.log(Level.INFO, "Executing command to delete a meal from meal options");
                mealOptions.extractAndRemoveMeal(userInput, command);
                historyTracker.saveMealOptions(mealOptions);
                break;

            case DeleteMealEntryCommand.COMMAND:
                assert mealEntries != null : "Meal entries list should not be null";
                logger.log(Level.INFO, "Executing command to delete a meal from mealEntries");
                mealEntries.removeMealWithFeedback(userInput, command, user);
                historyTracker.saveMealEntries(mealEntries);
                break;

            case AddMealEntryCommand.COMMAND:
                assert mealOptions != null : "Meal options list should not be null";
                assert mealEntries != null : "Meal entries list should not be null";
                logger.log(Level.INFO, "Executing command to add a meal to mealEntries");
                mealEntries.extractAndAppendMeal(userInput, command, mealOptions, user);
                historyTracker.saveMealEntries(mealEntries);
                break;

            case LogMealsCommand.COMMAND:
                assert mealEntries != null : "Meal entries list should not be null";
                logger.log(Level.INFO, "Executing command to show meal history");
                UI.printMealEntries(this.mealEntries);
                break;

            case ListCommandsCommand.COMMAND:
                List<Command> commands = CommandMap.getCommands(userInput, command);
                assert commands != null : "Commands list should not be null";
                logger.log(Level.INFO, "Executing command to show all available commands");
                UI.printCommands(commands);
                break;

            case UpdateUserDataCommand.COMMAND:
                logger.log(Level.INFO, "Executing command to update user data");
                User newUser = User.askForUserData();
                assert newUser != null : "New user data should not be null";
                userHistoryTracker.printAllUserEntries();
                break;

            case TodayCalorieProgressCommand.COMMAND:
                assert mealEntries != null : "Meal entries list should not be null";
                logger.log(Level.INFO, "Executing command to print daily progress bar");
                mealEntries.printDaysConsumptionBar(user, LocalDateTime.now());
                break;

            case HistoricCalorieProgressCommand.COMMAND:
                assert mealEntries != null : "Meal entries list should not be null";
                logger.log(Level.INFO, "Executing command to print historic calorie bar");
                Optional<Integer> pastDays = parseDaysFromCommand(commandPair, 0);
                assert pastDays.isPresent() : "Past days should be specified";
                pastDays.ifPresent(days -> mealEntries.printHistoricConsumptionBars(user, days));
                break;

            case MealRecommendationsCommand.COMMAND:
                assert user.getHealthGoal() != null : "User health goal should not be null";
                List<Recipe> recipes = RecipeMap.getRecipesByGoal(user.getHealthGoal());
                assert recipes != null && !recipes.isEmpty() : "Recipes should not be null or empty";
                logger.log(Level.INFO, "Executing command to list meal recommendation");
                UI.printRecommendation(recipes);
                break;

            case WeightTimelineCommand.COMMAND:
                logger.log(Level.INFO, "Executing command to print weight timeline");
                Optional<UserEntryList> userHistoryData = userHistoryTracker.loadUserData();
                assert userHistoryData != null && userHistoryData.isPresent() : "User history data should not be null or empty";
                WeightEntryDisplay.display(userHistoryData);
                break;

            default:
                logger.log(Level.WARNING, "Invalid command received");
                UI.printReply("Use a valid command", "Retry: ");
                break;
        }
    }

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
                .mapToObj(i -> inputTokens[i].strip())
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
        assert currentUser != null : "User should not be null";
        mealEntries.printDaysConsumptionBar(currentUser, LocalDateTime.now());
    }

    /**
     * Tries to parse a certain command token of the customer into an integer
     * @param commandPair The considered commands
     * @param index The index of the command in the additionalCommands Array
     * @return Number of days (Integer)
     */
    private Optional<Integer> parseDaysFromCommand(CommandPair commandPair, int index) {
        assert commandPair != null : "CommandPair should not be null";
        assert index >= 0 : "Index should be non-negative";
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
