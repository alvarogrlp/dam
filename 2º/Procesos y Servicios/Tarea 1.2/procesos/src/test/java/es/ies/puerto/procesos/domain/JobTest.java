package es.ies.puerto.procesos.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    private Job job;

    @BeforeEach
    void setUp() {
        job = new Job();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(job);
        assertNotNull(job.getExecutedAt());
    }

    @Test
    void testConstructorWithParameters() {
        String id = "test-id";
        String command = "test command";
        Job paramJob = new Job(id, command);
        
        assertEquals(id, paramJob.getId());
        assertEquals(command, paramJob.getCommand());
        assertNotNull(paramJob.getExecutedAt());
    }

    @Test
    void testGetAndSetId() {
        String id = "job-123";
        job.setId(id);
        assertEquals(id, job.getId());
    }

    @Test
    void testGetAndSetCommand() {
        String command = "ls -la";
        job.setCommand(command);
        assertEquals(command, job.getCommand());
    }

    @Test
    void testGetAndSetExecutedAt() {
        LocalDateTime now = LocalDateTime.now();
        job.setExecutedAt(now);
        assertEquals(now, job.getExecutedAt());
    }

    @Test
    void testGetAndSetStdout() {
        String stdout = "output content";
        job.setStdout(stdout);
        assertEquals(stdout, job.getStdout());
    }

    @Test
    void testGetAndSetStderr() {
        String stderr = "error content";
        job.setStderr(stderr);
        assertEquals(stderr, job.getStderr());
    }

    @Test
    void testGetAndSetExitCode() {
        int exitCode = 0;
        job.setExitCode(exitCode);
        assertEquals(exitCode, job.getExitCode());
    }

    @Test
    void testGetAndSetSuccess() {
        job.setSuccess(true);
        assertTrue(job.isSuccess());
        
        job.setSuccess(false);
        assertFalse(job.isSuccess());
    }

    @Test
    void testToString() {
        job.setId("test-123");
        job.setCommand("test command");
        job.setExitCode(0);
        job.setSuccess(true);
        
        String result = job.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("test-123"));
        assertTrue(result.contains("test command"));
        assertTrue(result.contains("exitCode=0"));
        assertTrue(result.contains("success=true"));
    }
}
