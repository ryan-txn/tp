package seedu.healthmate.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.io.RandomAccessFile;

import seedu.healthmate.core.User;
import seedu.healthmate.core.UserEntryList;

/**
 * Manages the saving and loading of UserEntry lists, which store user information over time.
 * Handles adding new user entries, and retrieving stored data for display or further processing.
 */
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

    //@@author ryan-txn
    /**
     * Saves the provided User entry to a file. If the file does not exist,
     * it will be created first, and then the user data will be appended to the save file.
     *
     * @param userEntry The User object containing data to be saved.
     */
    public void saveUserToFile(User userEntry) {
        createFileIfNotExists();
        addUserEntry(userEntry);
    }

    /**
     * Prints all user entries from the data file to the console.
     * Displays an error message if the file is not found.
     */
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

    /**
     * Converts a CSV line from the data file into a User object.
     *
     * @param line CSV-formatted string representing a user entry.
     * @return User object with data from the parsed line.
     */
    private static User getUserEntryFromFileLine(String line) {
        String[] fields = line.split(",");  // Split the CSV line by commas

        double height = Double.parseDouble(fields[0]);
        double weight = Double.parseDouble(fields[1]);
        boolean isMale = Boolean.parseBoolean(fields[2]);
        int age = Integer.parseInt(fields[3]);
        String healthGoal = fields[4];
        double idealCalories = Double.parseDouble(fields[5]);
        String localDateTime = fields[6].trim();

        User user = new User(height, weight, isMale, age, healthGoal, idealCalories, localDateTime);
        return user;
    }

    /**
     * Appends the given User entry to the data file. Creates a new line with
     * the user's information in CSV format. If an error occurs, an error message is displayed.
     *
     * @param userEntry The User object to add to the data file.
     */
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

    /**
     * Creates a directory if it does not already exist.
     * Verifies that the directory exists after attempting to create it.
     *
     * @param folderName Name of the directory to create.
     */
    private static void createDirectoryIfNotExists(String folderName) {
        File directory = new File(folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        assert directory.exists() : "Data directory should exist after creation";
    }

    /**
     * Creates the user data file if it does not already exist.
     * If the file is created successfully, a confirmation message is displayed.
     */
    private void createFileIfNotExists() {
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

    public void clearSaveFile() {
        try {
            FileWriter fw = new FileWriter(DATA_DIRECTORY + File.separator + USER_DATA_FILE, false);
            fw.write("");  // Overwrite with an empty string
            fw.close();
        } catch (IOException e) {
            System.out.println("Error clearing save file: " + e.getMessage());
        }
    }

    public Optional<User> getLastUser() {
        File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);
        try (RandomAccessFile raf = new RandomAccessFile(userDataFile, "r")) {
            long fileLength = userDataFile.length() - 1;
            StringBuilder lastLine = new StringBuilder();

            // Move pointer to end of file
            raf.seek(fileLength);

            for (long pointer = fileLength; pointer >= 0; pointer--) {
                raf.seek(pointer);
                char c = (char) raf.read();
                if (c == '\n' && lastLine.length() > 0) {
                    break;
                }

                lastLine.insert(0, c);
            }

            return Optional.of(getUserEntryFromFileLine(lastLine.toString()));
        } catch (IOException e) {
            System.out.println("Error retrieving last user data" + e);
            return Optional.empty();
        }
    }
    //@@author
}
