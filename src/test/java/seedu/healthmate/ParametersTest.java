package seedu.healthmate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import seedu.healthmate.exceptions.BadCalorieException;
import seedu.healthmate.exceptions.BadPortionException;
import seedu.healthmate.exceptions.BadTimestampException;
import seedu.healthmate.exceptions.EmptyCalorieException;
import seedu.healthmate.exceptions.EmptyTimestampException;
import seedu.healthmate.utils.Parameter;

public class ParametersTest {
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
     * Test cases for getCalories method
     */
    @Test
    public void testGetCalories() {
        // valid case
        try {
            int calories = Parameter.getCalories("add mealEntry grapes /c400 /p3");
            assertEquals(400, calories);
        } catch (Exception e) {
            // Shouldn't throw exception for valid input
        }

        // missing calories (throws EmptyCalorieException)
        assertThrows(EmptyCalorieException.class, () -> {
            Parameter.getCalories("add mealEntry grapes /p3");
        });

        // bad format (throws BadCalorieException)
        assertThrows(EmptyCalorieException.class, () -> {
            Parameter.getCalories("add mealEntry grapes /1ch5 /p3");
        });

        // bad format (throws BadCalorieException)
        assertThrows(BadCalorieException.class, () -> {
            Parameter.getCalories("add mealEntry grapes /c123123asdasd /p3");
        });

        // bad format (throws BadCalorieException)
        assertThrows(EmptyCalorieException.class, () -> {
            Parameter.getCalories("add mealEntry grapes /abc 400 /p3");
        });

    }

    /**
     * Test cases for getPortions method
     */
    @Test
    public void testGetPortions() {
        // valid case
        try {
            int portions = Parameter.getPortions("add mealEntry grapes /c400 /p3");
            assertEquals(3, portions);
        } catch (Exception e) {
            // Shouldn't throw exception for valid input
            fail("Exception thrown for valid input: " + e.getMessage());
        }

        // missing portions, default to 1
        try {
            int portions = Parameter.getPortions("add mealEntry grapes /c400");
            assertEquals(1, portions); // Default to 1 when portions are not specified
        } catch (Exception e) {
            // Shouldn't throw exception for missing portion, default to 1
            fail("Exception thrown for valid input: " + e.getMessage());
        }

        // bad format (throws BadPortionException)
        assertThrows(BadPortionException.class, () -> {
            Parameter.getPortions("add mealEntry grapes /c400 /pabc");
        });

        // bad format (throws BadPortionException)
        assertThrows(BadPortionException.class, () -> {
            Parameter.getPortions("add mealEntry grapes /c400 /p3abc");
        });

        // valid with spaces
        try {
            int portions = Parameter.getPortions("add mealEntry grapes /p2 ");
            assertEquals(2, portions); // Spaces should be ignored
        } catch (Exception e) {
            // Shouldn't throw exception for valid input with spaces
            fail("Exception thrown for valid input: " + e.getMessage());
        }

        // no portion signaller, default to 1
        try {
            int portions = Parameter.getPortions("add mealEntry grapes /c400");
            assertEquals(1, portions); // Default to 1 when portions are not specified
        } catch (Exception e) {
            // Shouldn't throw exception for missing portion, default to 1
            fail("Exception thrown for valid input: " + e.getMessage());
        }
    }

    /**
     * Test cases for getTimestamp method.
     */
    @Test
    public void testGetTimestamp() {
        // valid case
        try {
            LocalDate date = Parameter.getTimestamp("add mealEntry grapes /c400 /t2024-11-05");
            assertEquals(LocalDate.of(2024, 11, 5), date);
        } catch (Exception e) {
            // Shouldn't throw an exception for valid input
            fail("Exception thrown for valid input: " + e.getMessage());
        }

        // missing timestamp (throws EmptyTimestampException)
        assertThrows(EmptyTimestampException.class, () -> {
            Parameter.getTimestamp("add mealEntry grapes /c400");
        });

        // invalid date format (throws BadTimestampException)
        assertThrows(BadTimestampException.class, () -> {
            Parameter.getTimestamp("add mealEntry grapes /c400 /t05-11-2024");
        });

        // invalid date format with characters (throws BadTimestampException)
        assertThrows(BadTimestampException.class, () -> {
            Parameter.getTimestamp("add mealEntry grapes /c400 /t2024-11-aa");
        });

        // valid case with spaces after timestamp
        try {
            LocalDate date = Parameter.getTimestamp(" add mealEntry grapes /c400 /t2024-11-05 ");
            assertEquals(LocalDate.of(2024, 11, 5), date); // Leading and trailing spaces ignored
        } catch (Exception e) {
            // Shouldn't throw an exception for valid input with spaces
            fail("Exception thrown for valid input with spaces: " + e.getMessage());
        }
    }

}
