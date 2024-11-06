package seedu.healthmate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import seedu.healthmate.core.User;
import seedu.healthmate.services.UserHistoryTracker;

public class UserHistoryTrackerTest {
    private UserHistoryTracker userHistoryTracker;

    @BeforeEach
    public void setUp() {
        userHistoryTracker = new UserHistoryTracker();
    }

    @Test
    public void shouldSaveUserEntry_WhenEntryIsAdded() {
        User user = new User(200, 200, false, 90, "STEADY_STATE");
        userHistoryTracker.addUserEntry(user);
        String expectedUser = user.toString();
        String savedUser = userHistoryTracker.getLastUser()
                .map(x -> x.toString())
                .orElse("Error");
        assertEquals(expectedUser, savedUser);
    }


    @AfterEach
    public void userHistoryTracker_cleanup() {
        userHistoryTracker.clearSaveFile();

        User testUser = new User(180, 80, true, 20, "BULKING");

        userHistoryTracker.addUserEntry(testUser);
    }

}
