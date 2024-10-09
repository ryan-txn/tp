package seedu.healthmate;

public class UI {

    private static final String SEPARATOR = "_________________________________________________________________________";
    private static final String INTENDATION = "      ";
    private static final String LOGO =
              INTENDATION + " |\n"
            + INTENDATION + "     \\\\|//\n"
            + INTENDATION + "     \\\\|//\n"
            + INTENDATION + "    \\\\\\|///\n"
            + INTENDATION + "    \\\\\\|///\n"
            + INTENDATION + "     \\\\|//\n"
            + INTENDATION + "      \\|/\n"
            + INTENDATION + "       |\n";


    public static void printReply(String input, String actionPerformed) {
        System.out.println(INTENDATION + SEPARATOR);
        System.out.println(INTENDATION + actionPerformed + input);
        System.out.println(INTENDATION + SEPARATOR);
    }

    public static void printGreeting() {
        System.out.println(INTENDATION + LOGO);
        System.out.println(INTENDATION + SEPARATOR);
        System.out.println(INTENDATION + "Welcome to HealthMate");
        System.out.println(INTENDATION + "Let's get healthy!");
        System.out.println(INTENDATION + SEPARATOR);
    }

    public static void printFarewell() {
        System.out.println(INTENDATION + "Stay healthy!");
        System.out.println(INTENDATION + SEPARATOR);
    }

    public static void printString(String input) {
        System.out.println(INTENDATION + input);
    }

    public static void printSeparator() {
        System.out.println(INTENDATION + SEPARATOR);
    }

    public static void printMealOptions(MealList mealOptions) {
        printSeparator();
        if (mealOptions.size() > 0) {
            for (int i = 0; i < mealOptions.size(); i++) {
                System.out.println(INTENDATION + (i + 1)
                        + ": " + mealOptions.toMealString(i));
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
                System.out.println(INTENDATION + (i + 1)
                        + ": " + mealEntries.toMealString(i));
            }
        } else {
            printReply("No meal entries added yet", "");
        }
        printSeparator();
    }
    public static void printCommands() {
        System.out.println(INTENDATION + SEPARATOR);
        for (String command: Commands.getAllCommands()){
            System.out.println(INTENDATION + "-" + command);
        }
        System.out.println(INTENDATION + SEPARATOR);

    }

    public static String simulateReply(String input, String actionPerformed) {
        String line1 = "\n";
        String line2 = INTENDATION + actionPerformed + input + "\n";
        String line3 = INTENDATION + SEPARATOR + "\n";
        return  line1 + line2 + line3;
    }

    public static String simulateFareWell() {
        String line1 = INTENDATION + "Stay healthy!" + "\n";
        String line2 = INTENDATION + SEPARATOR + "\n";
        return line1 + line2;
    }

    public static String simulateInitOutput() {
        return SEPARATOR + "\n" + INTENDATION + "Meal Entries Loaded Successfully!" + "\n"
                + INTENDATION + "Meal Options Loaded Successfully!" + "\n"
                + SEPARATOR+ "\n"
                + SEPARATOR;
    }

    public static String toMealOptionsString(MealList mealOptions) {
        String mealOptionsString = "";
        if (mealOptions.size() > 0) {
            for (int i = 0; i < mealOptions.size(); i++) {
                mealOptionsString += INTENDATION + i + ": " + mealOptions.toMealString(i) + "\n";
            }
        } else {
            mealOptionsString = simulateReply("No meal options added yet", "");
        }
        return mealOptionsString;
    }

    public static String toMealEntriesString(MealList mealEntries) {
        String mealOptionsString = "";
        if (mealEntries.size() > 0) {
            for (int i = 0; i < mealEntries.size(); i++) {
                mealOptionsString += INTENDATION + i + ": " + mealEntries.toMealString(i) + "\n";
            }
        } else {
            mealOptionsString = simulateReply("No meal options added yet", "");
        }
        return mealOptionsString;
    }

}
