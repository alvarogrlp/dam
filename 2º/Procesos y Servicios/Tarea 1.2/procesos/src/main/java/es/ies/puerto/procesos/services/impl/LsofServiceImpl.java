package es.ies.puerto.procesos.services.impl;

import es.ies.puerto.procesos.domain.Job;
import es.ies.puerto.procesos.repositories.interfaces.JobRepository;
import es.ies.puerto.procesos.services.interfaces.CommandService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * Servicio para ejecutar el comando lsof -i
 */
@Service
public class LsofServiceImpl implements CommandService {
    
    private static final String COMMAND = "lsof -i";
    private final JobRepository jobRepository;

    public LsofServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job execute() throws Exception {
        Job job = new Job(UUID.randomUUID().toString(), COMMAND);
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", COMMAND);
            Process process = processBuilder.start();
            
            StringBuilder stdout = new StringBuilder();
            StringBuilder stderr = new StringBuilder();
            
            // Leer stdout en tiempo real
            Thread stdoutThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[OUT] " + line);
                        stdout.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            
            // Leer stderr en tiempo real
            Thread stderrThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println("[ERR] " + line);
                        stderr.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            
            stdoutThread.start();
            stderrThread.start();
            
            int exitCode = process.waitFor();
            stdoutThread.join();
            stderrThread.join();
            
            job.setStdout(stdout.toString());
            job.setStderr(stderr.toString());
            job.setExitCode(exitCode);
            job.setSuccess(exitCode == 0);
            
            // Guardar el job
            jobRepository.save(job);
            
        } catch (Exception e) {
            job.setStderr(e.getMessage());
            job.setExitCode(-1);
            job.setSuccess(false);
            jobRepository.save(job);
            throw e;
        }
        
        return job;
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public String getName() {
        return "lsof -i (List open files with network connections)";
    }
}
