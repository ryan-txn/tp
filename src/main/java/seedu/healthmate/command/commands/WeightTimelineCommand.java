package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.UserEntryList;
import seedu.healthmate.core.UserHistoryTracker;
import seedu.healthmate.core.WeightEntryDisplay;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeightTimelineCommand extends Command {
    public static final String COMMAND = "weight timeline";
    private static final String FORMAT = "weight timeline";
    private static final String DESCRIPTION = "View a timeline of the last 10 weight updates";

    public WeightTimelineCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(UserHistoryTracker userHistoryTracker, Logger logger) {
        logger.log(Level.INFO, "Executing command to print weight timeline");
        Optional<UserEntryList> userHistoryData = userHistoryTracker.loadUserData();
        assert userHistoryData != null && userHistoryData.isPresent() : "User history data should not be null or empty";
        WeightEntryDisplay.display(userHistoryData);
    }
}
