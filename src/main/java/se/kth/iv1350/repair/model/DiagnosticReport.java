package se.kth.iv1350.repair.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains the technician's findings and proposed repair tasks for a repair order.
 */
public class DiagnosticReport {
    private final String findings;
    private final List<RepairTask> repairTasks;

    /**
     * Creates a diagnostic report with the given findings and list of repair tasks.
     *
     * @param findings    A description of the technician's findings.
     * @param repairTasks The list of proposed repair tasks.
     */
    public DiagnosticReport(String findings, List<RepairTask> repairTasks) {
        this.findings = findings;
        this.repairTasks = new ArrayList<>(repairTasks);
    }

    /** @return The technician's findings. */
    public String getFindings() {
        return findings;
    }

    /** @return The total cost of all proposed repair tasks. */
    public double getTotalCost() {
        double total = 0;
        for (RepairTask task : repairTasks) {
            total += task.getCost();
        }
        return total;
    }

        /** @return An unmodifiable view of the proposed repair tasks. */
    public List<RepairTask> getRepairTasks() {
        return Collections.unmodifiableList(repairTasks);
    }
}
