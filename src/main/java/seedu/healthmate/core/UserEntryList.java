package seedu.healthmate.core;

import java.util.ArrayList;

/**
 * Represents a list of user entries in the HealthMate application.
 * This class manages a collection of User objects and provides methods to manipulate and access the list.
 */
public class UserEntryList {
    private ArrayList<User> userEntryList;

    /**
     * Constructs an empty UserEntryList.
     */
    public UserEntryList() {
        this.userEntryList = new ArrayList<>();
    }

    /**
     * Adds a new user entry to the list.
     *
     * @param user The User object to be added to the list
     */
    public void addUserEntry(User user) {
        userEntryList.add(user);
    }

    /**
     * Returns the list of all user entries.
     *
     * @return ArrayList containing all User objects
     */
    public ArrayList<User> getUserEntryList() {
        return this.userEntryList;
    }

    /**
     * Returns the most recently added user entry.
     *
     * @return The last User object in the list
     */
    public User getLastEntry() {
        return userEntryList.get(userEntryList.size() - 1);
    }

    /**
     * Checks if the list of user entries is empty.
     * @return {@code true} if the user entry list contains no elements, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return this.userEntryList.isEmpty();
    }

    @Override
    public String toString() {
        return this.userEntryList.stream()
                .map(user -> user.toString()) // Convert each User to its String representation
                .reduce((user1, user2) -> user1 + "\n" + user2) // Concatenate with newlines
                .orElse(""); // Return empty string if the list is empty
    }


}
