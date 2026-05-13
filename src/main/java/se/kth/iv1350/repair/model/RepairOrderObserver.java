package se.kth.iv1350.repair.model;

/**
 * Observer interface for receiving repair order updates.
 */
public interface RepairOrderObserver {
    /**
     * Called when a repair order has been updated.
     *
     * @param repairOrder A DTO representing the updated repair order.
     */
    void repairOrderUpdated(RepairOrderDTO repairOrder);
}
