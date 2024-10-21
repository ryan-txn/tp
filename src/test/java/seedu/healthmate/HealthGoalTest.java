package seedu.healthmate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthGoalTest {

    private HealthGoal healthGoal;

    @BeforeEach
    public void setUp() {
        // Initialize with a default health goal for each test
        healthGoal = new HealthGoal("STEADY_STATE");
    }

    @Test
    public void testSaveHealthGoal_validGoal() {
        // Test saving a valid health goal
        healthGoal.saveHealthGoal("WEIGHT_LOSS");
        assertEquals("WEIGHT_LOSS", healthGoal.getCurrentHealthGoal());

        healthGoal.saveHealthGoal("STEADY_STATE");
        assertEquals("STEADY_STATE", healthGoal.getCurrentHealthGoal());

        healthGoal.saveHealthGoal("BULKING");
        assertEquals("BULKING", healthGoal.getCurrentHealthGoal());
    }

    @Test
    public void testSaveHealthGoal_invalidGoal() {
        // Save an invalid health goal
        healthGoal.saveHealthGoal("INVALID_GOAL");
        // Check if the health goal remains unchanged or is null
        assertEquals("STEADY_STATE", healthGoal.getCurrentHealthGoal());  // Should remain unchanged
    }

    @Test
    public void testGetTargetCalories_maleWeightLoss() {
        // Test target calories for male with weight loss goal
        healthGoal.saveHealthGoal("WEIGHT_LOSS");
        double targetCalories = healthGoal.getTargetCalories(180, 75, true, 25);
        assertEquals(1270.52, targetCalories, 0.01);
    }

    @Test
    public void testGetTargetCalories_femaleBulking() {
        // Test target calories for female with bulking goal
        healthGoal.saveHealthGoal("BULKING");
        double targetCalories = healthGoal.getTargetCalories(160, 55, false, 30);
        assertEquals(1982.94, targetCalories, 0.01);
    }

    @Test
    public void testGetTargetCalories_steadyState() {
        // Test target calories for steady state
        healthGoal.saveHealthGoal("STEADY_STATE");
        double targetCalories = healthGoal.getTargetCalories(170, 68, true, 28);
        assertEquals(1821.86, targetCalories, 0.01);
    }

    @Test
    public void testToString() {
        // Test the toString() method
        healthGoal.saveHealthGoal("BULKING");
        assertEquals("BULKING", healthGoal.toString());
    }
}
