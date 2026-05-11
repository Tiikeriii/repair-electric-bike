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
    public void testFindKnownCustomerReturnsNonNull() {
        CustomerDTO result = registry.findCustomer(123456789);
        assertNotNull(result, "A known phone number should return a CustomerDTO");
    }

    @Test
    public void testFindUnknownCustomerReturnsNull() {
        CustomerDTO result = registry.findCustomer(000000000);
        assertNull(result, "An unknown phone number should return null");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectName() {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("Alice Svensson", result.getName(), "Name should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectPhoneNumber() {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals(123456789, result.getPhoneNumber(), "Phone number should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectEmail() {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("alice@example.com", result.getEmail(), "Email should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeBrand() {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("Trek", result.getBikeBrand(), "Bike brand should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeModel() {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("Powerfly 5", result.getBikeModel(), "Bike model should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeSerialNumber() {
        CustomerDTO result = registry.findCustomer(123456789);
        assertEquals("SN-2024-XR7", result.getBikeSerialNumber(), "Serial number should match");
    }
}
