package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.User;
import seedu.healthmate.services.UserHistoryTracker;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to update the user's personal data.
 * This command triggers prompts to gather and update the user's height, weight, gender, age, and health goal.
 */
public class UpdateUserDataCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "update userdata";
    public static final String COMMAND_LOWER = "update userdata";
    /** Command format for updating user data. */
    private static final String FORMAT = "update userdata";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Triggers prompts for asking height, weight, gender, age and health goal";

    /**
     * Constructs an {@code UpdateUserDataCommand} object with a predefined command keyword,
     * format, and description.
     */
    public UpdateUserDataCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the update user data command by triggering prompts to collect new data for
     * the user's profile, including height, weight, gender, age, and health goal.
     * Logs the command execution and asserts that the new user data is valid.
     *
     * @param userHistoryTracker The user history tracker used to save and display updated user information.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(UserHistoryTracker userHistoryTracker, Logger logger) {
        logger.log(Level.INFO, "Executing command to update user data");

        // Prompts the user to enter new data
        User newUser = User.askForUserData();
        assert newUser != null : "New user data should not be null";

        // Displays all user entries after update
        userHistoryTracker.printAllUserEntries();
        logger.log(Level.INFO, "Finish executing command to update user data");
    }
}
