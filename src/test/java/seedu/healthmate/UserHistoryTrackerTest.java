package seedu.healthmate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import seedu.healthmate.core.HealthGoal;
import seedu.healthmate.core.User;
import seedu.healthmate.core.UserEntryList;
import seedu.healthmate.services.UserHistoryTracker;

public class UserHistoryTrackerTest {
    private UserHistoryTracker userHistoryTracker;

    @BeforeEach
    public void setUp() {
        userHistoryTracker = new UserHistoryTracker();
    }

    /**
     * Verifies that adding a User entry to UserHistoryTracker successfully saves the entry.
     * Checks if the last saved user entry matches the expected User data.
     */
    @Test
    public void shouldSaveUserWhenEntryIsAdded() {
        User user = User.createAlternativeUserStub();
        userHistoryTracker.addUserEntry(user);
        String expectedUser = user.toString();
        String savedUser = userHistoryTracker.getLatestUser()
                .map(x -> x.toString())
                .orElseThrow(() -> new AssertionError("Expected a saved user entry, but none was found"));
        assertEquals(expectedUser, savedUser);
    }

    /**
     * Tests that UserEntryList loads correctly from the save file. Clears the save file,
     * adds multiple User entries to UserHistoryTracker, and constructs the expected content.
     * Loads the UserEntryList and verifies it matches the expected result.
     */
    @Test
    public void shouldLoadUserEntryListWhenSaveFileExists() {
        userHistoryTracker.clearSaveFile();
        User altUser = User.createAlternativeUserStub();
        User user = User.createUserStub();
        StringBuilder expectedSaveFileContent = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            userHistoryTracker.addUserEntry(altUser);
            expectedSaveFileContent.append("\n").append(altUser.toString());
            userHistoryTracker.addUserEntry(user);
            expectedSaveFileContent.append("\n").append(user.toString());
        }

        UserEntryList userEntryList = userHistoryTracker.loadUserEntries()
                .orElseThrow(() -> new AssertionError("Expected a loaded UserEntryList, but none was found"));

        assertEquals(expectedSaveFileContent.toString().trim(), userEntryList.toString());
    }

    @AfterEach
    public void userHistoryTracker_cleanup() {
        userHistoryTracker.clearSaveFile();
        HealthGoal healthGoal = new HealthGoal(3);
        User testUser = new User(180, 80, true, 20, healthGoal, true);

        userHistoryTracker.addUserEntry(testUser);
    }

}
