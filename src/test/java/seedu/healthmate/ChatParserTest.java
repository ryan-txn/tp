package seedu.healthmate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ChatParserTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOutput = System.out;


    @BeforeEach
    public void setOutputStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStream() {
        System.setOut(originalOutput);
    }

    /**
     * Mocks the chatParser.run() method
     * @param chatParser
     * @param simulatedInput User input for which the behaviour of chatParser.run() is asserted
     * @param expectedOutput Expected output printed to the consule that is to be compared with the actual ouput
     */
    private void compareChatParserOutput(ChatParser chatParser, String simulatedInput, String expectedOutput) {
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        chatParser.run();
        assertEquals(outputStream.toString(), expectedOutput);
        chatParser.cleanMealLists();
    }


    /**
     * Tests if the ChatParser correctly flags invalid random input
     * Inspired by: https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
     */
    @Test
    public void randomInput_printsError() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "hi\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
            + UI.simulateReply("Invalid command", "Retry: ")
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }


    @Test
    void addMealToOptionsWithName_success() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "save meal burger /c300\nmeal menu\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
            + UI.simulateReply("burger with 300 calories", "Added to options: ")
            + chatParser.getMealOptionsStringWithNewMeal("burger with 300 calories")
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests if adding a meal to meal options without a name failes correctly
     */
    @Test void addMealToOptionsNoName_failure() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "save meal /c300\nbye";
        String expectedOuput = UI.simulateInitOutput()
            + UI.simulateReply("Meal options require a name", "Retry: ")
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOuput);
    }

    /**
     * Tests if tracking a meal entry with correctly specified calories was successful.
     */
    @Test void trackMealEntryWithCalories_success() {
        ChatParser chatParser = new ChatParser();
        User user = User.checkForUserData(chatParser.getHistoryTracker());
        String simulatedInput = "add mealEntry pizza /c300\nbye";
        LocalDate today = LocalDateTime.now().toLocalDate();
        String timeString = "(at: " + today + ")";
        String expectedOutput = UI.simulateInitOutput()
            + UI.simulateReply("pizza with 300 calories " + timeString, "Tracked: ")
            + user.simulateUsersConsumptionBar(300, today)
            + System.lineSeparator()
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }
}
