package seedu.healthmate.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import seedu.healthmate.core.User;
import seedu.healthmate.core.UserEntryList;

public class UserHistoryTracker {
    private static final String DATA_DIRECTORY = "data";
    private static final String USER_DATA_FILE = "user_data.csv";

    public UserHistoryTracker() {
        createDirectoryIfNotExists(DATA_DIRECTORY);
    }

    //@@author kennethSty
    /**
     * Loads a User instance if a file with user data exists.
     * Creates a new User instance otherwise
     * @param userHistoryTracker
     * @return A newly created or "loaded" user object
     */
    public User checkForUserData(UserHistoryTracker userHistoryTracker) {
        Optional<UserEntryList> optionalUserEntryList = userHistoryTracker.loadUserData();
        return optionalUserEntryList.map(userEntryList -> userEntryList.getLastEntry())
                .orElseGet(() -> User.askForUserData());
    }

    public Optional<UserEntryList> loadUserData() {
        createFileIfNotExists();
        File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);

        UserEntryList userEntryList = new UserEntryList();

        try (Scanner s = new Scanner(userDataFile)){
            while (s.hasNextLine()) {
                String line = s.nextLine();
                User user = getUserEntryFromFileLine(line);
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
    //@@author

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


    private static User getUserEntryFromFileLine(String line) {
        String[] fields = line.split(",");  // Split the CSV line by commas

        // Parse each field from the CSV line
        double height = Double.parseDouble(fields[0]);
        double weight = Double.parseDouble(fields[1]);
        boolean isMale = Boolean.parseBoolean(fields[2]);
        int age = Integer.parseInt(fields[3]);
        String healthGoal = fields[4];
        double idealCalories = Double.parseDouble(fields[5]);
        String localDateTime = fields[6];

        User user = new User(height, weight, isMale, age, healthGoal, idealCalories, localDateTime);
        return user;
    }

    private static void createDirectoryIfNotExists(String folderName) {
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        assert directory.exists() : "Data directory should exist after creation";
    }

    public void saveUserToFile(User userEntry) {
        createFileIfNotExists();
        addUserEntry(userEntry);
    }

    public void addUserEntry(User userEntry) {
        File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);

        try {
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
