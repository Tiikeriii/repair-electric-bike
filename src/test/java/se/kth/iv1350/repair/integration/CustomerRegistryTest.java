package se.kth.iv1350.repair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repair.model.CustomerDTO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CustomerRegistry class.
 */
public class CustomerRegistryTest {
    private CustomerRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = new CustomerRegistry();
    }

    @Test
    public void testFindKnownCustomerReturnsNonNull() throws CustomerNotFoundException, DatabaseFailureException {
        CustomerDTO result = registry.findCustomer(123456789);
        assertNotNull(result, "A known phone number should return a CustomerDTO");
    }

    @Test
    public void testFindUnknownCustomerThrowsException() {
        CustomerRegistry registry = new CustomerRegistry();
        assertThrows(CustomerNotFoundException.class, () -> {
            registry.findCustomer(999999999);
        }, "Should throw CustomerNotFoundException for unknown phone number");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectName() throws CustomerNotFoundException, DatabaseFailureException {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("Alice Svensson", result.getName(), "Name should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectPhoneNumber() throws CustomerNotFoundException, DatabaseFailureException {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals(123456789, result.getPhoneNumber(), "Phone number should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectEmail() throws CustomerNotFoundException, DatabaseFailureException {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("alice@example.com", result.getEmail(), "Email should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeBrand() throws CustomerNotFoundException, DatabaseFailureException {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("Trek", result.getBikeBrand(), "Bike brand should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeModel() throws CustomerNotFoundException, DatabaseFailureException {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("Powerfly 5", result.getBikeModel(), "Bike model should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeSerialNumber() throws CustomerNotFoundException, DatabaseFailureException {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("SN-2024-XR7", result.getBikeSerialNumber(), "Serial number should match");
    }
}
