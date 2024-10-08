package seedu.healthmate;

import java.util.Arrays;
import java.util.List;

public class Commands {
    public static final String LIST_COMMANDS = "list commands";
    public static final String LOG_MEALS = "log meals";
    public static final String ADD_MEAL_ENTRY = "add mealEntry";
    public static final String SAVE_MEAL = "save meal";
    public static final String MEAL_MENU = "meal menu";

    // Method to return all commands as a List
    public static List<String> getAllCommands() {
        return Arrays.asList(
                LIST_COMMANDS,
                LOG_MEALS,
                ADD_MEAL_ENTRY,
                SAVE_MEAL,
                MEAL_MENU
        );
    }

    // Method to print all commands
    public static void printAllCommands() {
        System.out.println("Available Commands:");
        for (String command : getAllCommands()) {
            System.out.println(command);
        }
    }
}

