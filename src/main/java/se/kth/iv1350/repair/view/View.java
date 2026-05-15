package se.kth.iv1350.repair.view;

import se.kth.iv1350.repair.controller.RepairController;
import se.kth.iv1350.repair.integration.CustomerNotFoundException;
import se.kth.iv1350.repair.integration.DatabaseFailureException;
import se.kth.iv1350.repair.model.CustomerDTO;
import se.kth.iv1350.repair.model.RepairOrderDTO;
import se.kth.iv1350.repair.model.RepairTaskDTO;

import java.util.Arrays;
import java.util.List;

/**
 * The application's view. Since there is no real UI, all calls are hard-coded
 * to simulate the basic flow of the use case. Everything returned by the
 * controller is printed to System.out. All formatting of output is done here.
 * Repair order updates are handled automatically by the observer pattern.
 */
public class View {
    private final RepairController controller;

    /**
     * Creates a new view and runs the hard-coded simulation of the basic flow.
     *
     * @param controller The controller to use for all operations.
     */
    public View(RepairController controller) {
        this.controller = controller;
        runBasicFlow();
    }

    /**
     * Simulates the complete basic flow: customer lookup, repair order creation,
     * technician diagnostic, customer acceptance, and printout.
     */
    private void runBasicFlow() {
        System.out.println("=== Repair Electric Bike  Basic Flow Simulation ===\n");

        // Steps 2-4: Receptionist enters customer's phone number (entering 69 will cause the "database" to be unavailable)
        int phoneNumber = 123456789;
        System.out.println("[Receptionist] Customer phone number entered: " + phoneNumber);

        // Steps 5-6: System searches and presents customer details
        CustomerDTO customerInfo = null;
        try {
            customerInfo = controller.findCustomer(phoneNumber);
        } catch (CustomerNotFoundException e) {
            System.out.println("[System] No customer found with that phone number, please try again.");
            return;
        } catch (DatabaseFailureException e) {
            System.out.println("[System] The system is currently unavailable, please contact support.");
            controller.logException("Database unavailable for customer registry lookup", e);
            return;
        }
        System.out.println("[System] Customer found:");
        System.out.println(formatCustomer(customerInfo));
        System.out.println();

        // Steps 7-8: Customer confirms details (hard-coded as confirmed)
        System.out.println("[Receptionist] Are the details correct? [Customer confirms: Yes]\n");

        // Steps 9-11: Receptionist enters problem description; system creates repair order
        String problemDescription = "Bike loses power after approximately 5 km of riding.";
        System.out.println("[Receptionist] Problem description entered: " + problemDescription);
        RepairOrderDTO repairOrder = controller.createRepairOrder(customerInfo, problemDescription);
        System.out.println("[System] Repair order created: " + repairOrder.getOrderId());
        System.out.println("[System] Technician and receptionist have been notified.\n");

        // Step 12: Customer waits
        System.out.println("[Customer] Waiting while technician inspects the bike...");
        System.out.println("[System] Technician has been automatically notified of the new repair order.\n");

        // Steps 15-17: Technician performs diagnostic and enters report
        List<RepairTaskDTO> tasks = Arrays.asList(
                new RepairTaskDTO("Replace battery management system (BMS)", 1500.0),
                new RepairTaskDTO("Clean and lubricate drivetrain", 350.0)
        );
        String findings = "Battery cells show degradation. BMS fault code detected. Drivetrain needs service.";
        controller.addDiagnosticReport(findings, tasks);
        System.out.println("[System] Diagnostic report added. Receptionist and technician notified automatically.\n");

        // Step 18: Receptionist informs customer
        System.out.println("[Receptionist] Informing customer about diagnostic report and cost.\n");

        // Steps 19-20: Customer accepts (hard-coded as accepted)
        System.out.println("[Customer] Proposed repair tasks and cost accepted.\n");

        // Steps 21-22: Accept triggers observer notification and printer
        repairOrder = controller.acceptRepairOrder();
        System.out.println("[System] State: " + repairOrder.getState());
        controller.printRepairOrder(formatRepairOrder(repairOrder));
        System.out.println("[Receptionist] Printed repair order given to customer.\n");

        // Step 23
        System.out.println("[Customer] Leaves the workshop.");
        System.out.println("\n=== End of simulation ===");
    }

    /**
     * Formats customer and bike information for display.
     *
     * @param customer The customer data to format.
     * @return A formatted string with customer and bike details.
     */
    private String formatCustomer(CustomerDTO customer) {
        return "Customer: " + customer.getName()
                + " | Phone: " + customer.getPhoneNumber()
                + " | Email: " + customer.getEmail()
                + "\nBike: " + customer.getBikeBrand()
                + " " + customer.getBikeModel()
                + " | Serial: " + customer.getBikeSerialNumber();
    }

    /**
     * Formats a repair order DTO for display.
     *
     * @param order The repair order data to format.
     * @return A formatted string with all repair order details.
     */
    private String formatRepairOrder(RepairOrderDTO order) {
        CustomerDTO customer = order.getCustomerInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPAIR ORDER ").append(order.getOrderId()).append(" ===\n");
        sb.append("Date: ").append(order.getDate()).append("\n");
        sb.append("Customer: ").append(customer.getName())
          .append(" | Phone: ").append(customer.getPhoneNumber())
          .append(" | Email: ").append(customer.getEmail()).append("\n");
        sb.append("Bike: ").append(customer.getBikeBrand())
          .append(" ").append(customer.getBikeModel())
          .append(" | Serial: ").append(customer.getBikeSerialNumber()).append("\n");
        sb.append("Problem: ").append(order.getProblemDescription()).append("\n");
        sb.append("State: ").append(order.getState()).append("\n");
        if (order.hasDiagnosticReport()) {
            sb.append(formatDiagnosticReport(order)).append("\n");
            sb.append("Estimated completion: ").append(order.getEstimatedCompletion()).append("\n");
        }
        sb.append("=========================");
        return sb.toString();
    }

    /**
     * Formats the diagnostic report section of a repair order for display.
     *
     * @param order The repair order containing the diagnostic report data.
     * @return A formatted string with findings, tasks and total cost.
     */
    private String formatDiagnosticReport(RepairOrderDTO order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Findings: ").append(order.getDiagnosticFindings()).append("\n");
        List<String> descriptions = order.getRepairTaskDescriptions();
        List<Double> costs = order.getRepairTaskCosts();
        for (int i = 0; i < descriptions.size(); i++) {
            sb.append("  - ").append(descriptions.get(i))
              .append(" - Cost: ").append(costs.get(i)).append(" SEK\n");
        }
        sb.append("Total cost: ").append(order.getTotalCost()).append(" SEK");
        return sb.toString();
    }
}