package se.kth.iv1350.repair.integration;

import se.kth.iv1350.repair.model.Customer;
import se.kth.iv1350.repair.model.CustomerDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all access to the customer registry.
 */
public class CustomerRegistry {
    private static final CustomerRegistry instance = new CustomerRegistry();
    private Map<Integer, Customer> customers = new HashMap<>();

    /**
     * Private constructor tto prevent instantiation of multiple instances
     */
    private CustomerRegistry() {
        customers.put(123456789, new Customer(
        "Alice Svensson", 123456789, "alice@example.com",
        "Trek", "Powerfly 5", "SN-2024-XR7" ));
    }

    /**
     * Returns the CustomerRegistry instance
     * 
     * @return The single CustomerRegistry instance
     */
    public static CustomerRegistry getInstance() {
        return instance;
    }

    /**
     * Clears the customer list, only used for testing
     */
    public static void clearForTesting() {
        instance.customers.clear();
        instance.customers.put(123456789, new Customer(
        "Alice Svensson", 123456789, "alice@example.com",
        "Trek", "Powerfly 5", "SN-2024-XR7" ));
    }

    /**
     * Searches for a customer by phone number and returns a DTO if found.
     * Throws a DatabaseFailureException if the phoneNumber 69 is searched for
     *
     * @param phoneNumber The customer's phone number to search for.
     * @return A CustomerDTO if the customer was found, or null if not found.
     * @throws CustomerNotFoundException when the specified phone number is not found
     * @throws DatabaseFailureException when the phonenumber (69) is searched for
     */
    public CustomerDTO findCustomer(int phoneNumber) throws CustomerNotFoundException, DatabaseFailureException {
        if (phoneNumber == 69) {
            throw new DatabaseFailureException("Unable to reach the customer registry");
        }
        Customer customer = customers.get(phoneNumber);
        if (customer == null) {
            throw new CustomerNotFoundException(phoneNumber);
        }
        return customer.toDTO();
    }
}
