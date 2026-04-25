package se.kth.iv1350.repair.model;

import java.time.LocalDate;

/**
 * Represents a repair order containing customer info, bike details,
 * problem description, diagnostic report, and current state.
 */
public class RepairOrder {
    private final String orderId;
    private final CustomerInfo customerInfo;
    private final String problemDescription;
    private final LocalDate date;
    private DiagnosticReport diagnosticReport;
    private RepairOrderState state;

    /**
     * Creates a newly created repair order.
     *
     * @param orderId            A unique identifier for this order.
     * @param customerInfo       The customer and bike information.
     * @param problemDescription The customer's description of the problem.
     */
    public RepairOrder(String orderId, CustomerInfo customerInfo, String problemDescription) {
        this.orderId = orderId;
        this.customerInfo = customerInfo;
        this.problemDescription = problemDescription;
        this.date = LocalDate.now();
        this.state = RepairOrderState.NEWLY_CREATED;
    }

    /**
     * Adds a diagnostic report and moves the order to READY_FOR_APPROVAL state.
     *
     * @param report The diagnostic report from the technician.
     */
    public void addDiagnosticReport(DiagnosticReport report) {
        this.diagnosticReport = report;
        this.state = RepairOrderState.READY_FOR_APPROVAL;
    }

    /**
     * Accepts the repair order, changing state to ACCEPTED.
     */
    public void accept() {
        this.state = RepairOrderState.ACCEPTED;
    }

    /**
     * Rejects the repair order, changing state to REJECTED.
     */
    public void reject() {
        this.state = RepairOrderState.REJECTED;
    }

    /** @return The unique identifier of this repair order. */
    public String getOrderId() {
        return orderId;
    }

    /** @return The customer and bike information for this order. */
    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    /** @return The customer's description of the problem. */
    public String getProblemDescription() {
        return problemDescription;
    }

    /** @return The date this repair order was created. */
    public LocalDate getDate() {
        return date;
    }

    /** @return The diagnostic report, or null if not yet added. */
    public DiagnosticReport getDiagnosticReport() {
        return diagnosticReport;
    }

    /** @return The current state of this repair order. */
    public RepairOrderState getState() {
        return state;
    }

    /** @return A formatted string with all repair order data, suitable for printing. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPAIR ORDER ").append(orderId).append(" ===\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append(customerInfo).append("\n");
        sb.append("Problem: ").append(problemDescription).append("\n");
        sb.append("State: ").append(state).append("\n");
        if (diagnosticReport != null) {
            sb.append("--- Diagnostic Report ---\n").append(diagnosticReport).append("\n");
            sb.append("Estimated completion: ").append(date.plusDays(7)).append("\n");
        }
        sb.append("=========================");
        return sb.toString();
    }
}
