package seedu.healthmate;

import java.util.Scanner;

public class ChatParser {

    public static final String CALORIE_SIGNALLER = "/c";
    private MealEntriesList mealEntries;
    private MealList mealOptions;
    private final HistoryTracker historyTracker;

    public ChatParser(MealEntriesList mealEntries, MealList mealOptions){
        this.historyTracker = new HistoryTracker();
        this.mealEntries = historyTracker.loadMealEntries();
        this.mealOptions = historyTracker.loadMealOptions();
    }

    public void run() {
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

    public void multiCommandParsing(String userInput) {
        String[] inputTokens = userInput.split(" ");
        String commandToken1 = inputTokens[0].strip();
        String commandToken2 = inputTokens[1].strip();
        String command = commandToken1 + " " + commandToken2;
        switch (command) {
        case "list meals":
            UI.printMealOptions(this.mealOptions);
            break;
        case "save meal":
            mealOptions.appendMealFromString(userInput, command);
            historyTracker.saveMealOptions(mealOptions);
            break;
        case "add mealEntry":
            mealEntries.appendMealFromString(userInput, command);
            historyTracker.saveMealEntries(mealEntries);
            break;
        case "log meals":
            UI.printMealEntries(this.mealEntries);
            break;
        default:
            UI.printReply("Use a valid command", "Retry");
            break;
        }
    }

}
