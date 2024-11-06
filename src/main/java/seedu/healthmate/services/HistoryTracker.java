package seedu.healthmate.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.healthmate.core.Meal;
import seedu.healthmate.core.MealEntriesList;
import seedu.healthmate.core.MealEntry;
import seedu.healthmate.core.MealList;

/**
 * Handles saving and loading of meal entries and meal options to/from persistent storage.
 * Uses CSV files to store the data in a data directory.
 */
public class HistoryTracker {
    protected static final String DATA_DIRECTORY = "data";
    private static final String MEAL_ENTRIES_FILE = "meal_entries.csv";
    private static final String MEAL_OPTIONS_FILE = "meal_options.csv";

    /**
     * Creates a new HistoryTracker and ensures the data directory exists.
     */
    public HistoryTracker() {
        createDirectoryIfNotExists(DATA_DIRECTORY);
    }

    /**
     * Creates a directory if it does not already exist.
     * @param folderName The name of the directory to create
     */
    public static void createDirectoryIfNotExists(String folderName) {
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        assert directory.exists() : "Data directory should exist after creation";
    }

    /**
     * Saves the list of meal entries to a CSV file.
     * @param mealEntries The list of meal entries to save
     */
    public void saveMealEntries(MealEntriesList mealEntries) {
        saveMealToFile(mealEntries.getMealEntries(), MEAL_ENTRIES_FILE);
    }

    /**
     * Saves new or modified meal options to a CSV file.
     * Only saves meals that don't already exist in the file.
     * @param mealOptions The list of meal options to save
     */
    public void saveMealOptions(MealList mealOptions) {
        //only saves meals which are new/details change
        List<Meal> mealList = mealOptions.getMealList();
        List<Meal> existingMeals = loadMealFromFile(MEAL_OPTIONS_FILE, false);
        List<Meal> newMeals = new ArrayList<>();
        
        for (Meal meal : mealList) {
            if (!existingMeals.contains(meal)) {
                newMeals.add(meal);
            }
        }
        
        saveMealToFile(newMeals, MEAL_OPTIONS_FILE);
    }

    /**
     * Loads meal entries from the CSV file.
     * @return A MealEntriesList containing all saved meal entries
     */
    public MealEntriesList loadMealEntries() {
        List<Meal> meals = loadMealFromFile(MEAL_ENTRIES_FILE, true);
        MealEntriesList mealEntriesList = new MealEntriesList();
        for (Meal meal : meals) {
            mealEntriesList.addMealWithoutCLIMessage(meal);
        }
        UI.printString("Meal Entries Loaded Successfully!");
        return mealEntriesList;
    }

    /**
     * Loads meal options from the CSV file.
     * @return A MealList containing all saved meal options
     */
    public MealList loadMealOptions() {
        List<Meal> meals = loadMealFromFile(MEAL_OPTIONS_FILE, false);
        MealList mealList = new MealList();
        for (Meal meal : meals) {
            mealList.addMealWithoutCLIMessage(meal);
        }
        UI.printString("Meal Options Loaded Successfully!");
        return mealList;
    }

    /**
     * Creates and returns an empty MealEntriesList.
     * @return An empty MealEntriesList
     */
    public MealEntriesList loadEmptyMealEntries() {
        return new MealEntriesList();
    }

    /**
     * Creates and returns an empty MealList.
     * @return An empty MealList
     */
    public MealList loadEmptyMealOptions() {
        return new MealList();
    }

    /**
     * Saves a list of meals to a specified CSV file.
     * @param meals The list of meals to save
     * @param fileName The name of the file to save to
     */
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

    /**
     * Loads meals from a specified CSV file.
     * @param fileName The name of the file to load from
     * @param isEntry Whether the meals being loaded are meal entries (true) or meal options (false)
     * @return A list of meals loaded from the file
     */
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

    /**
     * Parses meal data from CSV format and adds it to the meals list.
     * @param meals The list to add the parsed meal to
     * @param parts The array of strings containing the meal data
     * @param isEntry Whether the meal being parsed is a meal entry (true) or meal option (false)
     * @return The updated list of meals
     */
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
