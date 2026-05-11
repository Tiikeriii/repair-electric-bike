package se.kth.iv1350.repair.integration;

/**
 * Responsible for printing repair orders. In a real system this class would
 * communicate with a physical printer. Here, output goes to System.out.
 */
public class Printer {

    /**
     * Prints a repair order to System.out. The printout contains all repair
     * order data including estimated completion date.
     *
     * @param repairOrderDTO The repair order data to print.
     */
    public void printRepairOrder(String formattedRepairOrder) {
        System.out.println("\n[PRINTER OUTPUT]");
        System.out.println(formattedRepairOrder);
        System.out.println("[END OF PRINTOUT]\n");
    }
}
