package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class HistoricCalorieProgressCommand extends Command {
    public static final String COMMAND = "show historicCalories";
    private static final String FORMAT = "show historicCalories {Number of Days inclu. Today}";
    private static final String DESCRIPTION = "Prints Calorie Progress Bars to represent Historical Calorie Progress";

    public HistoricCalorieProgressCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
