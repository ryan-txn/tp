package seedu.healthmate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class UserHistoryTracker {
    private static final String DATA_DIRECTORY = "data";
    private static final String USER_DATA_FILE = "user_data.csv";

    public UserHistoryTracker() {
        createDirectoryIfNotExists(DATA_DIRECTORY);
    }

    public UserEntry checkForUserData(UserHistoryTracker userHistoryTracker) {
        Optional<UserEntryList> optionalUserEntryList = userHistoryTracker.loadUserData();
        return optionalUserEntryList.map(userEntryList -> userEntryList.getLastEntry())
                .orElseGet(() -> UserEntry.askForUserData());
    }

    public void clearSaveFile() {
        try {
            FileWriter fw = new FileWriter(DATA_DIRECTORY + File.separator + USER_DATA_FILE, false);
            fw.write("");  // Overwrite with an empty string
            fw.close();
            System.out.println("Save file cleared successfully.");
        } catch (IOException e) {
            System.out.println("Error clearing save file: " + e.getMessage());
        }
    }


    public Optional<UserEntryList> loadUserData() {
        createFileIfNotExists();
        File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);

        UserEntryList userEntryList = new UserEntryList();

        try (Scanner s = new Scanner(userDataFile)){
            while (s.hasNextLine()) {
                String line = s.nextLine();
                UserEntry user = getUserEntryFromFileLine(line);
                userEntryList.addUserEntry(user);
            }
        } catch (IOException e) {
            System.out.println("Error creating user data file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing a number." + e.getMessage());
        } catch (NoSuchElementException e) {
            // silent catch if existing user file contains no content
        }
        return userEntryList.getUserEntryList().isEmpty() ? Optional.empty() : Optional.of(userEntryList);
    }

    public void printAllUserEntries() {
        File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);

        try (Scanner scanner = new Scanner(userDataFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");  // Split line into components by commas
                System.out.println(String.join(", ", fields));  // Join components with a comma and space
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: User data file not found. " + e.getMessage());
        }
    }


    private static UserEntry getUserEntryFromFileLine(String line) {
        String[] fields = line.split(",");  // Split the CSV line by commas

        // Parse each field from the CSV line
        double height = Double.parseDouble(fields[0]);
        double weight = Double.parseDouble(fields[1]);
        boolean isMale = Boolean.parseBoolean(fields[2]);
        int age = Integer.parseInt(fields[3]);
        String healthGoal = fields[4];
        double idealCalories = Double.parseDouble(fields[5]);
        String localDateTime = fields[6];

        UserEntry user = new UserEntry(height, weight, isMale, age, healthGoal, idealCalories, localDateTime);
        return user;
    }

    public static void createDirectoryIfNotExists(String folderName) {
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        assert directory.exists() : "Data directory should exist after creation";
    }

    public void saveUserEntryListToFile(UserEntryList userEntryList) {
        ArrayList<UserEntry> userEntries = userEntryList.getUserEntryList();
        for (UserEntry userEntry : userEntries) {
            saveUserToFile(userEntry);
        }
    }

    public void saveUserToFile(UserEntry userEntry) {
        createFileIfNotExists();
        addUserEntry(userEntry);
    }

    public void addUserEntry(UserEntry userEntry) {
        File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);

        try {
            System.out.println("Saving entry to file at: " + userDataFile.getAbsolutePath());  // Print the absolute file path
            FileWriter fw = new FileWriter(userDataFile, true);
            fw.write(userEntry.toString() + System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            System.out.println("Error adding userEntry to data file: " + e.getMessage());
        }
    }

    public void createFileIfNotExists() {
        try {
            File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);
            if (!userDataFile.exists()) {
                userDataFile.createNewFile();
                System.out.println("Creating new save file");
            }
        } catch (IOException e) {
            System.out.println("Error creating user data file: " + e.getMessage());
        }
    }
}
