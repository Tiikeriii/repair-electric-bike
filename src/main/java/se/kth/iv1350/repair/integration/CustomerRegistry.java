package se.kth.iv1350.repair.integration;

import se.kth.iv1350.repair.model.CustomerDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all access to the customer registry.
 */
public class CustomerRegistry {
    private final Map<Integer, CustomerDTO> customers = new HashMap<>();

    /**
     * Creates the registry and seeds it with one sample customer for simulation.
     */
    public CustomerRegistry() {
        customers.put(123456789, new CustomerDTO(
                "Alice Svensson", 123456789, "alice@example.com",
                "Trek", "Powerfly 5", "SN-2024-XR7"
        ));
    }

    /**
     * Searches for a customer by phone number.
     *
     * @param phoneNumber The customer's phone number to search for.
     * @return The found CustomerInfo, or null if no customer has that phone number.
     */
    public CustomerDTO findCustomer(int phoneNumber) {
        return customers.get(phoneNumber);
    }
}
