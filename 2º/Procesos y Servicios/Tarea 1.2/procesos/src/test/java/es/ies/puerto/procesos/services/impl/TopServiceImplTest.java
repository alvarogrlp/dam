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
class TopServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    private TopServiceImpl topService;

    @BeforeEach
    void setUp() {
        topService = new TopServiceImpl(jobRepository);
    }

    @Test
    void testGetCommand() {
        assertEquals("top -b -n1", topService.getCommand());
    }

    @Test
    void testGetName() {
        String name = topService.getName();
        assertNotNull(name);
        assertTrue(name.contains("top"));
    }

    @Test
    void testExecuteSavesJob() throws Exception {
        doNothing().when(jobRepository).save(any(Job.class));

        try {
            topService.execute();
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
            topService.execute();
        } catch (Exception e) {
            // Expected on non-Linux systems
        }

        verify(jobRepository, atLeastOnce()).save(any(Job.class));
        Job savedJob = jobCaptor.getValue();
        assertNotNull(savedJob);
        assertEquals("top -b -n1", savedJob.getCommand());
        assertNotNull(savedJob.getId());
    }

    @Test
    void testExecuteHandlesException() throws Exception {
        doThrow(new RuntimeException("Save failed")).when(jobRepository).save(any(Job.class));

        assertThrows(Exception.class, () -> topService.execute());
    }
}
