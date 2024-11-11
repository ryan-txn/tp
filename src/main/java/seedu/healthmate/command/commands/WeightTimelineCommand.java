package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.UserEntryList;
import seedu.healthmate.services.UserHistoryTracker;
import seedu.healthmate.core.WeightEntryDisplay;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to view a timeline of the user's last 10 weight updates.
 * This command displays the weight entries in a timeline format.
 */
public class WeightTimelineCommand extends Command {

    /** Command keyword to invoke this action. */
    public static final String COMMAND = "weight timeline";
    public static final String COMMAND_LOWER = "weight timeline";
    /** Command format for displaying the weight timeline. */
    private static final String FORMAT = "weight timeline";

    /** Description of the command functionality. */
    private static final String DESCRIPTION = "View a timeline of the last 10 weight updates";

    /**
     * Constructs a {@code WeightTimelineCommand} object with a predefined command keyword,
     * format, and description.
     */
    public WeightTimelineCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    /**
     * Executes the weight timeline command by retrieving and displaying the user's weight history.
     * Logs the command execution and asserts that the user history data is available and valid.
     *
     * @param userHistoryTracker The tracker that stores and manages the user's historical data.
     * @param logger The logger used for logging command execution steps.
     */
    public static void executeCommand(UserHistoryTracker userHistoryTracker, Logger logger) {

        // Retrieves and verifies user weight history data
        Optional<UserEntryList> userHistoryData = userHistoryTracker.loadUserEntries();
        assert userHistoryData != null && userHistoryData.isPresent() : "User history data should not be null or empty";

        // Displays the weight timeline if data is available
        logger.log(Level.INFO, "Executing command to print weight timeline");
        WeightEntryDisplay.display(userHistoryData);
        logger.log(Level.INFO, "Finish executing command to print weight timeline.");
    }
}
