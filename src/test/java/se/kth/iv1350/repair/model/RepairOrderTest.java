package se.kth.iv1350.repair.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RepairOrder class.
 */
public class RepairOrderTest {
    private RepairOrder repairOrder;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("Alice", 123, "a@b.com", "Trek", "FX3", "SN001");
        repairOrder = new RepairOrder("ORD-001", customer, "Bike won't start");
    }

    @Test
    public void testInitialStateIsNewlyCreated() {
        assertEquals(RepairOrderState.NEWLY_CREATED, repairOrder.getState(),
                "A newly created repair order should have state NEWLY_CREATED");
    }

    @Test
    public void testAddDiagnosticReportChangesStateToReadyForApproval() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        DiagnosticReport report = new DiagnosticReport("Motor fault detected", tasks);
        repairOrder.addDiagnosticReport(report);
        assertEquals(RepairOrderState.READY_FOR_APPROVAL, repairOrder.getState(),
                "State should be READY_FOR_APPROVAL after diagnostic report is added");
    }

    @Test
    public void testNoDiagnosticReportInitially() {
        assertNull(repairOrder.getDiagnosticReport(),
                "Diagnostic report should be null before any report is added");
    }

    @Test
    public void testAddDiagnosticReportStoresReport() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        DiagnosticReport report = new DiagnosticReport("Motor fault detected", tasks);
        repairOrder.addDiagnosticReport(report);
        assertNotNull(repairOrder.getDiagnosticReport(),
                "Diagnostic report should not be null after being added");
    }

    @Test
    public void testAcceptChangesStateToAccepted() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        repairOrder.accept();
        assertEquals(RepairOrderState.ACCEPTED, repairOrder.getState(),
                "State should be ACCEPTED after accept() is called");
    }

    @Test
    public void testRejectChangesStateToRejected() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        repairOrder.reject();
        assertEquals(RepairOrderState.REJECTED, repairOrder.getState(),
                "State should be REJECTED after reject() is called");
    }

    @Test
    public void testDateIsSetOnCreation() {
        assertNotNull(repairOrder.getDate(),
                "Creation date should be set automatically");
    }

    @Test
    public void testToDTOWithoutDiagnosticReport() {
        RepairOrderDTO dto = repairOrder.toDTO();
        assertFalse(dto.hasDiagnosticReport(),
                "DTO should not have a diagnostic report when none has been added");
    }

    @Test
    public void testToDTOWithDiagnosticReportContainsFindings() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        RepairOrderDTO dto = repairOrder.toDTO();
        assertEquals("Motor fault", dto.getDiagnosticFindings(),
                "DTO should contain the correct diagnostic findings");
    }

    @Test
    public void testToDTOContainsCorrectState() {
        RepairOrderDTO dto = repairOrder.toDTO();
        assertEquals("NEWLY_CREATED", dto.getState(),
                "DTO state should match the repair order state");
    }
}
