package seedu.healthmate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;

import seedu.healthmate.services.HistoryTracker;

public class HistoryTrackerTest {
    
    @Test
    public void testCreateDataDirectory() {
        HistoryTracker historyTracker = new HistoryTracker();
        File dataDirectory = new File("data");
        assertEquals(true, dataDirectory.exists(), "Data directory should be created");
        dataDirectory.delete();
    }
}
