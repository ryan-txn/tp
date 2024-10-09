package seedu.healthmate;

import java.util.Scanner;



/**
 * Encapsulates the main logic of the application by parsing user input into objects
 * and storing them respectively.
 */
public class ChatParser {

    public static final String CALORIE_SIGNALLER = "/c";

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
        askForHealthGoal();

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


    public void askForHealthGoal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(UI.INDENTATION + "Before we begin, please enter your current height, weight and health goal!");

        System.out.println(UI.INDENTATION + "Weight: ");
        double weight = scanner.nextDouble();
        User.addWeightEntry(weight);

        System.out.println(UI.INDENTATION + "Height: "); 
        double height = scanner.nextDouble();
        User.addHeightEntry(height);

        System.out.println(UI.INDENTATION + "Health Goal: ");
        String healthGoal = scanner.nextLine();
        parseHealthGoal(healthGoal);

    }

    /**
     * Steers the activation of features offered to the user via two-token commands
     * @param userInput String user input from the command line
     */
    public void multiCommandParsing(String userInput) {
        String[] inputTokens = userInput.split(" ");
        String commandToken1 = inputTokens[0].strip();
        String commandToken2 = inputTokens[1].strip();
        String command = commandToken1 + " " + commandToken2;
        switch (command) {
        case Commands.MEAL_MENU:
            UI.printMealOptions(this.mealOptions);
            break;
        case Commands.SAVE_MEAL:
            mealOptions.appendMealFromString(userInput, command);
            historyTracker.saveMealOptions(mealOptions);
            break;
        case Commands.DELETE_MEAL:
            mealOptions.removeMealFromString(userInput, command);
            historyTracker.saveMealOptions(mealOptions);
            break;
        case Commands.DELETE_MEAL_ENTRY:
            mealEntries.removeMealFromString(userInput, command);
            historyTracker.saveMealEntries(mealEntries);
            break;
        case Commands.ADD_MEAL_ENTRY:
            mealEntries.appendMealFromString(userInput, command);
            historyTracker.saveMealEntries(mealEntries);
            break;
        case Commands.LOG_MEALS:
            UI.printMealEntries(this.mealEntries);
            break;
        case Commands.LIST_COMMANDS:
            UI.printCommands();
            break;
        default:
            UI.printReply("Use a valid command", "Retry: ");
            break;
        }
    }

    public void parseHealthGoal(String healthGoal) {
        switch (healthGoal) {
        case "cut weight":
            break;
        case "bulk up":
            break;
        case "higher wellbeing":
            break;
        default:
            break;
        }
    }

}
