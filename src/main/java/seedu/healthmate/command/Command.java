package seedu.healthmate.command;

public abstract class Command {
    private final String FORMAT;
    public final String COMMAND;
    private final String description;
    private static final String INDENTATION = "      ";

    public Command(String command, String format, String description) {
        this.FORMAT = format;
        this.COMMAND = command;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Command: " + COMMAND + "\n" +
                INDENTATION + "Format: " + FORMAT + "\n" +
                INDENTATION + "Description: " + description;
    }
    public String getCommand() {
        return COMMAND;
    }
}
