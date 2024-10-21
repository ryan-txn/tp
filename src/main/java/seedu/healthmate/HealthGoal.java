package seedu.healthmate;

import java.util.Objects;

public class HealthGoal {

    private static final String WEIGHT_LOSS = "WEIGHT_LOSS";
    private static final String STEADY_STATE = "STEADY_STATE";
    private static final String BULKING = "BULKING";

    private static final double weightLossModifier = 0.7;
    private static final double steadyStateModifier = 1.1;
    private static final double bulkingModifier = 1.5;

    private String currentHealthGoal;

    public HealthGoal(String healthGoalInput) {
        saveHealthGoal(healthGoalInput);
    }

    public void saveHealthGoal(String healthGoalInput) {
        // Assert that the input is not null
        assert healthGoalInput != null : "Health goal input cannot be null";

        switch (healthGoalInput) {
            case WEIGHT_LOSS:
                currentHealthGoal = WEIGHT_LOSS;
                return;
            case STEADY_STATE:
                currentHealthGoal = STEADY_STATE;
                return;
            case BULKING:
                currentHealthGoal = BULKING;
                return;
            default:
                // If healthGoalInput is invalid
                UI.printReply("Invalid Health Goal", "Save Health Goal Error: ");
        }
    }

    public String getCurrentHealthGoal() {
        return currentHealthGoal;
    }

    public double getTargetCalories(double height, double weight, boolean isMale, int age) {
        // Assert that height, weight, and age are positive numbers
        assert height > 0 : "Height must be positive";
        assert weight > 0 : "Weight must be positive";
        assert age > 0 : "Age must be positive";

        double rawCaloriesTarget;
        if (isMale) {
            // Formula for males
            rawCaloriesTarget = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            // Formula for females
            rawCaloriesTarget = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        // Assert that the current health goal is set before proceeding
        assert currentHealthGoal != null : "Current health goal must be set";

        switch (currentHealthGoal) {
            case WEIGHT_LOSS:
                return rawCaloriesTarget * weightLossModifier;

            case STEADY_STATE:
                return rawCaloriesTarget * steadyStateModifier;

            case BULKING:
                return rawCaloriesTarget * bulkingModifier;
        }
        return -1;
    }

    @Override
    public String toString() {
        // Assert that the current health goal is not null
        assert currentHealthGoal != null : "Current health goal cannot be null when converting to string";
        return currentHealthGoal;
    }
}
