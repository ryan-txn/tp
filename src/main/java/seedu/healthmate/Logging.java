package seedu.healthmate;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
    public static Logger setupLogger(Logger logger, String NameClassToBeLogged) {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logger.addHandler(ch);

        try {
            HistoryTracker.createDirectoryIfNotExists("logs");
            FileHandler fh = new FileHandler("logs" + File.separator + NameClassToBeLogged + ".log");
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);
            logger.addHandler(fh);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Logger file creation unsuccessful", ex);
        }
        return logger;
    }
}
