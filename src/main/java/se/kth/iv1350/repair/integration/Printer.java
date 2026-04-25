package se.kth.iv1350.repair.integration;

import se.kth.iv1350.repair.model.RepairOrder;

/**
 * Responsible for printing repair orders.
 */
public class Printer {

    /**
     * Prints a repair order to System.out. The printout contains all repair
     * order data.
     *
     * @param repairOrder The repair order to print.
     */
    public void printRepairOrder(RepairOrder repairOrder) {
        System.out.println("\n[PRINTER OUTPUT]");
        System.out.println(repairOrder);
        System.out.println("[END OF PRINTOUT]\n");
    }
}
