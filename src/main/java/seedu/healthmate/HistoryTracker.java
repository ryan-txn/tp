package seedu.healthmate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryTracker {
    private static final String DATA_DIRECTORY = "data";
    private static final String MEAL_ENTRIES_FILE = "meal_entries.csv";
    private static final String MEAL_OPTIONS_FILE = "meal_options.csv";

    public HistoryTracker() {
        createDataDirectoryIfNotExists();
    }

    private void createDataDirectoryIfNotExists() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void saveMealEntries(MealEntriesList mealEntries) {
        saveToFile(mealEntries.getMealEntries(), MEAL_ENTRIES_FILE);
    }

    public void saveMealOptions(MealList mealOptions) {
        saveToFile(mealOptions.getMealList(), MEAL_OPTIONS_FILE);
    }

    public MealEntriesList loadMealEntries() {
        List<Meal> meals = loadFromFile(MEAL_ENTRIES_FILE, true);
        MealEntriesList mealEntriesList = new MealEntriesList();
        for (Meal meal : meals) {
            mealEntriesList.addMealWithoutCLIMessage(meal);
        }
        UI.printString("Meal Entries Loaded Successfully!");
        return mealEntriesList;
    }

    public MealList loadMealOptions() {
        List<Meal> meals = loadFromFile(MEAL_OPTIONS_FILE, false);
        MealList mealList = new MealList();
        for (Meal meal : meals) {
            mealList.addMealWithoutCLIMessage(meal);
        }
        UI.printString("Meal Options Loaded Successfully!");
        return mealList;
    }

    public MealEntriesList loadEmptyMealEntries() {
        return new MealEntriesList();
    }

    public MealList loadEmptyMealOptions() {
        return new MealList();
    }

    private void saveToFile(List<Meal> meals, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIRECTORY + File.separator + fileName))) {
            for (Meal meal : meals) {
                writer.write(meal.toSaveString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Meal> loadFromFile(String fileName, boolean isEntry) {
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
}
