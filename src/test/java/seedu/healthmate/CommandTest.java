package seedu.healthmate;



import org.junit.jupiter.api.Test;
import seedu.healthmate.command.Command;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    // Concrete subclass of Command for testing
    private static class TestCommand extends Command {

        public TestCommand(String command, String format, String description) {
            super(command, format, description);
        }
    }

    @Test
    public void testGetCommand() {
        Command command = new TestCommand("add", "add <meal>", "Adds a meal to the system");
        assertEquals("add", command.getCommand(), "The command should be 'add'.");
    }

    @Test
    public void testToString() {
        Command command = new TestCommand("add", "add <meal>", "Adds a meal to the system");
        String expectedString = "Command: add\n      Format: add <meal>\n      Description: Adds a meal to the system";
        assertEquals(expectedString, command.toString(), "The toString method should return the expected " +
                "formatted string.");
    }

    @Test
    public void testToStringWithDifferentValues() {
        Command command = new TestCommand("remove", "remove <meal>", "Removes a meal from the system");
        String expectedString = "Command: remove\n      Format: remove <meal>\n      Description: Removes a meal " +
                "from the system";
        assertEquals(expectedString, command.toString(), "The toString method should return the expected formatted " +
                "string for different values.");
    }
}
