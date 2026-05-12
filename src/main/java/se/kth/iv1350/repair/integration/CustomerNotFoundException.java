package se.kth.iv1350.repair.integration;

/**
 * Thrown when a search is made for a phone number that does not exist
 * in the customer registry.
 */
public class CustomerNotFoundException extends Exception {

    /**
     * Creates a new instance with a message containing the phone number searched.
     *
     * @param phoneNumber The phone number that was not found.
     */
    public CustomerNotFoundException(int phoneNumber) {
        super("No customer found with phone number: " + phoneNumber);
    }
}