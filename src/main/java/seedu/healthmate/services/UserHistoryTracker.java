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

/**
 * Manages the saving and loading of UserEntry lists, which store user information over time.
 * Handles adding new user entries, and retrieving stored data for display or further processing.
 */
public class UserHistoryTracker extends HistoryTracker {

    private static final String USER_DATA_FILE = "user_data.csv";

    public UserHistoryTracker() {
        super();
    }

    //@@author kennethSty
    /**
     * Loads a User instance if a file with user data exists.
     * Creates a new User instance otherwise
     * @return A newly created or "loaded" user object
     */
    public User checkForUserData() {
        return getLatestUser().orElseGet(() -> User.askForUserData());
    }

    public Optional<User> getLatestUser() {
        Optional<UserEntryList> optionalUserEntryList = this.loadUserEntries();
        return optionalUserEntryList.map(userEntryList -> userEntryList.getLastEntry());
    }

    public Optional<UserEntryList> loadUserEntries() {

        UserEntryList userEntryList = new UserEntryList();

        try {
            File userDataFile = createFileIfNotExists();
            Scanner s = new Scanner(userDataFile);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                User user = getUserEntryFromFileLine(line);
                userEntryList.addUserEntry(user);
            }

        } catch (IOException e) {
            UI.printString("Error loading user data file. A new profile needs to be created");
        } catch (ArrayIndexOutOfBoundsException e) {
            UI.printString("It seems your user data file is incomplete. A new profile needs to be created");
        } catch (NumberFormatException e) {
            UI.printString("Error parsing a number.");
        } catch (NoSuchElementException e) {
            // silent catch if existing user file contains no content
        }
        return userEntryList.isEmpty() ? Optional.empty() : Optional.of(userEntryList);
    }

    /**
     * Saves the provided User entry to a file. If the file does not exist,
     * it will be created first, and then the user data will be appended to the save file.
     *
     * @param userEntry The User object containing data to be saved.
     */
    public void saveUserToFile(User userEntry) {
        try {
            createFileIfNotExists();
            addUserEntry(userEntry);
        } catch (IOException e) {
            UI.printReply("Saving to the user file was unsuccessful", "Error: ");
        }
    }
    //@@author

    //@@author ryan-txn
    /**
     * Prints all user entries from the data file to the console.
     * Displays an error message if the file is not found.
     */
    public void printAllUserEntries() {
        File userDataFile = new File(super.DATA_DIRECTORY + File.separator + USER_DATA_FILE);

        try (Scanner scanner = new Scanner(userDataFile)) {
            System.out.println("Last few records...");
            Optional<UserEntryList> userListOpt = loadUserEntries();

            if (userListOpt.isEmpty()) {
                System.out.println("No user entries found.");
                return;
            }

            UserEntryList userList = userListOpt.get();
            int start = userList.getUserEntryList().size() - 1; // Calculate starting index for last 5 entries
            int end = Math.max(userList.getUserEntryList().size() - 6, 0);

            for (int i = start; i > end; i--) {
                User user = userList.getUserEntryList().get(i);
                System.out.println();
                user.printUIString();
                System.out.println();
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
        boolean isAbleToSeeSpecialChars = Boolean.parseBoolean(fields[7]);

        return new User(height, weight, isMale, age, healthGoal, idealCalories, localDateTime, isAbleToSeeSpecialChars);
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

    //@@author kennethSty
    /**
     * Creates the user data file if it does not already exist.
     * If the file is created successfully, a confirmation message is displayed.
     */
    private File createFileIfNotExists() throws IOException{
        File userDataFile = new File(DATA_DIRECTORY + File.separator + USER_DATA_FILE);
        if (!userDataFile.exists()) {
            userDataFile.createNewFile();
        }
        return userDataFile;
    }
    //@@author

    //@@author ryan-txn
    /**
     * Clears the save file by overwriting it with an empty string.
     * If an error occurs during file access, an error message is printed to the console.
     */
    public void clearSaveFile() {
        try {
            FileWriter fw = new FileWriter(DATA_DIRECTORY + File.separator + USER_DATA_FILE, false);
            fw.write("");  // Overwrite with an empty string
            fw.close();
        } catch (IOException e) {
            System.out.println("Error clearing save file: " + e.getMessage());
        }
    }
    //@@ author
}
