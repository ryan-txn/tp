package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.UserEntryList;
import seedu.healthmate.services.UserHistoryTracker;
import seedu.healthmate.services.UI;


import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to clear the user's personal data.
 */
public class ClearUserDataCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "clear userdata";

    public static final String COMMAND_LOWER = "clear userdata";


    /** Command format for clearing user data. */
    private static final String FORMAT = "clear userdata";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "Clears all userdata";

    /**
     * Constructs an {@code ClearUserDataCommand} object with a predefined command keyword,
     * format, and description.
     */
    public ClearUserDataCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the clear user data command deleting all file data
     * Logs the command execution and asserts that the new user data is valid.
     *
     * @param userHistoryTracker The user history tracker used to save and display updated user information.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(UserHistoryTracker userHistoryTracker, Logger logger) {
        logger.log(Level.INFO, "Executing command to update user data");

        userHistoryTracker.clearSaveFile();
        Optional<UserEntryList> updatedUserEntryList = userHistoryTracker.loadUserEntries();
        assert updatedUserEntryList.isEmpty() : "New user entry list should be empty";

        UI.printReply("User Data has been cleared!", "Success: ");
        logger.log(Level.INFO, "Finish executing command to clear user data");
    }
}
