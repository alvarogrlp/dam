package es.ies.puerto.procesos.controllers;

import es.ies.puerto.procesos.domain.Job;
import es.ies.puerto.procesos.services.interfaces.CommandService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Controlador CLI principal de la aplicación
 */
@Component
public class CliController implements CommandLineRunner {
    
    private final List<CommandService> commandServices;
    private final Scanner scanner;

    public CliController(List<CommandService> commandServices) {
        this.commandServices = commandServices;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Lanzador de Procesos (CLI) Linux ===");
        System.out.println();
        
        boolean running = true;
        
        while (running) {
            showMenu();
            String option = scanner.nextLine().trim();
            
            if (option.isEmpty()) {
                continue;
            }
            
            switch (option) {
                case "1":
                    executeCommand(0);
                    break;
                case "2":
                    executeCommand(1);
                    break;
                case "3":
                    executeCommand(2);
                    break;
                case "4":
                    executeCustomCommand();
                    break;
                case "0":
                    System.out.println("\nCerrando aplicación...");
                    System.out.println("Pulsa Ctrl+C para finalizar definitivamente.");
                    running = false;
                    break;
                default:
                    System.err.println("\n[ERROR] Opción no válida. Intente de nuevo.");
            }
            
            if (running) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void showMenu() {
        System.out.println("\n================================================");
        System.out.println("Comandos disponibles:");
        System.out.println("================================================");
        
        for (int i = 0; i < commandServices.size(); i++) {
            CommandService service = commandServices.get(i);
            System.out.println((i + 1) + ". " + service.getName());
        }
        
        System.out.println("4. Ejecutar comando personalizado (validación de errores)");
        System.out.println("0. Salir");
        System.out.println("================================================");
        System.out.print("Seleccione una opción: ");
    }

    private void executeCommand(int index) {
        if (index < 0 || index >= commandServices.size()) {
            System.err.println("[ERROR] Índice de comando inválido.");
            return;
        }
        
        CommandService service = commandServices.get(index);
        System.out.println("\n>>> Ejecutando: " + service.getCommand());
        System.out.println("------------------------------------------------");
        
        try {
            Job job = service.execute();
            System.out.println("------------------------------------------------");
            System.out.println(">>> Ejecución completada.");
            System.out.println(">>> Job ID: " + job.getId());
            System.out.println(">>> Exit Code: " + job.getExitCode());
            System.out.println(">>> Success: " + (job.isSuccess() ? "✓" : "✗"));
            System.out.println(">>> Salida guardada en: jobs-history/");
            
        } catch (Exception e) {
            System.err.println("\n[ERROR] Error durante la ejecución: " + e.getMessage());
        }
    }

    private void executeCustomCommand() {
        System.out.print("\nIngrese el comando a ejecutar: ");
        String customCommand = scanner.nextLine().trim();
        
        if (customCommand.isEmpty()) {
            System.err.println("[ERROR] El comando no puede estar vacío.");
            return;
        }
        
        // Validar que el comando está en la lista de comandos permitidos
        List<String> allowedCommands = Arrays.asList("lsof -i", "top -b -n1", "ps aux | head");
        
        boolean isAllowed = allowedCommands.stream()
                .anyMatch(allowed -> customCommand.equals(allowed));
        
        if (!isAllowed) {
            String errorMessage = String.format(
                "[ERROR] Comando no permitido: '%s'%n" +
                "[ERROR] Solo se permiten los siguientes comandos:%n" +
                "[ERROR]   - lsof -i%n" +
                "[ERROR]   - top -b -n1%n" +
                "[ERROR]   - ps aux | head%n" +
                "[ERROR] La información del error ha sido almacenada.",
                customCommand
            );
            System.err.println(errorMessage);
            
            // Almacenar el error
            Job errorJob = new Job(java.util.UUID.randomUUID().toString(), customCommand);
            errorJob.setStderr(errorMessage);
            errorJob.setExitCode(-1);
            errorJob.setSuccess(false);
            
            try {
                // Buscar el repositorio para guardar el error
                commandServices.stream()
                    .findFirst()
                    .ifPresent(service -> {
                        try {
                            // Usar reflexión para acceder al repositorio
                            java.lang.reflect.Field field = service.getClass().getDeclaredField("jobRepository");
                            field.setAccessible(true);
                            es.ies.puerto.procesos.repositories.interfaces.JobRepository repo = 
                                (es.ies.puerto.procesos.repositories.interfaces.JobRepository) field.get(service);
                            repo.save(errorJob);
                            System.err.println("[INFO] Error registrado con Job ID: " + errorJob.getId());
                        } catch (Exception e) {
                            System.err.println("[ERROR] No se pudo guardar el registro de error: " + e.getMessage());
                        }
                    });
            } catch (Exception e) {
                System.err.println("[ERROR] No se pudo guardar el registro de error.");
            }
            
            return;
        }
        
        // Buscar el servicio correspondiente
        CommandService matchingService = commandServices.stream()
                .filter(service -> service.getCommand().equals(customCommand))
                .findFirst()
                .orElse(null);
        
        if (matchingService != null) {
            System.out.println("\n>>> Ejecutando: " + customCommand);
            System.out.println("------------------------------------------------");
            
            try {
                Job job = matchingService.execute();
                System.out.println("------------------------------------------------");
                System.out.println(">>> Ejecución completada.");
                System.out.println(">>> Job ID: " + job.getId());
                System.out.println(">>> Exit Code: " + job.getExitCode());
                System.out.println(">>> Success: " + (job.isSuccess() ? "✓" : "✗"));
                System.out.println(">>> Salida guardada en: jobs-history/");
                
            } catch (Exception e) {
                System.err.println("\n[ERROR] Error durante la ejecución: " + e.getMessage());
            }
        } else {
            System.err.println("[ERROR] No se encontró un servicio para el comando especificado.");
        }
    }
}
