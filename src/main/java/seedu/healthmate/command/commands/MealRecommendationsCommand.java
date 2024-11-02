package seedu.healthmate.command.commands;

import seedu.healthmate.command.Command;

public class MealRecommendationsCommand extends Command {
    public static final String COMMAND = "meal recommendations";
    private static final String FORMAT = "meal recommendations";
    private static final String DESCRIPTION = "Displays meal recommendations based on User's health goal";

    public MealRecommendationsCommand(){
        super(COMMAND, FORMAT, DESCRIPTION);
    }
}
