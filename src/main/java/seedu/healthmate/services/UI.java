package seedu.healthmate.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.MealEntry;
import seedu.healthmate.core.MealList;
import seedu.healthmate.core.User;
import seedu.healthmate.recommender.Recipe;
import seedu.healthmate.utils.DateTimeUtils;

/**
 * Handles UI interactions in a structured format.
 * Provides methods for printing messages to the user, format output and create UI elements such as consumption bars.
 */
public class UI {
    
    private static final String NEW_LINE = System.lineSeparator();
    private static final String SEPARATOR =
            "_____________________________________________________________________________";
    private static final String INDENTATION = "      ";
    private static final String LINE = INDENTATION + SEPARATOR;
    private static final String FRAME_LINE = LINE + NEW_LINE;
    private static final String LOGO =
              INDENTATION + " |\n"
            + INDENTATION + "     \\\\|//\n"
            + INDENTATION + "     \\\\|//\n"
            + INDENTATION + "    \\\\\\|///\n"
            + INDENTATION + "    \\\\\\|///\n"
            + INDENTATION + "     \\\\|//\n"
            + INDENTATION + "      \\|/\n"
            + INDENTATION + "       |\n";


    /**
     * Prints a formatted reply with a specified action and message.
     *
     * @param message The message to be printed to the user.
     * @param signaller A signaller, representing the kind of message.
     */
    public static void printReply(String message, String signaller) {
        System.out.println(LINE);
        System.out.println(INDENTATION + signaller + message);
        System.out.println(LINE);
    }

    /**
     * Prints an array of strings in the standard format of the UI
     * @param messages the strings to print to the user
     */
    public static void printMultiLineReply(List<String> messages) {
        System.out.println(LINE);
        messages.stream().forEach(message -> System.out.println(INDENTATION + message));
        System.out.println(LINE);
    }

    /** Prints a greeting message with a welcome logo. */
    public static void printGreeting() {
        System.out.println(INDENTATION + LOGO);
        System.out.println(LINE);
        System.out.println(INDENTATION + "Welcome to HealthMate");
        System.out.println(INDENTATION + "Let's get healthy!");
        System.out.println(LINE);
    }

    public static void printHelpReminder() {
        System.out.println(INDENTATION + "Use the `list commands` command to have a look at all commands.");
    }
    /** Prints a farewell message. */
    public static void printFarewell() {
        System.out.println(INDENTATION + "Stay healthy!");
        System.out.println(LINE);
    }

    public static void printSeparator() {
        System.out.println(LINE);
    }

    /** Prints a String with standard Indentation message. */
    public static void printString(String message) {
        System.out.println(INDENTATION + message);
    }

    /**
     * Prints the list of available meal options in a structured list format with indices signalling the position
     * of meals within the list.
     * @param mealOptions The list of meal options to display.
     */
    public static void printMealOptions(MealList mealOptions) {
        if (mealOptions.size() > 0) {
            printSeparator();
            for (int i = 0; i < mealOptions.size(); i++) {
                System.out.println(INDENTATION + (i + 1)
                        + ": " + mealOptions.toMealStringByIndex(i));
            }
            printSeparator();
        } else {
            printReply("No meal options added yet", "");
        }
    }

    /**
     * Prints the list of meal entries in a structured format, with indices indicating the position
     * of each entry in the list.
     * @param mealEntries The list of meal entries to display.
     */
    public static void printMealEntries(MealEntriesList mealEntries) {

        if (mealEntries.size() > 0) {
            printSeparator();
            for (int i = 0; i < mealEntries.size(); i++) {
                System.out.println(INDENTATION + (i + 1)
                        + ": " + mealEntries.toMealStringByIndex(i));
            }
            printSeparator();
        } else {
            printReply("No meal entries added yet", "");
        }

    }

    public static void printMealNotFound() {
        System.out.println(INDENTATION +  "The meal was not found in the meal menu!");
    }

    /**
     * Prints list of possible commands to the command line
     * @param commands A list of possible commands the user can choose to interact with the system
     */
    public static void printCommands(List<Command> commands) {
        System.out.println(LINE);
        if(commands.isEmpty()) {
            System.out.println(INDENTATION + "Command queried does not exist. Please use `command list` to view all " +
                    "searchable commands");
            System.out.println(LINE);
        } else if (commands.size() == 1) {
            System.out.println(INDENTATION + commands.get(0).toString());
            System.out.println(LINE);
        } else {
            System.out.println(INDENTATION + "Use `list commands <command>` to view a command's syntax");
            System.out.println(LINE);
            for (Command command : commands) {
                System.out.println(INDENTATION + command.shortDescription());
                System.out.println(LINE);
            }
        }
    }
    public static void printRecommendation(List<Recipe> recipes) {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Recommended recipes for your health goal");
        if (recipes.size() == 1) {
            System.out.println(INDENTATION + recipes.get(0).toString());
            System.out.println(LINE);
        } else {
            System.out.println(LINE);
            for (Recipe recipe : recipes) {
                System.out.println(INDENTATION + recipe.toString());
                System.out.println(LINE);
            }
        }
    }

    /**
     * Prints a progress bar comparing actual versus expected calorie consumption
     *
     * @param message Message printed if actual is 2x larger than expected with exact value
     * @param expectedValue double expected value
     * @param actualValue int actual value
     * @param timestamp timestamp in which the provided actualValue was consumed
     */
    public static void printConsumptionBar(String message,
                                           double expectedValue,
                                           int actualValue,
                                           LocalDate timestamp,
                                           boolean useSpecialChars) {
        assert timestamp != null : "Timestamp cannot be null";
        String consumptionBar = buildConsumptionBar(message, expectedValue, actualValue, timestamp, useSpecialChars);
        System.out.println(consumptionBar);
    }

    /**
     * High-level creation function of the progress bar.
     * Embedds the progress bar into the visual format of the UI
     *
     * @param message Message printed if actual is 2x larger than expected with exact value
     * @param expectedValue double expected value
     * @param actualValue int actual value
     * @param timestamp timestamp in which the provided actualValue was consumed
     * @return the progressBar in form of a String ready to be printed
     */
    public static String buildConsumptionBar(String message,
                                       double expectedValue,
                                       int actualValue,
                                       LocalDate timestamp,
                                       boolean useSpecialChars) {

        String header = INDENTATION + message + NEW_LINE;

        String progressBarBody = INDENTATION
                + progressBarStringBuilder(expectedValue, actualValue, useSpecialChars)
                + " (" + timestamp + ")"
                + NEW_LINE;

        String end = LINE;

        return header + progressBarBody + end;

    }

    /**
     * Prints a progress bar for historic consumption data.
     *
     * @param expectedValue the expected consumption value
     * @param actualValue   the actual consumption value
     * @param timestamp     the timestamp of the consumption data
     * @throws IllegalArgumentException if the timestamp is null
     */
    public static void printHistoricConsumptionBar(double expectedValue, int actualValue,
                                                   LocalDate timestamp, boolean useSpecialChars) {
        assert timestamp != null : "Timestamp cannot be null";
        System.out.println(INDENTATION
                + progressBarStringBuilder(expectedValue, actualValue, useSpecialChars)
                + " (" + timestamp + ")");
    }

    /**
     * Prints a summary of historic consumption statistics over a specified period.
     *
     * @param days The number of days over which statistics are calculated.
     * @param idealCalories The ideal daily calorie intake for comparison.
     * @param totalCaloriesConsumed The total calories consumed over the specified period.
     * @param totalIdealCalories The total ideal calorie intake for the specified period.
     * @param maxMeal A meal entry with the highest calorie count (possibly empty).
     */
    public static void printHistoricConsumptionStats(int days,
                                                     int idealCalories,
                                                     int totalCaloriesConsumed,
                                                     int totalIdealCalories,
                                                     Optional<MealEntry> maxMeal) {
        LocalDateTime today = DateTimeUtils.currentDate().atTime(23, 59);

        LocalDateTime maxConsumptionDate = maxMeal
                .map(mealEntry -> mealEntry.getTimestamp())
                .orElse(today);
        int maxCaloriesConsumed = maxMeal
                .map(mealEntry -> mealEntry.getCalories())
                .orElse(0);
        String maxMealString = maxMeal
                .map(mealEntry -> mealEntry.toString())
                .orElse("No maximum meal available");
        double percentOfIdealConsumed = Math.round(100.0 * (double)totalCaloriesConsumed
                / (double)totalIdealCalories);
        double percentMaxOfIdeal = Math.round(100.0 * (double)maxCaloriesConsumed
                / (double)idealCalories);

        UI.printString("Stats over past " + days + " days");
        UI.printString("Total Calories Consumed: " + totalCaloriesConsumed);
        UI.printString("Total Ideal Calories: " + totalIdealCalories);
        UI.printString("Percentage of Total Ideal Calories : " + percentOfIdealConsumed + "%");
        UI.printString( "Day With Heaviest Meal: " + maxConsumptionDate.toLocalDate());
        UI.printString("Heaviest Meal Consumed: " + maxMealString);
        UI.printString("Meals Consumption's Percentage of Daily Ideal Calories: " + percentMaxOfIdeal + "%");
        UI.printSeparator();
    }

    /**
     * Builds a string representing a progress bar
     * that visualizes the percentage of an actual value relative to an target value with filled/unfilled
     * and the percentage number in the midpoint of the bar.
     * 100 % is reached at the middle to allow to visualize "overshooting".
     * The whole bar is filled when the actual value is 200% of the target value.
     * If more than 200% is reached, the bar stops "filling" but the percentage in the middle keeps growing accordingly.
     * Inspired by:
     * https://medium.com/javarevisited/how-to-display-progressbar-on-the-standard-console-using-java-18f01d52b30e
     *
     * @param targetValue The target value for the progress calculation.
     * @param actualValue The actual value achieved, which is used to determine progress percentage.
     * @return A string representing the progress bar.
     */
    public static String progressBarStringBuilder(double targetValue, int actualValue, boolean useSpecialChars) {
        int percentageOfExpected = (int) Math.ceil((actualValue / targetValue) * 100);

        String incomplete = useSpecialChars ? "░" : "-"; // U+2591 Unicode Character
        String complete = useSpecialChars ? "█" : "*"; // U+2588 Unicode Character


        int numberOfBoxes = 60;
        double totalPercent = 100.0;
        int hundredPercentMark = (numberOfBoxes / 2);
        StringBuilder builder = new StringBuilder();

        IntStream.rangeClosed(1, numberOfBoxes)
                .boxed()
                .map(i -> {
                    //maps progress from 100 percent scale to numberOfIcons scale
                    if (i == hundredPercentMark) {
                        return "|" + String.format("%6s", percentageOfExpected + "%|");
                    } else if (i <= ((percentageOfExpected / totalPercent) * hundredPercentMark)) {
                        return complete;
                    } else {
                        return incomplete;
                    }
                }).forEach(step -> builder.append(step));

        return builder.toString();
    }


    // Functions to simulate UI behaviour for testing


    /**
     * Outputs the result of list meals as a String if a newMealString would be added at the end.
     * Used for testing.
     * @param mealOptions List of meal options to which a meal should be added
     * @param newMealString The string representing the meal that should be added to the mealOptions.
     * @return String representing the output the user would see if this meal would be correclty added to mealOptions.
     */
    public static String toMealOptionsString(MealList mealOptions, String newMealString) {
        String mealOptionsString = "";
        for (int i = 0; i < mealOptions.size(); i++) {
            mealOptionsString += INDENTATION + (i + 1) + ": " + mealOptions.toMealStringByIndex(i) + NEW_LINE;
        }
        mealOptionsString += INDENTATION + (mealOptions.size() + 1) + ": " + newMealString + NEW_LINE;
        return LINE + NEW_LINE + mealOptionsString + LINE + NEW_LINE;
    }

    /**
     * Function that simulates the behaviour of {@code UI.printReply()} for testing
     * @param message The message to be printed to the user.
     * @param signaller A signaller, representing the kind of message.
     * @return A formatted String that would be printed to the console if using {@code UI.printReply()}
     */
    public static String simulateReply(String message, String signaller) {
        String line1 = FRAME_LINE;
        String line2 = INDENTATION + signaller + message + NEW_LINE;
        return  line1 + line2 + FRAME_LINE;
    }

    /**
     * Simulates the output of {@code printString()} in the form of a string.
     *
     * @param message The message string to be simulated.
     * @return A formatted String representing the simulated message.
     */
    public static String simulateString(String message) {
        return INDENTATION + message + NEW_LINE;
    }

    /**
     * Simulates a farewell message output for testing purposes.
     * @return A String representing the farewell message to the user.
     */
    public static String simulateFareWell() {
        String line1 = INDENTATION + "Stay healthy!" + NEW_LINE;;
        return line1 + FRAME_LINE;
    }

    /**
     * Simulates the initial output for successfully loaded meal entries and meal options.
     * @return A String representing the formatted initial output message.
     */
    public static String simulateInitOutputAddMeal(){
        String line2 = INDENTATION + "Meal Entries Loaded Successfully!" + NEW_LINE;
        String line3 = INDENTATION + "Meal Options Loaded Successfully!" + NEW_LINE;
        return FRAME_LINE + line2 + line3 + FRAME_LINE;
    }

    public static String simulateInitOutput() {
        String line2 = INDENTATION + "Meal Entries Loaded Successfully!" + NEW_LINE;
        String line3 = INDENTATION + "Meal Options Loaded Successfully!" + NEW_LINE;
        String line4 = INDENTATION + "Use the `list commands` command to have a look at all commands." + NEW_LINE;
        return FRAME_LINE + line2 + line3 + FRAME_LINE +line4;
    }



    /**
     * Simulates the progress bar for historic consumption with a timestamp for testing.
     *
     * @param targetValue The target value for comparison.
     * @param actualValue The actual value to display.
     * @param timestamp The timestamp when the consumption occurred.
     * @return A formatted String with the progress bar and timestamp.
     * @throws IllegalArgumentException if {@code timestamp} is null.
     */
    public static String simulateHistoricConsumptionBar(double targetValue, int actualValue, LocalDate timestamp) {
        assert timestamp != null : "Timestamp cannot be null";
        return INDENTATION + progressBarStringBuilder(targetValue, actualValue, true) + " (" + timestamp + ")";
    }

    /**
     * Simulates the construction of a user-specific consumption bar for testing.
     *
     * @param caloriesConsumed The actual calories consumed by the user.
     * @param timestamp        The date for which the consumption is being simulated.
     * @return A string representation of the simulated consumption bar, including
     *         target calories, current calories consumed, and the percentage of
     *         expected calorie intake consumed.
     */
    public static String simulateUsersConsumptionBar(int caloriesConsumed, LocalDate timestamp, User user) {
        Integer targetCalories = user.getTargetCalories();
        return UI.simulateReply("Ideal Daily Caloric Intake: " + targetCalories, "")
                + UI.simulateString("Current Calories Consumed: " + caloriesConsumed)
                + UI.buildConsumptionBar("% of Expected Calorie Intake Consumed: ",
                targetCalories,
                caloriesConsumed,
                timestamp,
                true);
    }


}
