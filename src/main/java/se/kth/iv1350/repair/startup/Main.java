package se.kth.iv1350.repair.startup;

import se.kth.iv1350.repair.controller.RepairController;
import se.kth.iv1350.repair.integration.RegistryCreator;
import se.kth.iv1350.repair.view.RepairOrderView;
import se.kth.iv1350.repair.view.View;

/**
 * Starts the Repair Electric Bike application by creating and wiring
 * all top-level objects, then handing control to the view.
 */
public class Main {

    /**
     * The application entry point.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        RegistryCreator registryCreator = new RegistryCreator();
        RepairOrderView repairOrderView = new RepairOrderView();
        RepairController controller = new RepairController(registryCreator, repairOrderView);
        new View(controller);
    }
}
