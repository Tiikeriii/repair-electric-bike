package se.kth.iv1350.repair.integration;

/**
 * Thrown when the customer registry could not be reached.
 */
public class DatabaseFailureException extends RuntimeException {

    /**
     * Creates a new instance with a message describing the failure.
     *
     * @param message A description of the failure.
     */
    public DatabaseFailureException(String message) {
        super(message);
    }

    /**
     * Creates a new instance with a message and the cause of the failure.
     *
     * @param message A description of the failure.
     * @param cause   The exception that caused this failure.
     */
    public DatabaseFailureException(String message, Exception cause) {
        super(message, cause);
    }
}