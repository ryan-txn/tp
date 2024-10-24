package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class TodayCalorieProgressCommand extends Command {
    public static final String COMMAND = "show todayCalories";
    private static final String FORMAT = "show todayCalories";
    private static final String DESCRIPTION = "Prints a Daily Calorie Progress Bar to represent Today Calorie Progress";

    public TodayCalorieProgressCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}


