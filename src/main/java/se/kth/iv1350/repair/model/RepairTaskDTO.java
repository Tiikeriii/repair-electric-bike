package se.kth.iv1350.repair.model;

/**
 * Data transfer object for passing repair task data from the view to the controller.
 * The view creates instances of this class instead of the model class RepairTask.
 */
public class RepairTaskDTO {
    private final String description;
    private final double cost;

    /**
     * Creates a new RepairTaskDTO with the given description and cost.
     *
     * @param description A description of the repair work to be done.
     * @param cost        The estimated cost of this repair task.
     */
    public RepairTaskDTO(String description, double cost) {
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
}
