package seedu.healthmate;

//import java.util.ArrayList;

import java.time.LocalDate;

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


    /*public void addWeightEntry(double weight) {
        weightEntries.add(new WeightEntry(weight));
    }

    public void addHeightEntry(double height) {
        heightEntries.add(new HeightEntry(height));
    }*/

    //TODO: to be used as soon as loading of User data object properly implemented
    public void printConsumptionBar(int currentCalories, LocalDate timestamp) {
        UI.printConsumptionBar("", this.idealCalories, currentCalories, timestamp);
    }

    @Override
    public String toString() {
        return heightEntry.toString() + "\n" + weightEntry.toString() + "\n"
                + isMale + "\n" + age + "\n" + healthGoal.toString();
    }
}
