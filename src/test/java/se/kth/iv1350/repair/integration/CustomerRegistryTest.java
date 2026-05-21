package se.kth.iv1350.repair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repair.model.CustomerDTO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;

/**
 * Unit tests for the CustomerRegistry class.
 */
public class CustomerRegistryTest {
    private CustomerRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = CustomerRegistry.getInstance();
    }

    @AfterEach
    public void tearDown() {
        CustomerRegistry.clearForTesting();
    }
    
    @Test
    public void testGetInstanceReturnsSameInstance() {
        CustomerRegistry first = CustomerRegistry.getInstance();
        CustomerRegistry second = CustomerRegistry.getInstance();
        assertSame(first, second,"getInstance() should always return the same CustomerRegistry instance");
}
    @Test
    public void testFindKnownCustomerReturnsNonNull() throws CustomerNotFoundException, DatabaseFailureException {
        CustomerDTO result = registry.findCustomer(123456789);
        assertNotNull(result, "A known phone number should return a CustomerDTO");
    }

    @Test
    public void testFindUnknownCustomerThrowsException() {
        CustomerRegistry registry = CustomerRegistry.getInstance();
        assertThrows(CustomerNotFoundException.class, () -> {
            registry.findCustomer(999999999);
        }, "Should throw CustomerNotFoundException for unknown phone number");
    }

    @Test
    public void testDatabaseFailureThrowsException() {
    CustomerRegistry registry = CustomerRegistry.getInstance();
    assertThrows(DatabaseFailureException.class, () -> {
        registry.findCustomer(69);
    }, "Should throw DatabaseFailureException when database is unavailable");
}

@Test
    public void testCustomerNotFoundExceptionContainsPhoneNumber() {
    CustomerRegistry registry = CustomerRegistry.getInstance();
    CustomerNotFoundException exception = assertThrows(
            CustomerNotFoundException.class, () -> {
                registry.findCustomer(999999999);
            }, "Should throw CustomerNotFoundException for unknown phone number");
    
    assertTrue(exception.getMessage().contains("999999999"),
            "Exception message should contain the phone number that was searched");
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
