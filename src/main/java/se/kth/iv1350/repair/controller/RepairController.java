package se.kth.iv1350.repair.controller;

import se.kth.iv1350.repair.integration.CustomerRegistry;
import se.kth.iv1350.repair.integration.Printer;
import se.kth.iv1350.repair.integration.RegistryCreator;
import se.kth.iv1350.repair.integration.RepairOrderRegistry;
import se.kth.iv1350.repair.model.CustomerInfo;
import se.kth.iv1350.repair.model.DiagnosticReport;
import se.kth.iv1350.repair.model.RepairOrder;

import java.util.UUID;

/**
 * The application's only controller. All calls from the view go through this class.
 * It coordinates the model and integration layers to implement the use cases.
 */
public class RepairController {
    private final CustomerRegistry customerRegistry;
    private final RepairOrderRegistry repairOrderRegistry;
    private final Printer printer;
    private RepairOrder currentRepairOrder;

    /**
     * Creates a new controller instance.
     *
     * @param registryCreator Provides access to all registries and external systems.
     */
    public RepairController(RegistryCreator registryCreator) {
        this.customerRegistry = registryCreator.getCustomerRegistry();
        this.repairOrderRegistry = registryCreator.getRepairOrderRegistry();
        this.printer = registryCreator.getPrinter();
    }

    /**
     * Searches for a customer in the customer registry by phone number.
     *
     * @param phoneNumber The customer's phone number.
     * @return The found CustomerInfo, or null if the phone number is unknown.
     */
    public CustomerInfo findCustomer(int phoneNumber) {
        return customerRegistry.findCustomer(phoneNumber);
    }

    /**
     * Creates a new repair order for the given customer with the given problem description.
     * The order is stored in the repair order registry.
     *
     * @param customerInfo       The customer's information.
     * @param problemDescription The customer's description of the problem.
     * @return The newly created repair order.
     */
    public RepairOrder createRepairOrder(CustomerInfo customerInfo, String problemDescription) {
        String orderId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        currentRepairOrder = new RepairOrder(orderId, customerInfo, problemDescription);
        repairOrderRegistry.storeRepairOrder(currentRepairOrder);
        return currentRepairOrder;
    }

    /**
     * Retrieves the next repair order from the registry for a technician to process.
     *
     * @return The next repair order, or null if there are none waiting.
     */
    public RepairOrder getRepairOrder() {
        currentRepairOrder = repairOrderRegistry.getNextRepairOrder();
        return currentRepairOrder;
    }

    /**
     * Adds a diagnostic report and proposed repair tasks to the current repair order.
     *
     * @param report The diagnostic report produced by the technician.
     * @return The updated repair order.
     */
    public RepairOrder addDiagnosticReport(DiagnosticReport report) {
        currentRepairOrder.addDiagnosticReport(report);
        return currentRepairOrder;
    }

    /**
     * Registers that the customer has accepted the repair order.
     * The repair order is printed after acceptance.
     *
     * @return The accepted repair order.
     */
    public RepairOrder acceptRepairOrder() {
        currentRepairOrder.accept();
        printer.printRepairOrder(currentRepairOrder);
        return currentRepairOrder;
    }

    /**
     * Registers that the customer has rejected the repair order.
     * The order is kept in the registry but marked as rejected.
     *
     * @return The rejected repair order.
     */
    public RepairOrder rejectRepairOrder() {
        currentRepairOrder.reject();
        return currentRepairOrder;
    }
}
