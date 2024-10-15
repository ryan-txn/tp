package seedu.healthmate;

import java.util.ArrayList;

class User {
    private ArrayList<WeightEntry> weightEntries;
    private ArrayList<HeightEntry> heightEntries;


    public User() {
        this.weightEntries = new ArrayList<>();
        this.heightEntries = new ArrayList<>();
    }

    public void addWeightEntry(double weight) {
        weightEntries.add(new WeightEntry(weight));
    }

    public void addHeightEntry(double height) {
        heightEntries.add(new HeightEntry(height));
    }

    @Override
    public String toString() {
        return "User with" + this.weightEntries.get(this.weightEntries.size())
            + " and " + this.heightEntries.get(this.heightEntries.size());
    }
}
