package se.kth.iv1350.repair.integration;

import se.kth.iv1350.repair.model.RepairOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Handles all access to the repair order registry. Repair orders are never deleted.
 * Orders are queued in FIFO order for technicians to process.
 */
public class RepairOrderRegistry {
    private static RepairOrderRegistry instance;
    private final Deque<RepairOrder> queue = new ArrayDeque<>();
    private final List<RepairOrder> allOrders = new ArrayList<>();

    /**
     * Private constructor tto prevent instantiation of multiple instances
     */
    private RepairOrderRegistry() {

    }

    /**
     * Returns the instance for the RepairOrderRegistry
     * 
     * @return The single RepairOrderRegistry instance
     */
    public static RepairOrderRegistry getInstance() {
        if (instance == null) {
            instance = new RepairOrderRegistry();
        }
        return instance;
    }

    /**
     * Resets the singleton instance. Only used for testing purposes.
     */
    public static void resetInstance() {
        instance = null;
    }
    /**
     * Stores a new repair order in the registry and adds it to the processing queue.
     *
     * @param repairOrder The repair order to store.
     */
    public void storeRepairOrder(RepairOrder repairOrder) {
        queue.addLast(repairOrder);
        allOrders.add(repairOrder);
    }

    /**
     * Retrieves and removes the next repair order from the FIFO queue.
     *
     * @return The next repair order, or null if the queue is empty.
     */
    public RepairOrder getNextRepairOrder() {
        return queue.pollFirst();
    }

    /**
     * Returns an unmodifiable view of all repair orders ever created.
     *
     * @return All repair orders in the registry.
     */
    public List<RepairOrder> getAllOrders() {
        return Collections.unmodifiableList(allOrders);
    }
}
