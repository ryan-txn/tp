package seedu.healthmate;

import java.util.Arrays;
import java.util.List;

public class Commands {
    public static final String LIST_COMMANDS = "list commands";
    public static final String LOG_MEALS = "log meals";
    public static final String ADD_MEAL_ENTRY = "add mealEntry";
    public static final String SAVE_MEAL = "save meal";
    public static final String MEAL_MENU = "meal menu";
    public static final String UPDATE_USER_DATA = "update userdata";
    public static final String DELETE_MEAL_ENTRY = "delete mealEntry";
    public static final String DELETE_MEAL = "delete meal";


    // Method to return all commands as a List
    public static List<String> getAllCommands() {
        return Arrays.asList(
                LIST_COMMANDS,
                LOG_MEALS,
                ADD_MEAL_ENTRY,
                SAVE_MEAL,
                MEAL_MENU,
                UPDATE_USER_DATA
        );
    }
}

