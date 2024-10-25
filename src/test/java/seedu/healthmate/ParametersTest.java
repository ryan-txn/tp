package seedu.healthmate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        }

        // missing portions, default to 1
        try {
            int portions = Parameter.getPortions("add mealEntry grapes /c400");
            assertEquals(1, portions); // Default to 1 when portions are not specified
        } catch (Exception e) {
            // Shouldn't throw exception for missing portion, default to 1
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
        }

        // no portion signaller, default to 1
        try {
            int portions = Parameter.getPortions("add mealEntry grapes /c400");
            assertEquals(1, portions); // Default to 1 when portions are not specified
        } catch (Exception e) {
            // Shouldn't throw exception for missing portion, default to 1
        }
    }
}
