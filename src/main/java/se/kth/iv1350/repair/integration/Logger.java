package se.kth.iv1350.repair.integration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Provides shared file logging functionality for all loggers in the system.
 */
public abstract class Logger {
    private final String fileName;

    /**
     * Creates a new Logger that writes to the specified file.
     *
     * @param fileName The name of the file to write log entries to.
     */
    public Logger(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Logs a message to the log file.
     *
     * @param message The message to log.
     */
    public void log(String message) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, true));
            writer.println(LocalDateTime.now() + " " + message);
            writer.println();
            writer.close();
        } catch (IOException e) {
            System.err.println("Could not write to log file: " + e.getMessage());
        }
    }
}