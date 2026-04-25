package se.kth.iv1350.repair.model;

/**
 * Represents a single proposed repair task with an associated cost.
 */
public class RepairTask {
    private final String description;
    private final double cost;

    /**
     * Creates a new repair task.
     *
     * @param description A description of the repair work to be done.
     * @param cost        The estimated cost of this repair task.
     */
    public RepairTask(String description, double cost) {
        this.description = description;
        this.cost = cost;
    }

    /** @return The description of this repair task. */
    public String getDescription() {
        return description;
    }

    /** @return The cost of this repair task. */
    public double getCost() {
        return cost;
    }

    /** @return A formatted string with task description and cost. */
    @Override
    public String toString() {
        return description + " - Cost: " + cost + " SEK";
    }
}
