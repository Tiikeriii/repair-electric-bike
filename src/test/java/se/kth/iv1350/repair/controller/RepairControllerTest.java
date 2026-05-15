package se.kth.iv1350.repair.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.repair.integration.CustomerNotFoundException;
import se.kth.iv1350.repair.integration.CustomerRegistry;
import se.kth.iv1350.repair.integration.DatabaseFailureException;
import se.kth.iv1350.repair.integration.RegistryCreator;
import se.kth.iv1350.repair.integration.RepairOrderRegistry;
import se.kth.iv1350.repair.model.CustomerDTO;
import se.kth.iv1350.repair.model.RepairOrderDTO;
import se.kth.iv1350.repair.model.RepairOrderObserver;
import se.kth.iv1350.repair.model.RepairTaskDTO;
import se.kth.iv1350.repair.view.RepairOrderView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RepairController class.
 */
public class RepairControllerTest {
    private RepairController controller;
    private CustomerDTO customerInfo;

    @BeforeEach
    public void setUp() throws CustomerNotFoundException, DatabaseFailureException {
        controller = new RepairController(new RegistryCreator(), null);
        customerInfo = controller.findCustomer(123456789);
    }

    @AfterEach
    public void tearDown() {
        CustomerRegistry.resetInstance();
        RepairOrderRegistry.resetInstance();
    }

    @Test
    public void testFindKnownCustomerReturnsNonNull() {
        assertNotNull(customerInfo,
                "findCustomer with a known phone number should return a CustomerDTO");
    }

    @Test
    public void testFindUnknownCustomerThrowsException() {
        CustomerRegistry controller = CustomerRegistry.getInstance();
        assertThrows(CustomerNotFoundException.class, () -> {
            controller.findCustomer(999999999);
        }, "Should throw CustomerNotFoundException for unknown phone number");
    }

    @Test
    public void testCreateRepairOrderReturnsDTO() {
        RepairOrderDTO order = controller.createRepairOrder(customerInfo, "Bike won't start");
        assertNotNull(order, "createRepairOrder should return a non-null RepairOrderDTO");
    }

    @Test
    public void testCreatedOrderHasStateNewlyCreated() {
        RepairOrderDTO order = controller.createRepairOrder(customerInfo, "Bike won't start");
        assertEquals("NEWLY_CREATED", order.getState(),
                "A newly created order should have state NEWLY_CREATED");
    }

    @Test
    public void testGetRepairOrderReturnsCreatedOrder() {
        RepairOrderDTO created = controller.createRepairOrder(customerInfo, "Problem");
        RepairOrderDTO retrieved = controller.getRepairOrder();
        assertEquals(created.getOrderId(), retrieved.getOrderId(),
                "getRepairOrder should return the previously created order");
    }

    @Test
    public void testGetRepairOrderReturnsNullWhenQueueIsEmpty() {
        RepairOrderDTO result = controller.getRepairOrder();
        assertNull(result, "getRepairOrder should return null when no orders are queued");
    }

    @Test
    public void testAddDiagnosticReportChangesState() {
        controller.createRepairOrder(customerInfo, "Problem");
        controller.getRepairOrder();
        List<RepairTaskDTO> tasks = Arrays.asList(new RepairTaskDTO("Fix motor", 500.0));
        RepairOrderDTO updated = controller.addDiagnosticReport("Motor fault", tasks);
        assertEquals("READY_FOR_APPROVAL", updated.getState(),
                "State should be READY_FOR_APPROVAL after diagnostic report is added");
    }

    @Test
    public void testAcceptRepairOrderChangesStateToAccepted() {
        controller.createRepairOrder(customerInfo, "Problem");
        controller.getRepairOrder();
        List<RepairTaskDTO> tasks = Arrays.asList(new RepairTaskDTO("Fix motor", 500.0));
        controller.addDiagnosticReport("Motor fault", tasks);
        RepairOrderDTO accepted = controller.acceptRepairOrder();
        assertEquals("ACCEPTED", accepted.getState(),
                "State should be ACCEPTED after acceptRepairOrder is called");
    }

    @Test
    public void testRejectRepairOrderChangesStateToRejected() {
        controller.createRepairOrder(customerInfo, "Problem");
        controller.getRepairOrder();
        List<RepairTaskDTO> tasks = Arrays.asList(new RepairTaskDTO("Fix motor", 500.0));
        controller.addDiagnosticReport("Motor fault", tasks);
        RepairOrderDTO rejected = controller.rejectRepairOrder();
        assertEquals("REJECTED", rejected.getState(),
                "State should be REJECTED after rejectRepairOrder is called");
    }

    @Test
    public void testCreatedOrderContainsCorrectProblemDescription() {
        String problem = "Bike won't start";
        RepairOrderDTO order = controller.createRepairOrder(customerInfo, problem);
        assertEquals(problem, order.getProblemDescription(),
                "The repair order should contain the correct problem description");
    }
}