package seedu.healthmate.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import seedu.healthmate.recommender.Goals;
import seedu.healthmate.services.UI;
import seedu.healthmate.services.UserHistoryTracker;

/**
 * Represents a user record captured at a specific date and time.
 */
public class User {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final double idealCalories;
    private final double heightEntry;
    private final double weightEntry;
    private final boolean isMale;
    private final int age;
    private final HealthGoal healthGoal;
    private final LocalDateTime localDateTime;
    private final boolean isAbleToSeeSpecialChars;

    /**
     * Constructs a new User object with the specified details. This constructor
     * is used when collecting data directly from the user.
     *
     * @param height      User's height in centimeters.
     * @param weight      User's weight in kilograms.
     * @param isMale      True if the user is male, false otherwise.
     * @param age         User's age in years.
     * @param healthGoal  The health goal for the user (e.g., weight loss, muscle gain).
     */
    public User(double height, double weight, boolean isMale,
                int age, HealthGoal healthGoal, boolean isAbleToSeeSpecialChars) {
        this.heightEntry = height;
        this.weightEntry = weight;
        this.isMale = isMale;
        this.age = age;
        this.healthGoal = healthGoal;
        this.idealCalories = this.healthGoal.getTargetCalories(height, weight, isMale, age);
        this.localDateTime = LocalDateTime.now();
        this.isAbleToSeeSpecialChars = isAbleToSeeSpecialChars;
    }

    /**
     * Constructs a User object from previously saved data. This constructor
     * is used when loading user information from storage.
     *
     * @param height         User's height in centimeters.
     * @param weight         User's weight in kilograms.
     * @param isMale         True if the user is male, false otherwise.
     * @param age            User's age in years.
     * @param healthGoal     The health goal for the user (e.g., weight loss, muscle gain).
     * @param idealCalories  The previously calculated ideal calorie intake for the user.
     * @param localDateTime  The timestamp of the last user data entry in ISO-8601 format.
     */
    public User(double height, double weight, boolean isMale, int age,
                String healthGoal, double idealCalories, String localDateTime, boolean isAbleToSeeSpecialChars) {
        this.heightEntry = height;
        this.weightEntry = weight;
        this.isMale = isMale;
        this.age = age;
        this.healthGoal = new HealthGoal(healthGoal);
        this.idealCalories = idealCalories;
        this.localDateTime = LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER);
        this.isAbleToSeeSpecialChars = isAbleToSeeSpecialChars;
    }

    /**
     * Asks user to input specifics for creating a new User instance
     * @return A new user instance created with the data inputted by user.
     */
    public static User  askForUserData() {
        try {
            Scanner scanner = new Scanner(System.in);

            UI.printString("Create your profile: please enter...");

            double height = askForHeight(scanner);
            double weight = askForWeight(scanner);
            boolean isMale = askForGender(scanner);
            int age = askForAge(scanner);
            HealthGoal healthGoal = askForHealthGoal(scanner);
            boolean isAbleToSeeSpecialChars = askForSpecialChars(scanner);

            User user = new User(height, weight, isMale, age, healthGoal, isAbleToSeeSpecialChars);
            UI.printString("Profile creation Successful!");
            UI.printReply("Great! You can now begin to use the app!", "");

            UserHistoryTracker userHistoryTracker = new UserHistoryTracker();
            userHistoryTracker.saveUserToFile(user);

            return user;
        } catch (Exception exception) {
            UI.printReply("Wrong user input: " + exception.getMessage(), "Retry: ");
            return askForUserData();
        }
    }

    /**
     * Returns ideal calories to be consumed by this user instance
     * @return double Ideal calorie consumption
     */
    public int getTargetCalories() {
        return (int) this.idealCalories;
    }

    public boolean isAbleToSeeSpecialChars() {
        return this.isAbleToSeeSpecialChars;
    }

    /**
     * Creates a specific user profile for isolated testing.
     * @return User profile
     */
    public static User createUserStub() {
        HealthGoal bulkGoal = new HealthGoal("BULKING");
        return new User(180, 80.0, true, 20, bulkGoal, true);
    }

    public static User createAlternativeUserStub() {
        HealthGoal steadyGoal = new HealthGoal("STEADY_STATE");
        return new User(200, 200, false, 82, steadyGoal, true);
    }


    @Override
    public String toString() {
        return heightEntry + ","
                + weightEntry + ","
                + isMale + ","
                + age + ","
                + healthGoal.toString() + ","
                + idealCalories + ","
                + localDateTime.format(DATE_TIME_FORMATTER) + ","
                + isAbleToSeeSpecialChars;
    }

    public void printUIString() {
        UI.printString("Height: " + heightEntry + "cm");
        UI.printString("Weight: " + weightEntry + "kg");
        UI.printString("Gender: " + (isMale ? "male" : "female"));
        UI.printString("Age: " + age);
        UI.printString("Health Goal: " + healthGoal.toString());
        UI.printString("Ideal Daily Caloric Intake: " + idealCalories);
        UI.printString("Recorded at: " + localDateTime.format(DATE_TIME_FORMATTER));
        UI.printString("Is able to see special chars: " + isAbleToSeeSpecialChars);
    }

    public Goals getHealthGoal() {
        return Goals.valueOf(this.healthGoal.getCurrentHealthGoal());
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public double getWeight() {
        return this.weightEntry;
    }

    private static Double askForHeight(Scanner scanner) {
        UI.printString("Height in cm (e.g. 180):");
        try {
            double height = Double.parseDouble(scanner.nextLine());
            if (height <= 0){
                List<String> messages = List.of("Invalid height detected - entered height <= 0", "Retry:");
                UI.printMultiLineReply(messages);
                return askForHeight(scanner);
            } else if (height >= 270) {
                List<String> messages = List.of("Invalid height detected - entered height <= 270", "Retry:");
                UI.printMultiLineReply(messages);
                return askForHeight(scanner);
            } else {
                return height;
            }
        } catch (NumberFormatException n) {
            List<String> messages = List.of("Enter a valid height such that 0 < height < 270", "Retry:");
            UI.printMultiLineReply(messages);
            return askForHeight(scanner);
        }
    }

    private static Double askForWeight(Scanner scanner) {
        UI.printString("Weight in kg (e.g. 80):");
        try {
            double weight = Double.parseDouble(scanner.nextLine());
            if (weight <= 0){
                List<String> messages = List.of("Invalid weight detected. Entered weight <= 0", "Retry: ");
                UI.printMultiLineReply(messages);
                return askForWeight(scanner);
            } else if (weight >= 650) {
                List<String> messages = List.of("Invalid weight detected. Entered weight >= 650", "Retry: ");
                UI.printMultiLineReply(messages);
                return askForWeight(scanner);
            } else {
                return weight;
            }
        } catch (NumberFormatException n) {
            List<String> messages = List.of("Invalid weight detected. Enter a weight s.t. 0 < weight < 650", "Retry: ");
            UI.printMultiLineReply(messages);
            return askForWeight(scanner);
        }
    }

    private static boolean askForGender(Scanner scanner) {
        UI.printString("Gender (male or female):");
        String gender = scanner.nextLine();
        if (gender.equals("male")) {
            return true;
        } else if (gender.equals("female")) {
            return false;
        } else {
            List<String> messages = List.of("Gender does not fit the biological categories. " +
                            "Please select from: male, female", "Retry: ");
            UI.printMultiLineReply(messages);
            return askForGender(scanner);
        }
    }

    private static int askForAge(Scanner scanner) {
        UI.printString("Age (e.g. 20):");
        try {
            int age = Integer.parseInt(scanner.nextLine());
            if (age <= 0){
                List<String> messages = List.of("Invalid age detected. Age is <= 0.", "Retry: ");
                UI.printMultiLineReply(messages);
                return askForAge(scanner);
            } else if (age >= 600) {
                List<String> messages = List.of("Invalid age detected. Age is >= 600.", "Retry: ");
                UI.printMultiLineReply(messages);
                return askForAge(scanner);
            } else {
                return age;
            }
        } catch (NumberFormatException n) {
            List<String> messages = List.of("Invalid age detected. Has to be an integer between 0 and 600.", "Retry: ");
            UI.printMultiLineReply(messages);
            return askForAge(scanner);
        }
    }

    private static HealthGoal askForHealthGoal(Scanner scanner) {

        List<String> messages = List.of("Enter a health goal:",
                "Choose one of the following:",
                "1. WEIGHT_LOSS",
                "2. STEADY_STATE",
                "3. BULKING",
                "Enter the necessary number (1,2,3) to select");
        UI.printMultiLineReply(messages);
        try {
            int healthGoal = Integer.parseInt(scanner.nextLine().strip());
            boolean inputIsInvalid = healthGoal < 1 | healthGoal > 3;
            if (inputIsInvalid) {
                UI.printString("INVALID HEALTH GOAL: TRY AGAIN");
                return askForHealthGoal(scanner);
            } else {
                return new HealthGoal(healthGoal);
            }
        } catch (NumberFormatException n) {
            UI.printString("INVALID HEALTH GOAL: TRY AGAIN");
            return askForHealthGoal(scanner);
        }

    }

    private static boolean askForSpecialChars(Scanner scanner) {
        List<String> initMessages = List.of("Does progressbar below look well formatted as a questionmark? ",
                "Enter: {y} if it looks good. Enter: {n} if it contains weird characters such as '?'.");
        UI.printMultiLineReply(initMessages);
        UI.printString(UI.progressBarStringBuilder(100, 25, true));
        String input = scanner.nextLine().strip().toLowerCase();
        boolean inputIsInvalid = !input.equals("y") && !input.equals("n");
        if (inputIsInvalid) {
            List<String> messages = List.of("Invalid Input. Please enter 'y' or 'n'", "Retry");
            UI.printMultiLineReply(messages);
            return askForSpecialChars(scanner);
        } else {
            return input.equals("y") ? true : false;
        }
    }
}

