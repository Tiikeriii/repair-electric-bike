package se.kth.iv1350.repair.integration;

/**
 * Creates and provides access to all registry objects. This ensures that
 * exactly one instance of each registry exists throughout the application.
 */
public class RegistryCreator {
    private final CustomerRegistry customerRegistry;
    private final RepairOrderRegistry repairOrderRegistry;
    private final RepairOrderLogger repairOrderLogger;
    private final ErrorLogger errorLogger;
    private final Printer printer;

    /**
     * Creates a new instance and instantiates all registries and external system handlers.
     */
    public RegistryCreator() {
        this.customerRegistry = new CustomerRegistry();
        this.repairOrderRegistry = new RepairOrderRegistry();
        this.repairOrderLogger = new RepairOrderLogger("repairOrder.log");
        this.errorLogger = new ErrorLogger("error.log");
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

    /** @return repairOrderLogger The object that logs repair orders */
    public RepairOrderLogger getRepairOrderLogger() {
        return repairOrderLogger;
    }

    /** @return errorLogger The object that logs errors  */
    public ErrorLogger getErrorLogger() {
        return errorLogger;
    }

    /** @return The printer. */
    public Printer getPrinter() {
        return printer;
    }
}
