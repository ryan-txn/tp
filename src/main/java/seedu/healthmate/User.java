package seedu.healthmate;

import java.util.ArrayList;

public class User {
    private static ArrayList<WeightEntry> weightEntries;
    private static ArrayList<HeightEntry> heightEntries;

    private static HealthGoal healthGoal;
    private static int idealCalories;

    public User() {
        weightEntries = new ArrayList<>();
        heightEntries = new ArrayList<>();
    }

    public static void addWeightEntry(double weight) {
        weightEntries.add(new WeightEntry(weight));
    }

    public static void addHeightEntry(double height) {
        heightEntries.add(new HeightEntry(height));
    }

    @Override
    public String toString() {
        return "User with" + weightEntries.get(weightEntries.size() - 1)
            + " and " + heightEntries.get(heightEntries.size() - 1);
    }
}
