package seedu.Healthmate;

import java.util.Scanner;
import java.util.ArrayList;     
import java.time.LocalDateTime;
        
public class HealthMate {
    private static ArrayList<Meal> mealOptions = new ArrayList<>();
    private static ArrayList<MealEntry> mealEntries = new ArrayList<>();
    private static ArrayList<CalorieEntry> calorieEntries = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static double dailyCalorieGoal;

    public static void main(String[] args) {
        String logo = "HealthMate";
        System.out.println("Welcome to \n" + logo);
        
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            //TODO: Handle main logic here
        }
    }
}

class Meal {
    String name;
    int calories;

    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }
}

class MealEntry {
    Meal meal;
    LocalDateTime timestamp;

    public MealEntry(Meal meal) {
        this.meal = meal;
        this.timestamp = LocalDateTime.now();
    }
}

class CalorieEntry {
    int calories;
    LocalDateTime timestamp;

    public CalorieEntry(int calories) {
        this.calories = calories;
        this.timestamp = LocalDateTime.now();
    }
}

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
}

class WeightEntry {
    double weight;
    LocalDateTime timestamp;

    public WeightEntry(double weight) {
        this.weight = weight;
        this.timestamp = LocalDateTime.now();
    }
}

class HeightEntry {
    double height;
    LocalDateTime timestamp;

    public HeightEntry(double height) {
        this.height = height;
        this.timestamp = LocalDateTime.now();
    }
}
