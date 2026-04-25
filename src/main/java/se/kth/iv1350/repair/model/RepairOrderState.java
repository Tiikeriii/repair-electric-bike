package se.kth.iv1350.repair.model;

/**
 * Represents all possible states of a repair order.
 */
public enum RepairOrderState {
    NEWLY_CREATED,
    READY_FOR_APPROVAL,
    REJECTED,
    ACCEPTED,
    COMPLETED,
    PAID,
}
