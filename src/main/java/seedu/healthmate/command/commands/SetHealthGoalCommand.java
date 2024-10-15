package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class SetHealthGoalCommand extends Command{
    public static final String COMMAND = "set HealthGoal";
    private static final String FORMAT = COMMAND + " {WEIGHT_LOSS, STEADY_STATE, BULKING}";
    private static final String DESCRIPTION = "Sets your HealthGoal for calculating and tracking ideal calorie count";

    public SetHealthGoalCommand() {
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
