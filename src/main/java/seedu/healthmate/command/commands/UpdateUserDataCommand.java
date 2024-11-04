package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;
import seedu.healthmate.core.User;
import seedu.healthmate.core.UserHistoryTracker;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateUserDataCommand extends Command{
    public static final String COMMAND = "update userdata";
    private static final String FORMAT = "update userdata";
    private static final String DESCRIPTION = "Triggers prompts for asking height, weight, gender, age and health goal";

    public UpdateUserDataCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }

    public static void executeCommand(UserHistoryTracker userHistoryTracker, Logger logger) {
        logger.log(Level.INFO, "Executing command to update user data");
        User newUser = User.askForUserData();
        assert newUser != null : "New user data should not be null";
        userHistoryTracker.printAllUserEntries();
    }
}
