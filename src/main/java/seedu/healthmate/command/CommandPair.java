package seedu.healthmate.command;

import seedu.healthmate.utils.Pair;

public class CommandPair extends Pair<String, String[]> {

    public CommandPair(String twoTokenCommand, String[] additionalCommands){
        super(twoTokenCommand, additionalCommands);
    }

    public String getMainCommand() {
        return super.t();
    }

    public String getCommandByIndex(int index) {
        return super.u()[index];
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
