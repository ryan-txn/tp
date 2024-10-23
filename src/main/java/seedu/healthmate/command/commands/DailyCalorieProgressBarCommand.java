package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class DailyCalorieProgressBarCommand extends Command {
    public static final String COMMAND = "show dailyCalorieProgressBar";
    private static final String FORMAT = "show dailyCalorieProgressBar";
    private static final String DESCRIPTION = "Prints a Daily Calorie Progress Bar to represent Daily Calorie Progress";

    public DailyCalorieProgressBarCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}


