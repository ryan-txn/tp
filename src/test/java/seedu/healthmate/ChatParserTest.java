package seedu.healthmate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    /**
     * Tests if adding a meal with correctly specified calories works as expected
     */
    @Test void addMealToOptionsWithName_success() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "save meal burger /c 300\nmeal menu\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
            + UI.simulateReply("burger with 300 calories", "Added to options: ")
            + chatParser.toMealOptionsStringWithNew("burger with 300 calories")
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests if adding a meal to meal options without a name failes correctly
     */
    @Test void addMealToOptionsNoName_failure() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "save meal /c 300\nbye";
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
        String simulatedInput = "add mealEntry pizza /c 300\nbye";
        String timeString = "(at: " + LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).toString() + ")";
        String expectedOuput = UI.simulateInitOutput()
            + UI.simulateReply("pizza with 300 calories " + timeString, "Tracked: ")
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOuput);
    }
}
