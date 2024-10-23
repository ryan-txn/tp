package seedu.healthmate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HistoryTracker {
    private static final String DATA_DIRECTORY = "data";
    private static final String MEAL_ENTRIES_FILE = "meal_entries.csv";
    private static final String MEAL_OPTIONS_FILE = "meal_options.csv";
    private static final String USER_DATA_FILE = "user_data.txt";


    public HistoryTracker() {
        createDirectoryIfNotExists(DATA_DIRECTORY);
    }

    public static void createDirectoryIfNotExists(String folderName) {
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        assert directory.exists() : "Data directory should exist after creation";
    }

    public void saveMealEntries(MealEntriesList mealEntries) {
        saveMealToFile(mealEntries.getMealEntries(), MEAL_ENTRIES_FILE);
    }

    public void saveMealOptions(MealList mealOptions) {
        saveMealToFile(mealOptions.getMealList(), MEAL_OPTIONS_FILE);
    }

    public void saveUserDataFile(User user) {
        try {

            File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);

            if (!userDataFile.exists()) {
                userDataFile.createNewFile();
                return;
            }

            clearUserDataFile();
            updateUserDataFile(user);
        } catch (IOException e) {
            System.out.println("Error creating user data file: " + e.getMessage());
        }
    }

    public MealEntriesList loadMealEntries() {
        List<Meal> meals = loadMealFromFile(MEAL_ENTRIES_FILE, true);
        MealEntriesList mealEntriesList = new MealEntriesList();
        for (Meal meal : meals) {
            mealEntriesList.addMealWithoutCLIMessage(meal);
        }
        UI.printString("Meal Entries Loaded Successfully!");
        return mealEntriesList;
    }

    public MealList loadMealOptions() {
        List<Meal> meals = loadMealFromFile(MEAL_OPTIONS_FILE, false);
        MealList mealList = new MealList();
        for (Meal meal : meals) {
            mealList.addMealWithoutCLIMessage(meal);
        }
        UI.printString("Meal Options Loaded Successfully!");
        return mealList;
    }

    public Optional<User> loadUserData() {
        try {
            File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);
            if (!userDataFile.exists()) {
                userDataFile.createNewFile();
            }

            Optional<User> user = loadUserFromFile(userDataFile);
            return user;
        } catch (IOException e) {
            System.out.println("Error creating user data file: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> loadUserFromFile(File userDataFile) throws FileNotFoundException{
        try {
            Scanner s = new Scanner(userDataFile);
            double userHeight = Double.parseDouble(s.nextLine());
            double userWeight = Double.parseDouble(s.nextLine());
            boolean isMale = Boolean.parseBoolean(s.nextLine());
            int age = Integer.parseInt(s.nextLine());
            String goal = s.nextLine();

            return Optional.of(new User(userHeight, userWeight, isMale, age, goal));

        } catch (NumberFormatException e) {
            System.out.println("Error parsing a number." + e.getMessage());
        } catch (NoSuchElementException e) {
            // silent catch if existing user file contains no content
        }
        return Optional.empty();
    }

    public void clearUserDataFile(){
        try {
            FileWriter fw = new FileWriter(DATA_DIRECTORY + File.separator + USER_DATA_FILE);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error clearing user data file: " + e.getMessage());
        }
    }

    public void updateUserDataFile(User user) {
        try {
            FileWriter fw = new FileWriter(DATA_DIRECTORY + File.separator + USER_DATA_FILE, true);
            fw.write(user.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Error updating user data file: " + e.getMessage());
        }
    }

    public MealEntriesList loadEmptyMealEntries() {
        return new MealEntriesList();
    }

    public MealList loadEmptyMealOptions() {
        return new MealList();
    }

    private void saveMealToFile(List<Meal> meals, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIRECTORY + File.separator + fileName))) {
            for (Meal meal : meals) {
                writer.write(meal.toSaveString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Meal> loadMealFromFile(String fileName, boolean isEntry) {
        List<Meal> meals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(DATA_DIRECTORY + File.separator + fileName))
            ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                meals = parseAndAddMeal(meals, parts, isEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return meals;
    }

    private List<Meal> parseAndAddMeal(List<Meal> meals, String[] parts, boolean isEntry) {
        boolean isCorrectMealEntry = isEntry && (parts.length == 3);
        boolean isCorrectMeal = !isEntry && (parts.length == 2);
        if (isCorrectMealEntry) {
            String name = parts[0].isEmpty() ? null : parts[0];
            int calories = Integer.parseInt(parts[1]);
            LocalDateTime timestamp = LocalDateTime.parse(parts[2].strip());
            meals.add(new MealEntry(Optional.ofNullable(name), calories, timestamp));
        } else if (isCorrectMeal) {
            String name = parts[0].isEmpty() ? null : parts[0];
            int calories = Integer.parseInt(parts[1]);
            meals.add(new Meal(Optional.ofNullable(name), calories));
        }
        return meals;
    }

    public void saveMealOptions(List<Meal> mealList) {
        //only saves meals which are new/details change
        List<Meal> existingMeals = loadMealFromFile(MEAL_OPTIONS_FILE, false);
        List<Meal> newMeals = new ArrayList<>();
        
        for (Meal meal : mealList) {
            if (!existingMeals.contains(meal)) {
                newMeals.add(meal);
            }
        }
        
        saveMealToFile(newMeals, MEAL_OPTIONS_FILE);
    }
}
