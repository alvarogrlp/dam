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
class PsHeadServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    private PsHeadServiceImpl psHeadService;

    @BeforeEach
    void setUp() {
        psHeadService = new PsHeadServiceImpl(jobRepository);
    }

    @Test
    void testGetCommand() {
        assertEquals("ps aux | head", psHeadService.getCommand());
    }

    @Test
    void testGetName() {
        String name = psHeadService.getName();
        assertNotNull(name);
        assertTrue(name.contains("ps aux"));
    }

    @Test
    void testExecuteSavesJob() throws Exception {
        doNothing().when(jobRepository).save(any(Job.class));

        try {
            psHeadService.execute();
        } catch (Exception e) {
            // Expected to fail on Windows/non-Linux systems
        }

        verify(jobRepository, atLeastOnce()).save(any(Job.class));
    }

    @Test
    void testExecuteCreatesJobWithCorrectCommand() throws Exception {
        ArgumentCaptor<Job> jobCaptor = ArgumentCaptor.forClass(Job.class);
        doNothing().when(jobRepository).save(jobCaptor.capture());

        try {
            psHeadService.execute();
        } catch (Exception e) {
            // Expected on non-Linux systems
        }

        verify(jobRepository, atLeastOnce()).save(any(Job.class));
        Job savedJob = jobCaptor.getValue();
        assertNotNull(savedJob);
        assertEquals("ps aux | head", savedJob.getCommand());
        assertNotNull(savedJob.getId());
    }

    @Test
    void testExecuteHandlesException() throws Exception {
        doThrow(new RuntimeException("Save failed")).when(jobRepository).save(any(Job.class));

        assertThrows(Exception.class, () -> psHeadService.execute());
    }
}
