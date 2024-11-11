package seedu.healthmate.command;

/**
 * Represents an abstract command with a specific command string, format, and description.
 * This class serves as a base for creating different types of commands, each having its own format
 * and description for user interaction.
 */
public abstract class Command {
    public static final String INDENTATION = "      ";

    public final String command;
    private String commandLower;
    private final String format;
    private final String description;

    /**
     * Constructs a Command instance with the specified details.
     *
     * @param command The command string that triggers the command.
     * @param format The format for using the command.
     * @param description A description of what the command does.
     */
    public Command(String command, String format, String description) {
        this.format = format;
        this.command = command;
        this.description = description;
    }

    /**
     * Returns a string representation of the command, including its name, format, and description.
     *
     * @return A formatted string containing the command details.
     */
    @Override
    public String toString() {
        return "Command: " + command + "\n" +
                INDENTATION + "Format: " + format + "\n" +
                INDENTATION + "Description: " + description;
    }

    public String shortDescription() {
        return command + " \n " + INDENTATION + format;
    }

    /**
     * Returns the command string.
     *
     * @return The command string.
     */
    public String getCommand() {
        return command;
    }


}
