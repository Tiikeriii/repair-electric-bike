package se.kth.iv1350.repair.controller;

import se.kth.iv1350.repair.integration.CustomerNotFoundException;
import se.kth.iv1350.repair.integration.CustomerRegistry;
import se.kth.iv1350.repair.integration.DatabaseFailureException;
import se.kth.iv1350.repair.integration.Printer;
import se.kth.iv1350.repair.integration.RegistryCreator;
import se.kth.iv1350.repair.integration.ErrorLogger;
import se.kth.iv1350.repair.integration.RepairOrderLogger;
import se.kth.iv1350.repair.integration.RepairOrderRegistry;
import se.kth.iv1350.repair.model.Customer;
import se.kth.iv1350.repair.model.CustomerDTO;
import se.kth.iv1350.repair.model.DiagnosticReport;
import se.kth.iv1350.repair.model.RepairOrder;
import se.kth.iv1350.repair.model.RepairOrderDTO;
import se.kth.iv1350.repair.model.RepairTask;
import se.kth.iv1350.repair.model.RepairTaskDTO;
import se.kth.iv1350.repair.view.RepairOrderView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The application's only controller. All calls from the view go through this class.
 * It coordinates the model and integration layers to implement the use cases.
 */
public class RepairController {
    private final CustomerRegistry customerRegistry;
    private final RepairOrderRegistry repairOrderRegistry;
    private final RepairOrderLogger repairOrderLogger;
    private final ErrorLogger errorLogger;
    private final RepairOrderView repairOrderView;
    private final Printer printer;
    private RepairOrder currentRepairOrder;

    /**
     * Creates a new controller instance.
     *
     * @param registryCreator Provides access to all registries and external systems.
     */
    public RepairController(RegistryCreator registryCreator, RepairOrderView repairOrderView) {
        this.customerRegistry = registryCreator.getCustomerRegistry();
        this.repairOrderRegistry = registryCreator.getRepairOrderRegistry();
        this.repairOrderLogger = registryCreator.getRepairOrderLogger();
        this.errorLogger = registryCreator.getErrorLogger();
        this.repairOrderView = repairOrderView;
        this.printer = registryCreator.getPrinter();
    }

    /**
     * Searches for a customer in the customer registry by phone number.
     *
     * @param phoneNumber The customer's phone number.
     * @return A CustomerDTO if found, or null if the phone number is unknown.
     */
    public CustomerDTO findCustomer(int phoneNumber) throws CustomerNotFoundException, DatabaseFailureException {
        return customerRegistry.findCustomer(phoneNumber);
    }

    /**
     * Creates a new repair order for the given customer with the given problem description.
     * The order is stored in the repair order registry.
     *
     * @param customerDTO        The customer's information as a DTO from the view.
     * @param problemDescription The customer's description of the problem.
     * @return A RepairOrderDTO representing the newly created repair order.
     */
    public RepairOrderDTO createRepairOrder(CustomerDTO customerDTO, String problemDescription) {
        Customer customer = new Customer(
                customerDTO.getName(),
                customerDTO.getPhoneNumber(),
                customerDTO.getEmail(),
                customerDTO.getBikeBrand(),
                customerDTO.getBikeModel(),
                customerDTO.getBikeSerialNumber()
        );
        String orderId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        currentRepairOrder = new RepairOrder(orderId, customer, problemDescription);
        currentRepairOrder.addObserver(repairOrderView);
        currentRepairOrder.addObserver(repairOrderLogger);
        currentRepairOrder.notifyObservers();
        repairOrderRegistry.storeRepairOrder(currentRepairOrder);
        return currentRepairOrder.toDTO();
    }

    /**
     * Retrieves the next repair order from the registry for a technician to process.
     *
     * @return A RepairOrderDTO representing the next repair order, or null if none waiting.
     */
    public RepairOrderDTO getRepairOrder() {
        currentRepairOrder = repairOrderRegistry.getNextRepairOrder();
        if (currentRepairOrder == null) {
            return null;
        }
        return currentRepairOrder.toDTO();
    }

    /**
     * Adds a diagnostic report and proposed repair tasks to the current repair order.
     *
     * @param findings The technician's findings.
     * @param taskDTOs The list of proposed repair tasks as DTOs from the view.
     * @return A RepairOrderDTO representing the updated repair order.
     */
    public RepairOrderDTO addDiagnosticReport(String findings, List<RepairTaskDTO> taskDTOs) {
        List<RepairTask> repairTasks = new ArrayList<>();
        for (RepairTaskDTO dto : taskDTOs) {
            repairTasks.add(new RepairTask(dto.getDescription(), dto.getCost()));
        }
        DiagnosticReport report = new DiagnosticReport(findings, repairTasks);
        currentRepairOrder.addDiagnosticReport(report);
        return currentRepairOrder.toDTO();
    }

    /**
     * Registers that the customer has accepted the repair order.
     * The repair order is printed after acceptance.
     *
     * @return A RepairOrderDTO representing the accepted repair order.
     */
    public RepairOrderDTO acceptRepairOrder() {
        currentRepairOrder.accept();
        return currentRepairOrder.toDTO();
    }

    /**
     * Registers that the customer has rejected the repair order.
     * The order is kept in the registry but marked as rejected.
     *
     * @return A RepairOrderDTO representing the rejected repair order.
     */
    public RepairOrderDTO rejectRepairOrder() {
        currentRepairOrder.reject();
        return currentRepairOrder.toDTO();
    }

    public void printRepairOrder(String formattedRepairOrder) {
        printer.printRepairOrder(formattedRepairOrder);
    }

    /**
     * Logs an exception
     * 
     * @param message the message about the exception
     * @param e the exception
     */
    public void logException(String message, Exception e) {
        errorLogger.logException(message, e);
    }
}
