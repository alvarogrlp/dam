package es.ies.puerto.procesos.domain;

import java.time.LocalDateTime;

/**
 * Representa un trabajo/proceso ejecutado
 */
public class Job {
    private String id;
    private String command;
    private LocalDateTime executedAt;
    private String stdout;
    private String stderr;
    private int exitCode;
    private boolean success;

    public Job() {
        this.executedAt = LocalDateTime.now();
    }

    public Job(String id, String command) {
        this();
        this.id = id;
        this.command = command;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", command='" + command + '\'' +
                ", executedAt=" + executedAt +
                ", exitCode=" + exitCode +
                ", success=" + success +
                '}';
    }
}
