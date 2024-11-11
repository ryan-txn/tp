package seedu.healthmate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.healthmate.core.HealthGoal;

/**
 * Test class for the HealthGoal class.
 */
public class HealthGoalTest {

    private HealthGoal healthGoal;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        // Initialize with a default health goal for each test
        healthGoal = new HealthGoal(2);
    }

    /**
     * Tests saving a valid health goal.
     */
    @Test
    public void testSaveHealthGoal_validGoal() {
        healthGoal.saveHealthGoal(1);
        assertEquals("WEIGHT_LOSS", healthGoal.getCurrentHealthGoal(),
                "Expected health goal to be WEIGHT_LOSS");

        healthGoal.saveHealthGoal(2);
        assertEquals("STEADY_STATE", healthGoal.getCurrentHealthGoal(),
                "Expected health goal to be STEADY_STATE");

        healthGoal.saveHealthGoal(3);
        assertEquals("BULKING", healthGoal.getCurrentHealthGoal(),
                "Expected health goal to be BULKING");
    }

    /**
     * Tests saving an invalid health goal.
     */
    @Test
    public void testSaveHealthGoal_invalidGoal() {
        healthGoal.saveHealthGoal(4);
        assertEquals("STEADY_STATE", healthGoal.getCurrentHealthGoal(),
                "Health goal should remain unchanged for invalid input");
    }

    /**
     * Tests target calories calculation for a male with a weight loss goal.
     */
    @Test
    public void testGetTargetCalories_maleWeightLoss() {
        healthGoal.saveHealthGoal(1);
        double targetCalories = healthGoal.getTargetCalories(180, 75, true, 25);
        assertEquals(1633.0, targetCalories, 0.01,
                "Expected target calories for male weight loss to be 1270.52");
    }

    /**
     * Tests target calories calculation for a female with a bulking goal.
     */
    @Test
    public void testGetTargetCalories_femaleBulking() {
        healthGoal.saveHealthGoal(3);
        double targetCalories = healthGoal.getTargetCalories(160, 55, false, 30);
        assertEquals(1850.0, targetCalories, 0.01,
                "Expected target calories for female bulking to be 1982.94");
    }

    /**
     * Tests target calories calculation for steady state.
     */
    @Test
    public void testGetTargetCalories_steadyState() {
        healthGoal.saveHealthGoal(2);
        double targetCalories = healthGoal.getTargetCalories(170, 68, true, 28);
        assertEquals(1821.0, targetCalories, 0.01,
                "Expected target calories for steady state to be 1821.86");
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public void testToString() {
        healthGoal.saveHealthGoal(3);
        assertEquals("BULKING", healthGoal.toString(),
                "Expected toString() to return BULKING");
    }
}
