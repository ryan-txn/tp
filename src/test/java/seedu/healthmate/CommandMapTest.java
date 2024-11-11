package seedu.healthmate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.healthmate.command.Command;
import seedu.healthmate.command.CommandMap;
import seedu.healthmate.command.commands.MealLogCommand;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


class CommandMapTest {

    @BeforeEach
    void setUp() {
        // Initialize CommandMap if necessary
        // Since the static block initializes the COMMANDSMAP, no setup is needed
    }

    @Test
    void testGetCommandsRegularUsage() {
        // Test when userInput matches the command exactly
        String userInput = "list commands";
        String command = "list commands";

        List<Command> commands = CommandMap.getCommands(userInput, command);

        // Expect that all commands are returned
        assertEquals(14, commands.size());
    }

    @Test
    void testGetCommandsInvalidCommand() {
        // Test when userInput is not in the command map
        String userInput = "list commands asdas";
        String command = "list commands";

        List<Command> commands = CommandMap.getCommands(userInput, command);

        // Expect that all commands are returned
        assertEquals(0, commands.size());
    }

    @Test
    void testGetCommandsValidCommand() {
        // Test when userInput is not in the command map
        String userInput = "list commands meal log";
        String command = "list commands";

        List<Command> commands = CommandMap.getCommands(userInput, command);

        // Expect that all commands are returned
        assertEquals(1, commands.size());
        assertInstanceOf(MealLogCommand.class, commands.get(0));
    }
}


