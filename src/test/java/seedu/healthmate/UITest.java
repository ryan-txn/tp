package seedu.healthmate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import seedu.healthmate.services.UI;


public class UITest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SEPARATOR =
            "_____________________________________________________________________________";
    private static final String INDENTATION = "      ";
    private static final String LINE = INDENTATION + SEPARATOR;
    private static final String FRAME_LINE = LINE + LINE_SEPARATOR;
    private static final String LOGO =
            INDENTATION + " |\n"
                    + INDENTATION + "     \\\\|//\n"
                    + INDENTATION + "     \\\\|//\n"
                    + INDENTATION + "    \\\\\\|///\n"
                    + INDENTATION + "    \\\\\\|///\n"
                    + INDENTATION + "     \\\\|//\n"
                    + INDENTATION + "      \\|/\n"
                    + INDENTATION + "       |\n";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Tests if print greeting methods prints correctly
     */
    @Test
    public void testPrintGreeting() {
        UI.printGreeting();
        String expectedOutput = INDENTATION + LOGO
                + LINE_SEPARATOR
                + LINE + LINE_SEPARATOR
                + INDENTATION + "Welcome to HealthMate" + LINE_SEPARATOR
                + INDENTATION + "Let's get healthy!" + LINE_SEPARATOR
                + LINE + LINE_SEPARATOR;

        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Tests if farewell message prints correctly
     */
    @Test
    public void testPrintFarewell() {
        UI.printFarewell();
        String expectedOutput = INDENTATION + "Stay healthy!" + LINE_SEPARATOR + LINE + LINE_SEPARATOR;
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Tests if reply method works with and without arguments
     */
    @Test
    public void testPrintReply() {
        UI.printReply("Test input", "Action performed: ");
        String expectedOutput = LINE + LINE_SEPARATOR
                + INDENTATION + "Action performed: Test input" + LINE_SEPARATOR
                + LINE + LINE_SEPARATOR;
        assertEquals(expectedOutput, outContent.toString());

        UI.printReply("", "");
        String expectedOutput2 = LINE + LINE_SEPARATOR
                + INDENTATION + LINE_SEPARATOR
                + LINE + LINE_SEPARATOR;
        assertEquals(expectedOutput + expectedOutput2, outContent.toString());
    }

}
