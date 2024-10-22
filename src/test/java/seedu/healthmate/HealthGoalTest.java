package seedu.healthmate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        healthGoal = new HealthGoal("STEADY_STATE");
    }

    /**
     * Tests saving a valid health goal.
     */
    @Test
    public void testSaveHealthGoal_validGoal() {
        healthGoal.saveHealthGoal("WEIGHT_LOSS");
        assertEquals("WEIGHT_LOSS", healthGoal.getCurrentHealthGoal(),
                "Expected health goal to be WEIGHT_LOSS");

        healthGoal.saveHealthGoal("STEADY_STATE");
        assertEquals("STEADY_STATE", healthGoal.getCurrentHealthGoal(),
                "Expected health goal to be STEADY_STATE");

        healthGoal.saveHealthGoal("BULKING");
        assertEquals("BULKING", healthGoal.getCurrentHealthGoal(),
                "Expected health goal to be BULKING");
    }

    /**
     * Tests saving an invalid health goal.
     */
    @Test
    public void testSaveHealthGoal_invalidGoal() {
        healthGoal.saveHealthGoal("INVALID_GOAL");
        assertEquals("STEADY_STATE", healthGoal.getCurrentHealthGoal(),
                "Health goal should remain unchanged for invalid input");
    }

    /**
     * Tests target calories calculation for a male with a weight loss goal.
     */
    @Test
    public void testGetTargetCalories_maleWeightLoss() {
        healthGoal.saveHealthGoal("WEIGHT_LOSS");
        double targetCalories = healthGoal.getTargetCalories(180, 75, true, 25);
        assertEquals(1270.52, targetCalories, 0.01,
                "Expected target calories for male weight loss to be 1270.52");
    }

    /**
     * Tests target calories calculation for a female with a bulking goal.
     */
    @Test
    public void testGetTargetCalories_femaleBulking() {
        healthGoal.saveHealthGoal("BULKING");
        double targetCalories = healthGoal.getTargetCalories(160, 55, false, 30);
        assertEquals(1982.94, targetCalories, 0.01,
                "Expected target calories for female bulking to be 1982.94");
    }

    /**
     * Tests target calories calculation for steady state.
     */
    @Test
    public void testGetTargetCalories_steadyState() {
        healthGoal.saveHealthGoal("STEADY_STATE");
        double targetCalories = healthGoal.getTargetCalories(170, 68, true, 28);
        assertEquals(1821.86, targetCalories, 0.01,
                "Expected target calories for steady state to be 1821.86");
    }

    /**
     * Tests the toString() method.
     */
    @Test
    public void testToString() {
        healthGoal.saveHealthGoal("BULKING");
        assertEquals("BULKING", healthGoal.toString(),
                "Expected toString() to return BULKING");
    }
}
