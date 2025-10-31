package es.ies.puerto.procesos.repositories.file;

import es.ies.puerto.procesos.domain.Job;
import es.ies.puerto.procesos.repositories.interfaces.JobRepository;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del repositorio que persiste los Jobs en ficheros .txt
 */
@Repository
public class FileJobRepository implements JobRepository {
    
    private static final String STORAGE_DIR = "jobs-history";
    private static final DateTimeFormatter FILENAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    public FileJobRepository() {
        // Crear directorio si no existe
        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void save(Job job) throws IOException {
        String fileName = generateFileName(job);
        Path filePath = Paths.get(STORAGE_DIR, fileName);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write("=== JOB EXECUTION REPORT ===\n");
            writer.write("ID: " + job.getId() + "\n");
            writer.write("Command: " + job.getCommand() + "\n");
            writer.write("Executed At: " + job.getExecutedAt() + "\n");
            writer.write("Exit Code: " + job.getExitCode() + "\n");
            writer.write("Success: " + job.isSuccess() + "\n");
            writer.write("\n=== STDOUT ===\n");
            writer.write(job.getStdout() != null ? job.getStdout() : "(empty)");
            writer.write("\n\n=== STDERR ===\n");
            writer.write(job.getStderr() != null ? job.getStderr() : "(empty)");
            writer.write("\n");
        }
    }

    @Override
    public List<Job> findAll() {
        List<Job> jobs = new ArrayList<>();
        File dir = new File(STORAGE_DIR);
        
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    try {
                        Job job = readJobFromFile(file);
                        if (job != null) {
                            jobs.add(job);
                        }
                    } catch (IOException e) {
                        // Log and continue
                        System.err.println("Error reading file: " + file.getName());
                    }
                }
            }
        }
        
        return jobs;
    }

    @Override
    public Job findById(String id) {
        List<Job> allJobs = findAll();
        return allJobs.stream()
                .filter(job -> job.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private String generateFileName(Job job) {
        String timestamp = job.getExecutedAt().format(FILENAME_FORMATTER);
        String sanitizedCommand = job.getCommand()
                .replaceAll("[^a-zA-Z0-9]", "_")
                .substring(0, Math.min(30, job.getCommand().length()));
        return String.format("%s_%s_%s.txt", timestamp, job.getId(), sanitizedCommand);
    }

    private Job readJobFromFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        if (lines.isEmpty()) {
            return null;
        }
        
        Job job = new Job();
        StringBuilder stdout = new StringBuilder();
        StringBuilder stderr = new StringBuilder();
        
        String currentSection = "";
        
        for (String line : lines) {
            if (line.startsWith("ID: ")) {
                job.setId(line.substring(4));
            } else if (line.startsWith("Command: ")) {
                job.setCommand(line.substring(9));
            } else if (line.startsWith("Exit Code: ")) {
                job.setExitCode(Integer.parseInt(line.substring(11)));
            } else if (line.startsWith("Success: ")) {
                job.setSuccess(Boolean.parseBoolean(line.substring(9)));
            } else if (line.contains("=== STDOUT ===")) {
                currentSection = "stdout";
            } else if (line.contains("=== STDERR ===")) {
                currentSection = "stderr";
            } else if (!line.startsWith("===") && !line.startsWith("Executed At:")) {
                if ("stdout".equals(currentSection)) {
                    stdout.append(line).append("\n");
                } else if ("stderr".equals(currentSection)) {
                    stderr.append(line).append("\n");
                }
            }
        }
        
        job.setStdout(stdout.toString().trim());
        job.setStderr(stderr.toString().trim());
        
        return job;
    }
}
