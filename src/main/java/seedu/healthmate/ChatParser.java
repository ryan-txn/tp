package seedu.healthmate;

import seedu.healthmate.command.commands.LogMealsCommand;
import seedu.healthmate.command.commands.SaveMealCommand;
import seedu.healthmate.command.commands.ListCommandsCommand;
import seedu.healthmate.command.commands.AddMealEntryCommand;
import seedu.healthmate.command.commands.DeleteMealCommand;
import seedu.healthmate.command.commands.DeleteMealEntryCommand;
import seedu.healthmate.command.commands.MealMenuCommand;
import seedu.healthmate.command.commands.UpdateUserDataCommand;

import java.util.Scanner;



/**
 * Encapsulates the main logic of the application by parsing user input into objects
 * and storing them respectively.
 */
public class ChatParser {

    public static final String CALORIE_SIGNALLER = "/c";
    private static final String INDENTATION = "      ";

    private MealEntriesList mealEntries;
    private MealList mealOptions;
    private final HistoryTracker historyTracker;

    public ChatParser(){
        this.historyTracker = new HistoryTracker();
        UI.printSeparator();
        this.mealEntries = historyTracker.loadMealEntries();
        this.mealOptions = historyTracker.loadMealOptions();
        UI.printSeparator();
    }

    /**
     * Reads in user input from the command line
     * and initiates the parsing process steered by one-token and two-token-based user prompts.
     */
    public void run() {
        // check for health goal file existence and create file if none exists
        checkForUserData();

        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("bye")) {
            userInput = scanner.nextLine().strip();
            switch (userInput) {
            case "bye":
                UI.printFarewell();
                break;
            default:
                try {
                    this.multiCommandParsing(userInput);
                } catch (ArrayIndexOutOfBoundsException a) {
                    UI.printReply("Invalid command", "Retry: ");
                }
            }
        }
    }


    public void checkForUserData() {
        historyTracker.loadUserData();
    }

    /**
     * Steers the activation of features offered to the user via two-token commands
     * @param userInput String user input from the command line
     */
    public void multiCommandParsing(String userInput) {

        String[] inputTokens = userInput.split(" ");
        assert inputTokens.length > 1: "Should be at least 2";
        String commandToken1 = inputTokens[0].strip();
        String commandToken2 = inputTokens[1].strip();
        String command = commandToken1 + " " + commandToken2;

        switch (command) {
        case MealMenuCommand.COMMAND:
            UI.printMealOptions(this.mealOptions);
            break;
        case SaveMealCommand.COMMAND:
            mealOptions.appendMealFromString(userInput, command, mealOptions);
            historyTracker.saveMealOptions(mealOptions);
            break;
        case DeleteMealCommand.COMMAND:
            mealOptions.removeMealFromString(userInput, command);
            historyTracker.saveMealOptions(mealOptions);
            break;
        case DeleteMealEntryCommand.COMMAND:
            mealEntries.removeMealFromString(userInput, command);
            historyTracker.saveMealEntries(mealEntries);
            break;
        case AddMealEntryCommand.COMMAND:
            mealEntries.appendMealFromString(userInput, command, mealOptions);
            historyTracker.saveMealEntries(mealEntries);
            break;
        case LogMealsCommand.COMMAND:
            UI.printMealEntries(this.mealEntries);
            break;
        case ListCommandsCommand.COMMAND:
            UI.printCommands();
            break;
        case UpdateUserDataCommand.COMMAND:
            User currentUser = askForUserData();
            historyTracker.saveUserDataFile(currentUser);
            break;
        default:
            UI.printReply("Use a valid command", "Retry: ");
            break;
        }
    }

    public User askForUserData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(INDENTATION + "Please enter your " +
                "current height, weight, gender, age and health goal!");

        System.out.println(INDENTATION + "Height:");
        double height = Double.parseDouble(scanner.nextLine());

        System.out.println(INDENTATION + "Weight:");
        double weight = Double.parseDouble(scanner.nextLine());

        System.out.println(INDENTATION + "Gender:");
        String gender = scanner.nextLine();
        boolean isMale = (gender.equalsIgnoreCase("Male"));

        System.out.println(INDENTATION + "Age:");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.println(INDENTATION + "Health Goal:");
        String healthGoal = scanner.nextLine();

        return new User(height, weight, isMale, age, healthGoal);
    }

    public String toMealOptionsStringWithNew(String newMealString) {
        return UI.toMealOptionsString(this.mealOptions, newMealString);
    }

    public void cleanListsAfterTesting() {
        this.mealEntries = this.historyTracker.loadEmptyMealEntries();
        this.mealOptions = this.historyTracker.loadEmptyMealOptions();
        historyTracker.saveMealOptions(mealOptions);
        historyTracker.saveMealEntries(mealEntries);
    }

}
