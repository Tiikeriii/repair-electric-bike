package se.kth.iv1350.repair.integration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class FileLogger {
    private final String logFile = "error.log";

    /**
     * Logs an exception to the error log file.
     *
     * @param e The exception to log.
     */
    public void log(Exception e) {
        try {
            PrintWriter printer = new PrintWriter(new FileWriter(logFile, true));
            printer.println(LocalDateTime.now() + " ERROR: " + e.getMessage());
            e.printStackTrace(printer); // writes the full stack trace to the file
            printer.println();
            printer.close();
        } catch (IOException ioException) {
            System.err.println("Could not write to log file: " + ioException.getMessage());
        }
    }
}