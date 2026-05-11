package se.kth.iv1350.repair.model;

import java.util.List;

/**
 * Data transfer object for passing repair order data from the controller to the view.
 * Contains only the data needed for display, with no business logic.
 */
public class RepairOrderDTO {
    private final String orderId;
    private final CustomerDTO customerInfo;
    private final String problemDescription;
    private final String date;
    private final String state;
    private final String diagnosticFindings;
    private final List<String> repairTaskDescriptions;
    private final List<Double> repairTaskCosts;
    private final double totalCost;
    private final String estimatedCompletion;

    /**
     * Creates a RepairOrderDTO without a diagnostic report.
     *
     * @param orderId            The unique order identifier.
     * @param customerInfo       The customer and bike information.
     * @param problemDescription The customer's problem description.
     * @param date               The date the order was created.
     * @param state              The current state of the order.
     */
    public RepairOrderDTO(String orderId, CustomerDTO customerInfo,
                          String problemDescription, String date, String state) {
        this(orderId, customerInfo, problemDescription, date, state,
                null, null, null, 0, null);
    }

    /**
     * Creates a RepairOrderDTO with full diagnostic report information.
     *
     * @param orderId                The unique order identifier.
     * @param customerInfo           The customer and bike information.
     * @param problemDescription     The customer's problem description.
     * @param date                   The date the order was created.
     * @param state                  The current state of the order.
     * @param diagnosticFindings     The technician's findings, or null if not yet set.
     * @param repairTaskDescriptions Descriptions of all repair tasks, or null if not yet set.
     * @param repairTaskCosts        Costs of all repair tasks, or null if not yet set.
     * @param totalCost              The total cost of all repair tasks.
     * @param estimatedCompletion    The estimated completion date, or null if not yet set.
     */
    public RepairOrderDTO(String orderId, CustomerDTO customerInfo,
                          String problemDescription, String date, String state,
                          String diagnosticFindings, List<String> repairTaskDescriptions,
                          List<Double> repairTaskCosts, double totalCost,
                          String estimatedCompletion) {
        this.orderId = orderId;
        this.customerInfo = customerInfo;
        this.problemDescription = problemDescription;
        this.date = date;
        this.state = state;
        this.diagnosticFindings = diagnosticFindings;
        this.repairTaskDescriptions = repairTaskDescriptions;
        this.repairTaskCosts = repairTaskCosts;
        this.totalCost = totalCost;
        this.estimatedCompletion = estimatedCompletion;
    }

    /** @return The unique order identifier. */
    public String getOrderId() {
        return orderId;
    }

    /** @return The customer and bike information. */
    public CustomerDTO getCustomerInfo() {
        return customerInfo;
    }

    /** @return The customer's problem description. */
    public String getProblemDescription() {
        return problemDescription;
    }

    /** @return The date the order was created. */
    public String getDate() {
        return date;
    }

    /** @return The current state of the order. */
    public String getState() {
        return state;
    }

    /** @return The technician's diagnostic findings, or null if not yet set. */
    public String getDiagnosticFindings() { 
    return diagnosticFindings;
}

    /** @return Descriptions of all proposed repair tasks, or null if not yet set. */
    public List<String> getRepairTaskDescriptions() {
        return repairTaskDescriptions;
    }

    /** @return Costs of all proposed repair tasks, or null if not yet set. */
    public List<Double> getRepairTaskCosts() {
        return repairTaskCosts;
    }

    /** @return The total cost of all proposed repair tasks. */
    public double getTotalCost() {
        return totalCost;
    }

    /** @return The estimated completion date, or null if not yet set. */
    public String getEstimatedCompletion() {
        return estimatedCompletion;
    }

    /** @return True if this order has a diagnostic report. */
    public boolean hasDiagnosticReport() {
        return diagnosticFindings != null;
    }
}
