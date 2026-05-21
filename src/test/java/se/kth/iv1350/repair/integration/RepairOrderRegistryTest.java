package se.kth.iv1350.repair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repair.model.Customer;
import se.kth.iv1350.repair.model.RepairOrder;
import se.kth.iv1350.repair.model.RepairOrderDTO;
import se.kth.iv1350.repair.model.RepairOrderObserver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;

/**
 * Unit tests for the RepairOrderRegistry class.
 */
public class RepairOrderRegistryTest {
    private RepairOrderRegistry registry;
    private RepairOrder orderA;
    private RepairOrder orderB;
    private TestObserver testObserver;
    private List<RepairOrderObserver> observers;

    @BeforeEach
    public void setUp() {
        observers = new ArrayList<>();
        observers.add(testObserver);
        registry = RepairOrderRegistry.getInstance();
        Customer customer = new Customer("Alice", 123, "a@b.com", "Trek", "FX3", "SN001");
        orderA = new RepairOrder("A", customer, "Problem A", observers);
        orderB = new RepairOrder("B", customer, "Problem B", observers);
    }

    @AfterEach
    public void tearDown() {
        RepairOrderRegistry.clearForTesting();
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        RepairOrderRegistry first = RepairOrderRegistry.getInstance();
        RepairOrderRegistry second = RepairOrderRegistry.getInstance();
        assertSame(first, second,"getInstance() should always return the same RepairOrderRegistry instance");
    }

    @Test
    public void testGetNextReturnsNullWhenEmpty() {
        assertNull(registry.getNextRepairOrder(),
                "Should return null when no orders are in the queue");
    }

    @Test
    public void testFifoOrdering() {
        registry.storeRepairOrder(orderA);
        registry.storeRepairOrder(orderB);
        RepairOrder first = registry.getNextRepairOrder();
        assertEquals("A", first.getOrderId(),
                "First order stored should be the first retrieved");
    }

    @Test
    public void testOrderIsRemovedFromQueueAfterRetrieval() {
        registry.storeRepairOrder(orderA);
        registry.getNextRepairOrder();
        assertNull(registry.getNextRepairOrder(),
                "Queue should be empty after the only order is retrieved");
    }

    @Test
    public void testOrdersAreNeverDeletedFromAllOrders() {
        registry.storeRepairOrder(orderA);
        registry.storeRepairOrder(orderB);
        registry.getNextRepairOrder();
        assertEquals(2, registry.getAllOrders().size(),
                "All orders should remain in the registry even after retrieval from queue");
    }

    @Test
    public void testGetAllOrdersIsUnmodifiable() {
        registry.storeRepairOrder(orderA);
        Customer c = new Customer("Bob", 999, "b@b.com", "Giant", "E+", "SN999");
        RepairOrder orderC = new RepairOrder("C", c, "Problem C", observers);
        assertThrows(UnsupportedOperationException.class,
                () -> registry.getAllOrders().add(orderC),
                "The all-orders list should be unmodifiable");
    }

    public class TestObserver implements RepairOrderObserver {
        private RepairOrderDTO lastUpdate;
        private int updateCount = 0;

        @Override
        public void repairOrderUpdated(RepairOrderDTO repairOrder) {
            this.lastUpdate = repairOrder;
            this.updateCount++;
        }

        /** @return The last RepairOrderDTO received. */
        public RepairOrderDTO getLastUpdate() {
            return lastUpdate;
        }

        /** @return The number of times this observer was notified. */
        public int getUpdateCount() {
            return updateCount;
        }
    }
}
