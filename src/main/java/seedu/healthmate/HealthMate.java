package seedu.healthmate;

import seedu.healthmate.services.ChatParser;
import seedu.healthmate.services.UI;

public class HealthMate {
    private static ChatParser chatParser = new ChatParser();

    public static void main(String[] args) {
        UI.printGreeting();
        chatParser.run();

    }
}

