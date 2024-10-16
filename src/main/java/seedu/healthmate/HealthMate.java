package seedu.healthmate;

import me.tongfei.progressbar.ProgressBar;

public class HealthMate {
    private static HealthGoal healthGoal;
    private static ChatParser chatParser = new ChatParser();

    public static void main(String[] args) {
        UI.printGreeting();
        chatParser.run();

    }
}

