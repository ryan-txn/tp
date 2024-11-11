package seedu.healthmate.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.healthmate.core.User;
import seedu.healthmate.core.UserEntryList;

/**
 * Manages the saving and loading of UserEntry lists, which store user information over time.
 * Handles adding new user entries, and retrieving stored data for display or further processing.
 */
public class UserHistoryTracker extends HistoryTracker {

    public static final int USER_ENTRY_PRINTING_COUNT = 5;
    public static final int USER_DATA_SAVE_FILE_FIELDS = 8;
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
            List<String> messages = List.of("Sorry: There was an error loading your user profile.",
                    "A new profile needs to be created.");
            UI.printMultiLineReply(messages);
        } catch (ArrayIndexOutOfBoundsException e) {
            clearSaveFile();
            List<String> messages = List.of("It seems your user profile is incomplete.",
                    "A new profile needs to be created.");
            UI.printMultiLineReply(messages);
        } catch (NumberFormatException e) {
            clearSaveFile();
            List<String> messages = List.of("It seems some numbers in you user profile are corrupted.",
                    "A new profile needs to be created.");
            UI.printMultiLineReply(messages);
        } catch (IllegalArgumentException e) {
            clearSaveFile();
            List<String> messages = List.of("It seems some booleans / health goals in you user profile are corrupted.",
                    "A new profile needs to be created.");
            UI.printMultiLineReply(messages);
        } catch (DateTimeParseException e) {
            clearSaveFile();
            List<String> messages = List.of("It seems some datetime records in you user profile are corrupted.",
                    "A new profile needs to be created.");
            UI.printMultiLineReply(messages);
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
            int end = Math.max(start - (USER_ENTRY_PRINTING_COUNT - 1), 0);

            for (int i = start; i >= end; i--) {
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
    private static User getUserEntryFromFileLine(String line) throws ArrayIndexOutOfBoundsException,
            IllegalArgumentException, DateTimeParseException {

        String[] fields = line.split(",");  // Split the CSV line by commas
        if (fields.length != USER_DATA_SAVE_FILE_FIELDS) {
            throw new ArrayIndexOutOfBoundsException();
        }
        double height = Double.parseDouble(fields[0]);
        double weight = Double.parseDouble(fields[1]);
        boolean isMale = parseGender(fields[2].trim());
        int age = Integer.parseInt(fields[3]);
        String healthGoal = parseHealthGoal(fields[4].trim());
        double idealCalories = Double.parseDouble(fields[5]);
        String localDateTime = parseLocalDateTime(fields[6].trim());
        boolean isAbleToSeeSpecialChars = Boolean.parseBoolean(fields[7]);

        return new User(height, weight, isMale, age, healthGoal, idealCalories, localDateTime, isAbleToSeeSpecialChars);
    }

    /**
     * Parses and validates a health goal string.
     *
     * @param healthGoal the health goal to parse, expected to be "WEIGHT_LOSS", "STEADY_STATE", or "BULKING"
     * @return the validated health goal string if it matches one of the expected values
     * @throws IllegalArgumentException if the health goal is invalid
     */
    private static String parseHealthGoal(String healthGoal) throws IllegalArgumentException{
        if (!healthGoal.equals("WEIGHT_LOSS") && !healthGoal.equals("STEADY_STATE") && !healthGoal.equals("BULKING")) {
            throw new IllegalArgumentException();
        }
        return healthGoal;
    }

    /**
     * Parses and validates a local date-time string.
     *
     * @param localDateTime the date-time string to parse, expected to follow User.DATE_TIME_FORMATTER
     * @return the validated date-time string if it matches the expected format
     * @throws DateTimeParseException if the date-time format is invalid
     */
    private static String parseLocalDateTime(String localDateTime) throws DateTimeParseException{
        LocalDateTime.parse(localDateTime, User.DATE_TIME_FORMATTER);
        return localDateTime;
    }

    /**
     * Parses and validates a gender string.
     *
     * @param isMaleString the gender string to parse, expected to be "true" or "false"
     * @return true if the string is "true", false if the string is "false"
     * @throws IllegalArgumentException if the gender string is neither "true" nor "false"
     */
    private static boolean parseGender(String isMaleString) {
        if ("true".equals(isMaleString)) {
            return true;
        } else if ("false".equals(isMaleString)) {
            return false;
        } else {
            throw new IllegalArgumentException();
        }
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
    //@@author

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
