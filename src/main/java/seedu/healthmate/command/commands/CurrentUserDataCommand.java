package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.User;
import seedu.healthmate.services.UI;
import seedu.healthmate.services.UserHistoryTracker;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to update the user's personal data.
 * This command triggers prompts to gather and update the user's height, weight, gender, age, and health goal.
 */
public class CurrentUserDataCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "current userdata";
    public static final String COMMAND_LOWER = "current userdata";
    /** Command format for updating user data. */
    private static final String FORMAT = "current userdata";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Prints most recent userdata or error if none found";

    /**
     * Constructs an {@code CurrentUserDataCommand} object with a predefined command keyword,
     * format, and description.
     */
    public CurrentUserDataCommand() {
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
        Optional<User> lastUser = userHistoryTracker.getLatestUser();
        logger.log(Level.INFO, "Executing command to return current user data");

        // Prompts the user to enter new data
        lastUser.ifPresentOrElse(
                user -> {
                    UI.printSeparator();
                    UI.printString("Here is your current user data:");
                    user.printUIString();
                    UI.printSeparator();
                },
                () -> UI.printReply("Hit ENTER to set up a new user.", "")
        );

        logger.log(Level.INFO, "Finish executing command to return current user data");
    }
}
