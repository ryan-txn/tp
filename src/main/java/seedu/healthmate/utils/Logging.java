package seedu.healthmate.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import seedu.healthmate.services.HistoryTracker;

/**
 * Utility class for setting up logging functionality.
 */
public class Logging {
    /**
     * Sets up a logger with both console and file handlers.
     * The console handler is set to only log SEVERE messages, while the file handler logs ALL levels.
     * Log files are stored in a 'logs' directory with the class name as the file name.
     *
     * @param logger The logger instance to be configured
     * @param nameClassToBeLogged The name of the class being logged (used for the log file name)
     * @return The configured logger instance
     */
    public static Logger setupLogger(Logger logger, String nameClassToBeLogged) {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logger.addHandler(ch);

        try {
            HistoryTracker.createDirectoryIfNotExists("logs");
            FileHandler fh = new FileHandler("logs" + File.separator + nameClassToBeLogged + ".log");
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);
            logger.addHandler(fh);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Logger file creation unsuccessful", ex);
        }
        return logger;
    }
}
