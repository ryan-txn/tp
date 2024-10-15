package seedu.healthmate;

import java.io.*;
import java.time.LocalDateTime;
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
        createDataDirectoryIfNotExists();
    }

    private void createDataDirectoryIfNotExists() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void saveMealEntries(MealEntriesList mealEntries) {
        saveMealToFile(mealEntries.getMealEntries(), MEAL_ENTRIES_FILE);
    }

    public void saveMealOptions(MealList mealOptions) {
        saveMealToFile(mealOptions.getMealList(), MEAL_OPTIONS_FILE);
    }

    public void loadUserData() {
        try {
            File userDataFile = new File(DATA_DIRECTORY, USER_DATA_FILE);

            if (!userDataFile.exists()) {
                userDataFile.createNewFile();  // Create the file if it doesn't exist
                return;  // Exit early since there's no data to load
            }

            printUserDataFile();
            updateUser();
        } catch (IOException e) {
            System.out.println("Error creating user data file: " + e.getMessage());
        }
    }

    public void printUserDataFile() {
        try {
            // assign file to object
            File userDataFile = new File(DATA_DIRECTORY, USER_DATA_FILE);
            // use scanner to print file contents
            Scanner s = new Scanner(userDataFile);
            while (s.hasNext()) {
                UI.printString(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            UI.printString("Error printing user data file: " + e.getMessage());
        }
    }

    public void updateUser() {
        return;
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
}
