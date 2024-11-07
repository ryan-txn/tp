package seedu.healthmate.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import seedu.healthmate.recommender.Goals;
import seedu.healthmate.services.UI;
import seedu.healthmate.services.UserHistoryTracker;

/**
 * Represents a user record captured at a specific date and time.
 */
public class User {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final double idealCalories;
    private final double heightEntry;
    private final double weightEntry;
    private final boolean isMale;
    private final int age;
    private final HealthGoal healthGoal;
    private final LocalDateTime localDateTime;

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
                int age, String healthGoal) {
        this.heightEntry = height;
        this.weightEntry = weight;
        this.isMale = isMale;
        this.age = age;
        this.healthGoal = new HealthGoal(healthGoal);
        this.idealCalories = this.healthGoal.getTargetCalories(height, weight, isMale, age);
        this.localDateTime = LocalDateTime.now();
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
                String healthGoal, double idealCalories, String localDateTime) {
        this.heightEntry = height;
        this.weightEntry = weight;
        this.isMale = isMale;
        this.age = age;
        this.healthGoal = new HealthGoal(healthGoal);
        this.idealCalories = idealCalories;
        this.localDateTime = LocalDateTime.parse(localDateTime, formatter);
    }

    /**
     * Asks user to input specifics for creating a new User instance
     * @return A new user instance created with the data inputted by user.
     */
    public static User  askForUserData() {
        try {
            Scanner scanner = new Scanner(System.in);

            UI.printString("Create your profile: please enter...");

            UI.printString("Height in cm (e.g. 180):");
            double height = Double.parseDouble(scanner.nextLine());
            if (height <= 0){
                throw new NumberFormatException("INVALID HEIGHT");
            }
            UI.printString("Weight in kg (e.g. 80):");
            double weight = Double.parseDouble(scanner.nextLine());
            if (weight <= 0){
                throw new NumberFormatException("INVALID WEIGHT");
            }
            UI.printString("Gender (male or female):");
            String gender = scanner.nextLine();
            boolean isMale = setGender(gender);

            UI.printString("Age (e.g. 20):");
            int age = Integer.parseInt(scanner.nextLine());
            if (age <= 0){
                throw new NumberFormatException();
            }
            UI.printString("Health Goal (WEIGHT_LOSS, STEADY_STATE, BULKING):");
            String healthGoal = scanner.nextLine();
            validateHealthGoal(healthGoal);

            User user = new User(height, weight, isMale, age, healthGoal);
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

    /**
     * Creates a specific user profile for isolated testing.
     * @return User profile
     */
    public static User createUserStub() {
        return new User(180, 80.0, true, 20, "BULKING");
    }

    public static User createAlternativeUserStub() {
        return new User(200, 200, false, 82, "STEADY_STATE");
    }


    @Override
    public String toString() {
        return heightEntry + ","
                + weightEntry + ","
                + isMale + ","
                + age + ","
                + healthGoal.toString() + ","
                + idealCalories + ","
                + localDateTime.format(formatter);
    }

    public void printUIString() {
        UI.printString("Height: " + heightEntry + "cm");
        UI.printString("Weight: " + weightEntry + "kg");
        UI.printString("Gender: " + (isMale ? "male" : "female"));
        UI.printString("Age: " + age);
        UI.printString("Health Goal: " + healthGoal.toString());
        UI.printString("Ideal Daily Caloric Intake: " + idealCalories);
        UI.printString("Recorded at: " + localDateTime.format(formatter));
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

    private static boolean setGender(String gender) {
        if (gender.equals("male")) {
            return true;
        } else if (gender.equals("female")) {
            return false;
        } else {
            throw new IllegalArgumentException("INVALID GENDER");
        }
    }

    private static void validateHealthGoal(String healthGoal) {
        // Check if the input matches any valid formats
        if (!healthGoal.equals("WEIGHT_LOSS") && !healthGoal.equals("STEADY_STATE") && !healthGoal.equals("BULKING")) {
            // Throw an exception if invalid
            throw new IllegalArgumentException("INVALID HEALTH GOAL");
        }
    }



}

