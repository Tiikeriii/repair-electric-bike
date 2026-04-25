package se.kth.iv1350.repair.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repair.integration.RegistryCreator;
import se.kth.iv1350.repair.model.CustomerInfo;
import se.kth.iv1350.repair.model.DiagnosticReport;
import se.kth.iv1350.repair.model.RepairOrder;
import se.kth.iv1350.repair.model.RepairOrderState;
import se.kth.iv1350.repair.model.RepairTask;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RepairController class.
 */
public class RepairControllerTest {
    private RepairController controller;
    private CustomerInfo customerInfo;

    @BeforeEach
    public void setUp() {
        controller = new RepairController(new RegistryCreator());
        customerInfo = controller.findCustomer(123456789);
    }

    @Test
    public void testFindKnownCustomerReturnsNonNull() {
        assertNotNull(customerInfo,
                "findCustomer with a known phone number should return a CustomerInfo");
    }

    @Test
    public void testFindUnknownCustomerReturnsNull() {
        CustomerInfo unknown = controller.findCustomer(999999999);
        assertNull(unknown,
                "findCustomer with an unknown phone number should return null");
    }

    @Test
    public void testCreateRepairOrderReturnsOrder() {
        RepairOrder order = controller.createRepairOrder(customerInfo, "Bike won't start");
        assertNotNull(order, "createRepairOrder should return a non-null RepairOrder");
    }

    @Test
    public void testCreatedOrderHasStateNewlyCreated() {
        RepairOrder order = controller.createRepairOrder(customerInfo, "Bike won't start");
        assertEquals(RepairOrderState.NEWLY_CREATED, order.getState(),
                "A newly created order should have state NEWLY_CREATED");
    }

    @Test
    public void testGetRepairOrderReturnsCreatedOrder() {
        RepairOrder created = controller.createRepairOrder(customerInfo, "Problem");
        RepairOrder retrieved = controller.getRepairOrder();
        assertEquals(created.getOrderId(), retrieved.getOrderId(),
                "getRepairOrder should return the previously created order");
    }

    @Test
    public void testGetRepairOrderReturnsNullWhenQueueIsEmpty() {
        RepairOrder result = controller.getRepairOrder();
        assertNull(result, "getRepairOrder should return null when no orders are queued");
    }

    @Test
    public void testAddDiagnosticReportChangesState() {
        controller.createRepairOrder(customerInfo, "Problem");
        controller.getRepairOrder();
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        DiagnosticReport report = new DiagnosticReport("Motor fault", tasks);
        RepairOrder updated = controller.addDiagnosticReport(report);
        assertEquals(RepairOrderState.READY_FOR_APPROVAL, updated.getState(),
                "State should be READY_FOR_APPROVAL after diagnostic report is added");
    }

    @Test
    public void testAcceptRepairOrderChangesStateToAccepted() {
        controller.createRepairOrder(customerInfo, "Problem");
        controller.getRepairOrder();
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        controller.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        RepairOrder accepted = controller.acceptRepairOrder();
        assertEquals(RepairOrderState.ACCEPTED, accepted.getState(),
                "State should be ACCEPTED after acceptRepairOrder is called");
    }

    @Test
    public void testRejectRepairOrderChangesStateToRejected() {
        controller.createRepairOrder(customerInfo, "Problem");
        controller.getRepairOrder();
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        controller.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        RepairOrder rejected = controller.rejectRepairOrder();
        assertEquals(RepairOrderState.REJECTED, rejected.getState(),
                "State should be REJECTED after rejectRepairOrder is called");
    }
}
