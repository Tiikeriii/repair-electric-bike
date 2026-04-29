package se.kth.iv1350.repair.view;

import se.kth.iv1350.repair.controller.RepairController;
import se.kth.iv1350.repair.model.CustomerDTO;
import se.kth.iv1350.repair.model.DiagnosticReport;
import se.kth.iv1350.repair.model.RepairOrder;
import se.kth.iv1350.repair.model.RepairTask;

import java.util.Arrays;
import java.util.List;

/**
 * The application's view. Since there is no real UI, all calls are hard-coded
 * to simulate the basic flow of the use case. Everything returned by the
 * controller is printed to System.out.
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
        System.out.println("=== Repair Electric Bike — Basic Flow Simulation ===\n");

        // Steps 2–4: Receptionist enters customer's phone number
        int phoneNumber = 123456789;
        System.out.println("[Receptionist] Customer phone number entered: " + phoneNumber);

        // Steps 5–6: System searches and presents customer details
        CustomerDTO customerInfo = controller.findCustomer(phoneNumber);
        if (customerInfo == null) {
            System.out.println("[System] Phone number not found in customer registry.");
            return;
        }
        System.out.println("[System] Customer found:\n" + customerInfo + "\n");

        // Steps 7–8: Customer confirms details (hard-coded as confirmed)
        System.out.println("[Receptionist] Are the details correct? [Customer confirms: Yes]\n");

        // Steps 9–11: Receptionist enters problem description; system creates repair order
        String problemDescription = "Bike loses power after approximately 5 km of riding.";
        System.out.println("[Receptionist] Problem description entered: " + problemDescription);
        RepairOrder repairOrder = controller.createRepairOrder(customerInfo, problemDescription);
        System.out.println("[System] Repair order created: " + repairOrder.getOrderId() + "\n");

        // Step 12: Customer waits
        System.out.println("[Customer] Waiting while technician inspects the bike...\n");

        // Steps 13–14: Technician asks system for repair order
        repairOrder = controller.getRepairOrder();
        System.out.println("[Technician] Repair order retrieved:");
        System.out.println(repairOrder + "\n");

        // Steps 15–17: Technician performs diagnostic and enters report
        List<RepairTask> tasks = Arrays.asList(
                new RepairTask("Replace battery management system (BMS)", 1500.0),
                new RepairTask("Clean and lubricate drivetrain", 350.0)
        );
        DiagnosticReport report = new DiagnosticReport(
                "Battery cells show degradation. BMS fault code detected. Drivetrain needs service.",
                tasks
        );
        repairOrder = controller.addDiagnosticReport(report);
        System.out.println("[System] Repair order updated with diagnostic report.\n");

        // Step 18: Receptionist informs customer
        System.out.println("[Receptionist] Informing customer about diagnostic report:");
        System.out.println(repairOrder.getDiagnosticReport());
        System.out.println();

        // Step 19–20: Customer accepts (hard-coded as accepted)
        System.out.println("[Customer] Proposed repair tasks and cost accepted.\n");
        repairOrder = controller.acceptRepairOrder();

        // Step 21–22: System prints repair order, receptionist gives it to customer
        System.out.println("[System] State: " + repairOrder.getState());
        System.out.println("[Receptionist] Printed repair order given to customer.\n");

        // Step 23
        System.out.println("[Customer] Leaves the workshop.");
        System.out.println("\n=== End of simulation ===");
    }
}
