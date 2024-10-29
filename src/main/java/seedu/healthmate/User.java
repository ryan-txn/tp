package seedu.healthmate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

public class User {
    private final double idealCalories;
    private final double heightEntry;
    private final double weightEntry;
    private final boolean isMale;
    private final int age;
    private final HealthGoal healthGoal;
    private final LocalDateTime localDateTime;

    public User(double height, double weight, boolean isMale, int age, String healthGoal) {
        this.heightEntry = height;
        this.weightEntry = weight;
        this.isMale = isMale;
        this.age = age;
        this.healthGoal = new HealthGoal(healthGoal);
        this.idealCalories = this.healthGoal.getTargetCalories(height, weight, isMale, age);
        this.localDateTime = LocalDateTime.now();
    }

    /**
     * Loads a User instance if a file with user data exists.
     * Creates a new User instance otherwise
     * @param historyTracker
     * @return A newly created or "loaded" user object
     */
    public static User checkForUserData(HistoryTracker historyTracker) {
        Optional<User> optionalUser = historyTracker.loadUserData();
        User user = optionalUser.orElseGet(() -> User.askForUserData());
        historyTracker.saveUserDataFile(user);
        return user;
    }

    /**
     * Asks user to input specifics for creating a new User instance
     * @return A new user instance created with the data inputted by user.
     */
    public static User askForUserData() {

        try {
            Scanner scanner = new Scanner(System.in);

            UI.printString("Create your profile: please enter...");

            UI.printString("Height in cm (e.g. 180):");
            double height = Double.parseDouble(scanner.nextLine());

            UI.printString("Weight in kg (e.g. 80):");
            double weight = Double.parseDouble(scanner.nextLine());

            UI.printString("Gender (male or female):");
            String gender = scanner.nextLine();
            boolean isMale = (gender.equalsIgnoreCase("male"));

            UI.printString("Age (e.g. 20):");
            int age = Integer.parseInt(scanner.nextLine());

            UI.printString("Health Goal (WEIGHT_LOSS, STEADY_STATE, BULKING):");
            String healthGoal = scanner.nextLine();

            User user = new User(height, weight, isMale, age, healthGoal);
            UI.printString("Profile creation Successful!");
            UI.printReply("Great! You can now begin to use the app!", "");
            return user;
        } catch (Exception exception) {
            UI.printReply("Wrong user input", "Retry: ");
            return askForUserData();
        }
    }

    /**
     * Prints a user's consumption bar 
     * @param message Print message to the user
     * @param currentCalories Actual calorie consumption
     * @param timestamp The time for which ideal and actual consumption is compared
     */
    public void printUsersConsumptionBar(String message, int currentCalories, LocalDate timestamp) {
        UI.printConsumptionBar(message, this.idealCalories, currentCalories, timestamp);
    }

    /**
     * Simulates the construction of a user-specific consumption bar for testing
     * @param message Print message to the user
     * @param currentCalories Actual calorie consumption
     * @param timestamp The time for which ideal and actual consumption is compared
     * @return The simulated consumption bar
     */
    public String simulateUsersConsumptionBar(String message, int currentCalories, LocalDate timestamp) {
        return UI.buildConsumptionBar(message, this.idealCalories, currentCalories, timestamp);
    }

    /**
     * Prints the ideal caloric intake.
     */
    public void printTargetCalories() {
        UI.printReply(String.valueOf(this.idealCalories), "Ideal Caloric Intake: ");
    }

    /**
     * Prints the historic consumption bar for the current calories and timestamp.
     *
     * @param currentCalories the current caloric intake
     * @param timestamp       the timestamp of the consumption record
     * @throws IllegalArgumentException if currentCalories is negative or timestamp is null
     */
    public void printHistoricConsumptionBar(int currentCalories, LocalDate timestamp) {
        assert currentCalories >= 0 : "Current calories cannot be negative";
        assert timestamp != null : "Timestamp cannot be null";

        UI.printHistoricConsumptionBars(this.idealCalories, currentCalories, timestamp);
    }

    @Override
    public String toString() {
        return heightEntry + "\n" + weightEntry + "\n"
                + isMale + "\n" + age + "\n" + healthGoal.toString();
    }
}
