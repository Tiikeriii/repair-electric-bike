package se.kth.iv1350.repair.integration;

/**
 * Logs error messages and exceptions to a file for developer inspection.
 */
public class ErrorLogger extends Logger {

    /**
     * Creates a new ErrorLogger that writes to the specified file.
     *
     * @param fileName The name of the file to write log entries to.
     */
    public ErrorLogger(String fileName) {
        super(fileName);
    }

    /**
     * Logs an exception with its full stack trace to the log file.
     *
     * @param message A description of the error context.
     * @param e       The exception to log.
     */
    public void logException(String message, Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR: ").append(message).append("\n");
        sb.append("Exception: ").append(e.getMessage()).append("\n");
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\tat ").append(element).append("\n");
        }
        log(sb.toString());
    }
}
