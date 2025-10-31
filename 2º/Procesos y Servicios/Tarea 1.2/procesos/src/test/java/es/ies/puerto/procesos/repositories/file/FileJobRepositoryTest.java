package es.ies.puerto.procesos.repositories.file;

import es.ies.puerto.procesos.domain.Job;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileJobRepositoryTest {

    private FileJobRepository repository;
    private static final String TEST_STORAGE_DIR = "jobs-history";

    @BeforeEach
    void setUp() {
        repository = new FileJobRepository();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Limpiar directorio de test
        Path path = Paths.get(TEST_STORAGE_DIR);
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    void testConstructorCreatesDirectory() {
        File dir = new File(TEST_STORAGE_DIR);
        assertTrue(dir.exists());
        assertTrue(dir.isDirectory());
    }

    @Test
    void testSaveJob() throws IOException {
        Job job = new Job("test-123", "ls -la");
        job.setStdout("test output");
        job.setStderr("test error");
        job.setExitCode(0);
        job.setSuccess(true);
        job.setExecutedAt(LocalDateTime.now());

        repository.save(job);

        File dir = new File(TEST_STORAGE_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        
        assertNotNull(files);
        assertTrue(files.length > 0);
    }

    @Test
    void testSaveJobWithNullOutputs() throws IOException {
        Job job = new Job("test-456", "ps aux");
        job.setStdout(null);
        job.setStderr(null);
        job.setExitCode(1);
        job.setSuccess(false);
        job.setExecutedAt(LocalDateTime.now());

        repository.save(job);

        File dir = new File(TEST_STORAGE_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        
        assertNotNull(files);
        assertTrue(files.length > 0);
    }

    @Test
    void testFindAll() throws IOException {
        Job job1 = createTestJob("job-1", "command1");
        Job job2 = createTestJob("job-2", "command2");

        repository.save(job1);
        repository.save(job2);

        List<Job> jobs = repository.findAll();

        assertNotNull(jobs);
        assertEquals(2, jobs.size());
    }

    @Test
    void testFindAllEmptyDirectory() {
        List<Job> jobs = repository.findAll();
        assertNotNull(jobs);
        assertTrue(jobs.isEmpty());
    }

    @Test
    void testFindById() throws IOException {
        Job job = createTestJob("specific-id", "test command");
        repository.save(job);

        Job found = repository.findById("specific-id");

        assertNotNull(found);
        assertEquals("specific-id", found.getId());
        assertEquals("test command", found.getCommand());
    }

    @Test
    void testFindByIdNotFound() {
        Job found = repository.findById("non-existent-id");
        assertNull(found);
    }

    @Test
    void testSaveAndReadCompleteJob() throws IOException {
        Job originalJob = new Job("complete-job", "lsof -i");
        originalJob.setStdout("Line 1\nLine 2\nLine 3");
        originalJob.setStderr("Error line 1\nError line 2");
        originalJob.setExitCode(0);
        originalJob.setSuccess(true);
        originalJob.setExecutedAt(LocalDateTime.now());

        repository.save(originalJob);

        Job retrievedJob = repository.findById("complete-job");

        assertNotNull(retrievedJob);
        assertEquals(originalJob.getId(), retrievedJob.getId());
        assertEquals(originalJob.getCommand(), retrievedJob.getCommand());
        assertTrue(retrievedJob.getStdout().contains("Line 1"));
        assertTrue(retrievedJob.getStderr().contains("Error line 1"));
        assertEquals(originalJob.getExitCode(), retrievedJob.getExitCode());
        assertEquals(originalJob.isSuccess(), retrievedJob.isSuccess());
    }

    private Job createTestJob(String id, String command) {
        Job job = new Job(id, command);
        job.setStdout("test output for " + id);
        job.setStderr("test error for " + id);
        job.setExitCode(0);
        job.setSuccess(true);
        job.setExecutedAt(LocalDateTime.now());
        return job;
    }
}
