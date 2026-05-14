package se.kth.iv1350.repair.integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.AfterEach;

/**
 * Unit tests for the Logger strategy pattern.
 */
public class LoggerTest {

    @AfterEach
    public void cleanUp() {
        new File("test-error.log").delete();
        new File("test-repair.log").delete();
    }
    
    @Test
    public void testErrorLoggerDoesNotThrowWhenLogging() {
        ErrorLogger logger = new ErrorLogger("test-error.log");
        assertDoesNotThrow(() -> logger.log("Test error message"),
                "ErrorLogger should not throw when logging a message");
    }

    @Test
    public void testRepairOrderLoggerDoesNotThrowWhenLogging() {
        RepairOrderLogger logger = new RepairOrderLogger("test-repair.log");
        assertDoesNotThrow(() -> logger.log("Test repair order message"),
                "RepairOrderLogger should not throw when logging a message");
    }

    @Test
    public void testTwoLoggersAreIndependentInstances() {
        ErrorLogger errorLogger = new ErrorLogger("test-error.log");
        RepairOrderLogger repairLogger = new RepairOrderLogger("test-repair.log");
        assertNotSame(errorLogger, repairLogger,
                "Different logger implementations should be independent instances");
    }

    @Test
    public void testErrorLoggerExceptionLoggingDoesNotThrow() {
        ErrorLogger logger = new ErrorLogger("test-error.log");
        Exception testException = new Exception("Test exception");
        assertDoesNotThrow(() -> logger.logException("Test context", testException),
                "ErrorLogger should not throw when logging an exception");
    }
}