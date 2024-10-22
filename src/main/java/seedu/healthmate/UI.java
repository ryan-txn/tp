package seedu.healthmate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import seedu.healthmate.command.Command;


public class UI {
    
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SEPARATOR =
            "_____________________________________________________________________________";
    private static final String INDENTATION = "      ";
    private static final String LINE = INDENTATION + SEPARATOR;
    private static final String FRAME_LINE = LINE + LINE_SEPARATOR;

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

    public static void printString(String input) {
        System.out.println(INDENTATION + input);
    }

    public static void printSeparator() {
        System.out.println(LINE);
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



    public static String simulateReply(String input, String actionPerformed) {
        String line1 = LINE_SEPARATOR;
        String line2 = INDENTATION + actionPerformed + input + LINE_SEPARATOR;
        return  line1 + line2 + FRAME_LINE;
    }

    public static String simulateFareWell() {
        String line1 = INDENTATION + "Stay healthy!" + LINE_SEPARATOR;;
        return line1 + FRAME_LINE;
    }

    public static String simulateInitOutput() {
        String line2 = INDENTATION + "Meal Entries Loaded Successfully!" + LINE_SEPARATOR;
        String line3 = INDENTATION + "Meal Options Loaded Successfully!" + LINE_SEPARATOR;
        return FRAME_LINE + line2 + line3 + FRAME_LINE + LINE;

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
            mealOptionsString += INDENTATION + (i + 1) + ": " + mealOptions.toMealStringByIndex(i) + LINE_SEPARATOR;
        }
        mealOptionsString += INDENTATION + (mealOptions.size() + 1) + ": " + newMealString + LINE_SEPARATOR;
        return LINE + LINE_SEPARATOR + mealOptionsString + LINE + LINE_SEPARATOR;
    }

    /**
     * Prints bar comparing actual versus an expected calorie consumption
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

        String consumptionBar = buildConsumptionBar(message, expectedValue, actualValue, timestamp);
        System.out.println(consumptionBar);
    }

    public static String buildConsumptionBar(String message,
                                       double expectedValue,
                                       int actualValue,
                                       LocalDate timestamp) {
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
                        return "|" + percentageOfExpected + "%|";
                    } else if (i <= ((percentageOfExpected / totalPercent) * hundredPercentMark)) {
                        return complete;
                    } else {
                        return incomplete;
                    }
                }).forEach(step -> builder.append(step));
        return INDENTATION + message + LINE_SEPARATOR
                + INDENTATION + builder + " (" + timestamp + ")"
                + LINE_SEPARATOR + LINE;

    }




}
