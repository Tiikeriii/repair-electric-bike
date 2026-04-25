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
}
