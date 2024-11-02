package seedu.healthmate.core;

import java.util.ArrayList;

public class UserEntryList {
    private ArrayList<User> userEntryList;

    public UserEntryList() {
        this.userEntryList = new ArrayList<>();
    }

    public void addUserEntry(User user) {
        userEntryList.add(user);
    }

    public ArrayList<User> getUserEntryList() {
        return this.userEntryList;
    }

    public User getLastEntry() {
        return userEntryList.get(userEntryList.size() - 1);
    }

}
