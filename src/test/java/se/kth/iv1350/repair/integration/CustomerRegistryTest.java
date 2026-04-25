package se.kth.iv1350.repair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repair.model.CustomerInfo;

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
    public void testFindKnownCustomerReturnsCustomerInfo() {
        CustomerInfo result = registry.findCustomer(123456789);
        assertNotNull(result, "A known phone number should return a CustomerInfo object");
    }

    @Test
    public void testFindUnknownCustomerReturnsNull() {
        CustomerInfo result = registry.findCustomer(000000000);
        assertNull(result, "An unknown phone number should return null");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectName() {
        CustomerInfo result = registry.findCustomer(123456789);
        assertEquals("Alice Svensson", result.getName(), "Name should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectPhoneNumber() {
        CustomerInfo result = registry.findCustomer(123456789);
        assertEquals(123456789, result.getPhoneNumber(), "Phone number should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectEmail() {
        CustomerInfo result = registry.findCustomer(123456789);
        assertEquals("alice@example.com", result.getEmail(), "Email should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeBrand() {
        CustomerInfo result = registry.findCustomer(123456789);
        assertEquals("Trek", result.getBikeBrand(), "Bike brand should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeModel() {
        CustomerInfo result = registry.findCustomer(123456789);
        assertEquals("Powerfly 5", result.getBikeModel(), "Bike model should match");
    }

    @Test
    public void testFindKnownCustomerReturnsCorrectBikeSerialNumber() {
        CustomerInfo result = registry.findCustomer(123456789);
        assertEquals("SN-2024-XR7", result.getBikeSerialNumber(), "Serial number should match");
    }
}
