package se.kth.iv1350.repair.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DiagnosticReport class.
 */
public class DiagnosticReportTest {

    @Test
    public void testTotalCostIsCorrect() {
        List<RepairTask> tasks = Arrays.asList(
                new RepairTask("Replace BMS", 1500.0),
                new RepairTask("Clean drivetrain", 350.0)
        );
        DiagnosticReport report = new DiagnosticReport("Battery fault", tasks);
        assertEquals(1850.0, report.getTotalCost(), 0.001,
                "Total cost should be the sum of all repair task costs");
    }

    @Test
    public void testTotalCostWithSingleTask() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix brake", 200.0));
        DiagnosticReport report = new DiagnosticReport("Brake issue", tasks);
        assertEquals(200.0, report.getTotalCost(), 0.001,
                "Total cost with one task should equal that task's cost");
    }

    @Test
    public void testRepairTasksListIsUnmodifiable() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        DiagnosticReport report = new DiagnosticReport("Motor fault", tasks);
        assertThrows(UnsupportedOperationException.class,
                () -> report.getRepairTasks().add(new RepairTask("Extra task", 100.0)),
                "The repair tasks list should be unmodifiable");
    }

    @Test
    public void testGetRepairTasksReturnsAllTasks() {
        List<RepairTask> tasks = Arrays.asList(
                new RepairTask("Task A", 100.0),
                new RepairTask("Task B", 200.0)
        );
        DiagnosticReport report = new DiagnosticReport("Findings", tasks);
        assertEquals(2, report.getRepairTasks().size(),
                "All provided repair tasks should be returned");
    }
}
