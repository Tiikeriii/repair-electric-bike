package se.kth.iv1350.repair.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a repair order containing customer info, bike details,
 * problem description, diagnostic report, and current state.
 */
public class RepairOrder {
    private final String orderId;
    private final Customer customer;
    private final String problemDescription;
    private final LocalDate date;
    private DiagnosticReport diagnosticReport;
    private RepairOrderState state;
    private final List<RepairOrderObserver> observers = new ArrayList<>();

    /**
     * Creates a newly created repair order.
     *
     * @param orderId            A unique identifier for this order.
     * @param customer           The customer and bike information.
     * @param problemDescription The customer's description of the problem.
     * @param observersToAdd A list of all observers
     */
    public RepairOrder(String orderId, Customer customer, String problemDescription) {
        this.orderId = orderId;
        this.customer = customer;
        this.problemDescription = problemDescription;
        this.date = LocalDate.now();
        this.state = RepairOrderState.NEWLY_CREATED;
    }

    public void markCreated() {
        notifyObservers();
    }

    /**
     *  Adds an observer to the list of observers
     * 
     * @param observer The observer to add
     */
    public void addObserver(RepairOrderObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    /**
     * Notifies all observers that the repair order has been updated
     * 
     */
    private void notifyObservers() {
        RepairOrderDTO dto = toDTO();
        for (RepairOrderObserver observer : observers) {
            observer.repairOrderUpdated(dto);
        }
    }

    /**
     * Adds a diagnostic report and moves the order to READY_FOR_APPROVAL state.
     *
     * @param report The diagnostic report from the technician.
     */
    public void addDiagnosticReport(DiagnosticReport report) {
        this.diagnosticReport = report;
        this.state = RepairOrderState.READY_FOR_APPROVAL;
        notifyObservers();
    }

    /**
     * Accepts the repair order, changing state to ACCEPTED.
     */
    public void accept() {
        this.state = RepairOrderState.ACCEPTED;
        notifyObservers();
    }

    /**
     * Rejects the repair order, changing state to REJECTED.
     */
    public void reject() {
        this.state = RepairOrderState.REJECTED;
        notifyObservers();
    }

    /** @return The current state of this repair order. */
    public RepairOrderState getState() {
        return state;
    }

    /** @return The unique identifier of this repair order. */
    public String getOrderId() {
        return orderId;
    }

    /** @return The diagnostic report, or null if not yet added. */
    public DiagnosticReport getDiagnosticReport() {
        return diagnosticReport;
    }

    /** @return The date this repair order was created. */
    public LocalDate getDate() {
        return date;
    }

    /** @return The customer for this repair order. */
    public Customer getCustomer() {
        return customer;
    }

    /** @return The customer's description of the problem. */
    public String getProblemDescription() {
        return problemDescription;
    }

        /**
     * Creates a RepairOrderDTO containing all data needed by the view to
     * display this repair order.
     *
     * @return A RepairOrderDTO representing the current state of this order.
     */
    public RepairOrderDTO toDTO() {
        CustomerDTO customerDTO = customer.toDTO();
        if (diagnosticReport == null) {
            return new RepairOrderDTO(
                    orderId,
                    customerDTO,
                    problemDescription,
                    date.toString(),
                    state.toString()
            );
        }
        List<String> taskDescriptions = new ArrayList<>();
        List<Double> taskCosts = new ArrayList<>();
        for (RepairTask task : diagnosticReport.getRepairTasks()) {
            taskDescriptions.add(task.getDescription());
            taskCosts.add(task.getCost());
        }
        return new RepairOrderDTO(
                orderId,
                customerDTO,
                problemDescription,
                date.toString(),
                state.toString(),
                diagnosticReport.getFindings(),
                taskDescriptions,
                taskCosts,
                diagnosticReport.getTotalCost(),
                date.plusDays(7).toString()
        );
    }
}