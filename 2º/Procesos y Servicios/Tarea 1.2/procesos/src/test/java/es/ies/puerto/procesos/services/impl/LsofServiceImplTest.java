package es.ies.puerto.procesos.services.impl;

import es.ies.puerto.procesos.domain.Job;
import es.ies.puerto.procesos.repositories.interfaces.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LsofServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    private LsofServiceImpl lsofService;

    @BeforeEach
    void setUp() {
        lsofService = new LsofServiceImpl(jobRepository);
    }

    @Test
    void testGetCommand() {
        assertEquals("lsof -i", lsofService.getCommand());
    }

    @Test
    void testGetName() {
        String name = lsofService.getName();
        assertNotNull(name);
        assertTrue(name.contains("lsof"));
    }

    @Test
    void testExecuteSavesJob() throws Exception {
        // Simulate command not available (will fail but should still save)
        doNothing().when(jobRepository).save(any(Job.class));

        try {
            lsofService.execute();
        } catch (Exception e) {
            // Expected to fail on Windows/non-Linux systems
        }

        // Verify that save was called
        verify(jobRepository, atLeastOnce()).save(any(Job.class));
    }

    @Test
    void testExecuteCreatesJobWithCorrectCommand() throws Exception {
        ArgumentCaptor<Job> jobCaptor = ArgumentCaptor.forClass(Job.class);
        doNothing().when(jobRepository).save(jobCaptor.capture());

        try {
            lsofService.execute();
        } catch (Exception e) {
            // Expected on non-Linux systems
        }

        // Verify job was created with correct command
        verify(jobRepository, atLeastOnce()).save(any(Job.class));
        Job savedJob = jobCaptor.getValue();
        assertNotNull(savedJob);
        assertEquals("lsof -i", savedJob.getCommand());
        assertNotNull(savedJob.getId());
    }

    @Test
    void testExecuteHandlesException() throws Exception {
        doThrow(new RuntimeException("Save failed")).when(jobRepository).save(any(Job.class));

        assertThrows(Exception.class, () -> lsofService.execute());
    }
}
