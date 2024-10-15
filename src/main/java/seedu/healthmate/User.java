package seedu.healthmate;

import java.util.ArrayList;

public class User {
    private WeightEntry weightEntry;
    private HeightEntry heightEntry;
    private boolean isMale;
    private int age;
    private HealthGoal healthGoals;
    private static int idealCalories;

    public User(WeightEntry weightEntry, HeightEntry heightEntry, HealthGoal healthGoal) {
        this.weightEntry = weightEntry;
        this.heightEntry = heightEntry;


    }

    /*public void addWeightEntry(double weight) {
        weightEntries.add(new WeightEntry(weight));
    }

    public void addHeightEntry(double height) {
        heightEntries.add(new HeightEntry(height));
    }

    @Override
    public String toString() {
        return "User with" + weightEntries.get(weightEntries.size() - 1)
            + " and " + heightEntries.get(heightEntries.size() - 1);
    }*/
}
