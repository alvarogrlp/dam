package es.ies.puerto.procesos.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobAdditionalTest {

    @Test
    void testJobToStringWithAllFields() {
        Job job = new Job("id-123", "ls -la");
        job.setExitCode(1);
        job.setSuccess(false);
        job.setStdout("output");
        job.setStderr("error");
        
        String result = job.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("Job{"));
    }
}
