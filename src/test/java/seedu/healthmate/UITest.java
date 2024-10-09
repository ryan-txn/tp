package seedu.healthmate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class UITest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SEPARATOR = "_________________________________________________________________________";
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



}
