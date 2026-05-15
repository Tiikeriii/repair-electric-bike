package se.kth.iv1350.repair.view;

import java.util.List;

import se.kth.iv1350.repair.model.CustomerDTO;
import se.kth.iv1350.repair.model.RepairOrderDTO;
import se.kth.iv1350.repair.model.RepairOrderObserver;

/**
 * Displays repair order updates to System.out whenever a repair order changes state.
 */
public class RepairOrderView implements RepairOrderObserver {

    /**
     * Called when a repair order has been updated. Prints the updated
     * repair order to System.out.
     *
     * @param repairOrderDTO A DTO representing the updated repair order.
     */
    @Override
    public void repairOrderUpdated(RepairOrderDTO repairOrderDTO) {
        System.out.println(formatRepairOrder(repairOrderDTO));
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
