package seedu.healthmate.command;

public abstract class Command {
    private static final String INDENTATION = "      ";

    public final String command;
    private final String format;
    private final String description;

    public Command(String command, String format, String description) {
        this.format = format;
        this.command = command;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Command: " + command + "\n" +
                INDENTATION + "Format: " + format + "\n" +
                INDENTATION + "Description: " + description;
    }
    public String getCommand() {
        return command;
    }
}
