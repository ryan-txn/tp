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
import seedu.healthmate.recommender.Recipe;
import seedu.healthmate.utils.DateTimeUtils;


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


    public static void printReply(String input, String actionPerformed) {
        System.out.println(LINE);
        System.out.println(INDENTATION + actionPerformed + input);
        System.out.println(LINE);
    }

    public static void printGreeting() {
        System.out.println(INDENTATION + LOGO);
        System.out.println(LINE);
        System.out.println(INDENTATION + "Welcome to HealthMate");
        System.out.println(INDENTATION + "Let's get healthy!");
        System.out.println(LINE);
    }

    public static void printFarewell() {
        System.out.println(INDENTATION + "Stay healthy!");
        System.out.println(LINE);
    }

    public static void printSeparator() {
        System.out.println(LINE);
    }

    public static void printString(String input) {
        System.out.println(INDENTATION + input);
    }

    public static void printMealOptions(MealList mealOptions) {
        printSeparator();
        if (mealOptions.size() > 0) {
            for (int i = 0; i < mealOptions.size(); i++) {
                System.out.println(INDENTATION + (i + 1)
                        + ": " + mealOptions.toMealStringByIndex(i));
            }
        } else {
            printReply("No meal options added yet", "");
        }
        printSeparator();
    }

    public static void printMealEntries(MealEntriesList mealEntries) {
        printSeparator();
        if (mealEntries.size() > 0) {
            for (int i = 0; i < mealEntries.size(); i++) {
                System.out.println(INDENTATION + (i + 1)
                        + ": " + mealEntries.toMealStringByIndex(i));
            }
        } else {
            printReply("No meal entries added yet", "");
        }
        printSeparator();
    }

    public static void printMealNotFound() {
        System.out.println("The meal was not found in the meal menu!");
    }

    /**
     * Prints list of possible commands to the command line
     * @param commands A list of possible commands the user can choose to interact with the system
     */
    public static void printCommands(List<Command> commands) {
        System.out.println(LINE);
        if (commands.size() == 1) {
            System.out.println(INDENTATION + commands.get(0).toString());
        } else {
            System.out.println(INDENTATION + "Use `list commands <command>` to view a command's syntax");
            System.out.println(LINE);
            for (Command command : commands) {
                System.out.println(INDENTATION + command.command);
                System.out.println(LINE);
            }
        }
    }
    public static void printRecommendation(List<Recipe> recipes) {
        System.out.println(LINE);
        System.out.println(INDENTATION + "Recommended recipes for your health goal");
        if (recipes.size() == 1) {
            System.out.println(INDENTATION + recipes.get(0).toString());
        } else {
            System.out.println(LINE);
            for (Recipe recipe : recipes) {
                System.out.println(INDENTATION + recipe.toString());
                System.out.println(LINE);
            }
        }
    }

    /**
     * Outputs the result of list meals as a String if a newMealString would be added at the end
     * @param mealOptions
     * @param newMealString
     * @return
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
     * Prints bar comparing actual versus an expected calorie consumption
     *
     * Inspired by:
     * https://medium.com/javarevisited/how-to-display-progressbar-on-the-standard-console-using-java-18f01d52b30e
     * @param message Message printed if actual is 2x larger than expected with exact value
     * @param expectedValue double expected value
     * @param actualValue int actual value
     * @param timestamp timestamp in which the provided actualValue was consumed
     */
    public static void printConsumptionBar(String message,
                                           double expectedValue,
                                           int actualValue,
                                           LocalDate timestamp) {
        assert timestamp != null : "Timestamp cannot be null";
        String consumptionBar = buildConsumptionBar(message, expectedValue, actualValue, timestamp);
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
                                       LocalDate timestamp) {

        return INDENTATION + message + NEW_LINE
                + INDENTATION + progressBarStringBuilder(expectedValue, actualValue) + " (" + timestamp + ")"
                + NEW_LINE + LINE;

    }

    /**
     * Prints a progress bar for historic consumption data.
     *
     * @param expectedValue the expected consumption value
     * @param actualValue   the actual consumption value
     * @param timestamp     the timestamp of the consumption data
     * @throws IllegalArgumentException if the timestamp is null
     */
    public static void printHistoricConsumptionBar(double expectedValue, int actualValue, LocalDate timestamp) {
        assert timestamp != null : "Timestamp cannot be null";
        System.out.println(INDENTATION
                + progressBarStringBuilder(expectedValue, actualValue)
                + " (" + timestamp + ")");
    }

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

        double percentOfIdealConsumed = Math.round(100.0 * (double)totalCaloriesConsumed / (double)totalIdealCalories);
        double percentMaxOfIdeal = Math.round(100.0 * (double)maxCaloriesConsumed / (double)idealCalories);
        UI.printString("Stats over past " + days + " days");
        UI.printString("Total Calories Consumed: " + totalCaloriesConsumed);
        UI.printString("Total Ideal Calories: " + totalIdealCalories);
        UI.printString("Percentage of Total Ideal Calories : " + percentOfIdealConsumed + "%");
        UI.printString( "Day With Heaviest Meal: " + maxConsumptionDate.toLocalDate());
        UI.printString("Heaviest Meal Consumed: " + maxMealString);
        UI.printString("Meals Consumption's Percentage of Daily Ideal Calories: " + percentMaxOfIdeal + "%");
        UI.printSeparator();
    }

    public static String progressBarStringBuilder(double expectedValue, int actualValue) {
        int percentageOfExpected = (int) Math.ceil((actualValue / expectedValue) * 100);

        String incomplete = "░"; // U+2591 Unicode Character
        String complete = "█"; // U+2588 Unicode Character

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
    public static String simulateReply(String input, String actionPerformed) {
        String line1 = FRAME_LINE;
        String line2 = INDENTATION + actionPerformed + input + NEW_LINE;
        return  line1 + line2 + FRAME_LINE;
    }

    public static String simulateString(String input) {
        return INDENTATION + input + NEW_LINE;
    }

    public static String simulateFrameLine() {
        return FRAME_LINE;
    }

    public static String simulateFareWell() {
        String line1 = INDENTATION + "Stay healthy!" + NEW_LINE;;
        return line1 + FRAME_LINE;
    }

    public static String simulateInitOutput() {
        String line2 = INDENTATION + "Meal Entries Loaded Successfully!" + NEW_LINE;
        String line3 = INDENTATION + "Meal Options Loaded Successfully!" + NEW_LINE;
        return FRAME_LINE + line2 + line3 + FRAME_LINE;

    }

    public static String simulateHistoricConsumptionBar(double expectedValue, int actualValue, LocalDate timestamp) {
        assert timestamp != null : "Timestamp cannot be null";
        return INDENTATION + progressBarStringBuilder(expectedValue, actualValue) + " (" + timestamp + ")";
    }


}
