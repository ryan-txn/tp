package seedu.healthmate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.healthmate.core.User;
import seedu.healthmate.core.UserEntryList;
import seedu.healthmate.core.WeightEntryDisplay;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightEntryDisplayTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testShouldPrintGraph() {
        // Setup test data
        UserEntryList users = new UserEntryList();

        users.addUserEntry(new User(70.0, 79.0, true, 25,"WEIGHT_LOSS", 2200, "2024-10-24 16:34:15", true));
        users.addUserEntry(new User(70.0, 78.0, true, 25, "WEIGHT_LOSS", 2200, "2024-10-25 16:34:15", true));
        users.addUserEntry(new User(70.0, 77.5, true, 25, "WEIGHT_LOSS", 2200, "2024-10-26 16:34:15", true));
        users.addUserEntry(new User(70.0, 76.0, true, 25, "WEIGHT_LOSS", 2200, "2024-10-27 16:34:15", true));
        users.addUserEntry(new User(70.0, 75.0, true, 25, "WEIGHT_LOSS", 2200, "2024-10-28 16:34:15", true));
        users.addUserEntry(new User(70.0, 74.0, true, 25, "WEIGHT_LOSS", 2200, "2024-10-29 16:34:15", true));
        users.addUserEntry(new User(70.0, 73.5, true, 25, "WEIGHT_LOSS", 2200, "2024-10-30 16:34:15", true));
        users.addUserEntry(new User(70.0, 72.0, true, 25, "WEIGHT_LOSS", 2200, "2024-10-31 16:34:15", true));
        users.addUserEntry(new User(70.0, 71.0, true, 25, "WEIGHT_LOSS", 2200, "2024-11-01 16:34:15", true));
        users.addUserEntry(new User(70.0, 70.0, true, 25, "WEIGHT_LOSS", 2200, "2024-11-02 16:34:15", true));

        Optional<UserEntryList> optionalUserEntryList = Optional.of(users);


        WeightEntryDisplay.display(optionalUserEntryList);


        String output = outContent.toString();


        assertTrue(output.contains("Weight Timeline"));
        assertTrue(output.contains("|"));
        assertTrue(output.contains("--"));
    }
}
