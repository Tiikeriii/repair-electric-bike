package se.kth.iv1350.repair.integration;

/**
 * Creates and provides access to all registry objects. This ensures that
 * exactly one instance of each registry exists throughout the application.
 */
public class RegistryCreator {
    private final CustomerRegistry customerRegistry;
    private final RepairOrderRegistry repairOrderRegistry;
    private final Printer printer;

    /**
     * Creates a new instance and instantiates all registries and external system handlers.
     */
    public RegistryCreator() {
        this.customerRegistry = new CustomerRegistry();
        this.repairOrderRegistry = new RepairOrderRegistry();
        this.printer = new Printer();
    }

    /** @return The customer registry. */
    public CustomerRegistry getCustomerRegistry() {
        return customerRegistry;
    }

    /** @return The repair order registry. */
    public RepairOrderRegistry getRepairOrderRegistry() {
        return repairOrderRegistry;
    }

    /** @return The printer. */
    public Printer getPrinter() {
        return printer;
    }
}
