package se.kth.iv1350.repair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repair.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the observer pattern in RepairOrder.
 */
public class RepairOrderObserverTest {
    private RepairOrder repairOrder;
    private TestObserver testObserver;
    private List<RepairOrderObserver> observers = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        testObserver = new TestObserver();
        observers.add(testObserver);
        Customer customer = new Customer("Alice", 123, "a@b.com", "Trek", "FX3", "SN001");
        repairOrder = new RepairOrder("ORD-001", customer, "Bike won't start", observers);
    }

    @AfterEach
    public void tearDown() {
        observers.clear();
    }

    @Test
    public void testObserverNotifiedWhenDiagnosticReportAdded() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        assertEquals(2, testObserver.getUpdateCount(),
                "Observer should be notified twice once when created, once when diagnostic report is added");
    }

    @Test
    public void testObserverNotifiedWhenOrderAccepted() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        repairOrder.accept();
        assertEquals(3, testObserver.getUpdateCount(),
                "Observer should be notified thrice  once when created, once for report, once for accept");
    }

    @Test
    public void testObserverNotifiedWhenOrderRejected() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        repairOrder.reject();
        assertEquals(3, testObserver.getUpdateCount(),
                "Observer should be notified twice  once when created, once for report, once for reject");
    }

    @Test
    public void testObserverReceivesCorrectStateAfterAccept() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        repairOrder.accept();
        assertEquals("ACCEPTED", testObserver.getLastUpdate().getState(),
                "Observer should receive DTO with state ACCEPTED");
    }

    @Test
    public void testObserverReceivesCorrectStateAfterReject() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        repairOrder.reject();
        assertEquals("REJECTED", testObserver.getLastUpdate().getState(),
                "Observer should receive DTO with state REJECTED");
    }

    @Test
    public void testMultipleObserversAllNotified() {
        TestObserver secondObserver = new TestObserver();
        repairOrder.addObserver(secondObserver);
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        assertEquals(2, testObserver.getUpdateCount(),
                "First observer should be notified");
        assertEquals(1, secondObserver.getUpdateCount(),
                "Second observer should also be notified");
    }

    @Test
    public void testObserverReceivesCorrectOrderId() {
        List<RepairTask> tasks = Arrays.asList(new RepairTask("Fix motor", 500.0));
        repairOrder.addDiagnosticReport(new DiagnosticReport("Motor fault", tasks));
        assertEquals("ORD-001", testObserver.getLastUpdate().getOrderId(),
                "Observer should receive DTO with correct order ID");
    }

    public class TestObserver implements RepairOrderObserver {
        private RepairOrderDTO lastUpdate;
        private int updateCount = 0;

        @Override
        public void repairOrderUpdated(RepairOrderDTO repairOrder) {
            this.lastUpdate = repairOrder;
            this.updateCount++;
        }

        /** @return The last RepairOrderDTO received. */
        public RepairOrderDTO getLastUpdate() {
            return lastUpdate;
        }

        /** @return The number of times this observer was notified. */
        public int getUpdateCount() {
            return updateCount;
        }
    }
}