package seedu.healthmate;

//import java.util.ArrayList;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class User {
    public double idealCalories; //TODO: to be made private until loading of user data configured
    private HeightEntry heightEntry;
    private WeightEntry weightEntry;
    private boolean isMale;
    private int age;
    private HealthGoal healthGoal;

    public User(double height, double weight, boolean isMale, int age, String healthGoal) {
        this.heightEntry = new HeightEntry(height);
        this.weightEntry = new WeightEntry(weight);
        this.isMale = isMale;
        this.age = age;
        this.healthGoal = new HealthGoal(healthGoal);
        this.idealCalories = this.healthGoal.getTargetCalories(height, weight, isMale, age);
    }

    public void printConsumptionBar(String message, int currentCalories, LocalDate timestamp) {
        UI.printConsumptionBar(message, this.idealCalories, currentCalories, timestamp);
    }

    public String buildConsumptionBar(String message, int currentCalories, LocalDate timestamp) {
        return UI.buildConsumptionBar(message, this.idealCalories, currentCalories, timestamp);
    }

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
            UI.printReply("Great! You can now begin to use the app!1", "");
            return user;
        } catch (Exception exception) {
            UI.printReply("Wrong user input", "Retry: ");
            User user = askForUserData();
            return user;
        }
    }

    @Override
    public String toString() {
        return heightEntry.toString() + "\n" + weightEntry.toString() + "\n"
                + isMale + "\n" + age + "\n" + healthGoal.toString();
    }
}
