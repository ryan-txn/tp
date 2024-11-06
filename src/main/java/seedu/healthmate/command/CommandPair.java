package seedu.healthmate.command;

import seedu.healthmate.utils.Pair;

/**
 * Represents a combination of commands with a hierarchy of a main command and additional subcommands.
 * Extends {@code Pair} with the first element as the main command and
 * the second as an array of subcommands.
 */
public class CommandPair extends Pair<String, String[]> {

    public CommandPair(String twoTokenCommand, String[] additionalCommands){
        super(twoTokenCommand, additionalCommands);
    }

    /**
     * Returns the main command.
     * @return main command as a {@code String}
     */
    public String getMainCommand() {
        return super.t();
    }

    /**
     * Returns the subcommand at the specified index.
     * @param index the index of the subcommand
     * @return subcommand at the specified index
     */
    public String getCommandByIndex(int index) {
        return super.u()[index];
    }

    @Override
    public String toString() {
        return "Main Command: " + super.t() + " Other: " + super.u().toString();
    }

}
