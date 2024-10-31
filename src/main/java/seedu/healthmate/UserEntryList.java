package seedu.healthmate;

import java.sql.Array;
import java.util.ArrayList;

public class UserEntryList {
    private ArrayList<UserEntry> userEntryList;

    public UserEntryList() {
        this.userEntryList = new ArrayList<>();
    }

    public void addUserEntry(UserEntry userEntry) {
        userEntryList.add(userEntry);
    }

    public ArrayList<UserEntry> getUserEntryList() {
        return this.userEntryList;
    }

    public UserEntry getLastEntry() {
        return userEntryList.get(userEntryList.size() - 1);
    }

}
