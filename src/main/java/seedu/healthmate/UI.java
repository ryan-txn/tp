package seedu.healthmate;

public class UI {

    private static final String SEPARATOR = "_________________________________________________________________________";
    public static final String INDENTATION = "      ";
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
        System.out.println("");
        System.out.println(INDENTATION + actionPerformed + input);
        System.out.println(INDENTATION + SEPARATOR);
        System.out.println(INTENDATION + SEPARATOR);
        System.out.println(INTENDATION + actionPerformed + input);
        System.out.println(INTENDATION + SEPARATOR);
    }

    public static void printGreeting() {
        System.out.println(INDENTATION + LOGO);
        System.out.println(INDENTATION + SEPARATOR);
        System.out.println(INDENTATION + "Welcome to HealthMate");
        System.out.println(INDENTATION + "Let's get healthy!");
        System.out.println(INDENTATION + SEPARATOR);

    }

    public static void printFarewell() {
        System.out.println(INDENTATION + "Stay healthy!");
        System.out.println(INDENTATION + SEPARATOR);
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
            printReply("No meal options added yet", "");
        }
        printSeparator();
    }
  
    public static void printCommands() {
        System.out.println(INDENTATION + SEPARATOR);
        for (String command: Commands.getAllCommands()){
            System.out.println(INDENTATION + "-" + command);
        }
        System.out.println(INDENTATION + SEPARATOR);

    }

}
