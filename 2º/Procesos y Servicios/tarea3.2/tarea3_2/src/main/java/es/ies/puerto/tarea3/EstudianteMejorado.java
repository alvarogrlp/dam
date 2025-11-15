package es.ies.puerto.tarea3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Versión mejorada de la clase Estudiante que indica qué equipo específico
 * está utilizando cada estudiante.
 * @author Álvaro
 * @version 1.0
 */
public class EstudianteMejorado extends Thread {
    
    private String nombre;
    private static Semaphore semaforo = new Semaphore(4);
    
    /**
     * Constructor que inicializa el estudiante con un nombre
     * @param nombre el nombre del estudiante
     */
    public EstudianteMejorado(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        try {
            semaforo.acquire();
            
            int equipoNumero = 4 - semaforo.availablePermits();
            
            System.out.println("El " + nombre + " ha comenzado a utilizar el equipo " + equipoNumero);
            
            int tiempoUso = ThreadLocalRandom.current().nextInt(3000, 5001);
            Thread.sleep(tiempoUso);
            
            System.out.println("El " + nombre + " ha finalizado con el equipo " + equipoNumero);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El " + nombre + " fue interrumpido");
        } finally {
            semaforo.release();
        }
    }
    
    /**
     * Método principal que ejecuta la simulación del laboratorio mejorado
     * @param args argumentos de línea de comandos
     * @throws InterruptedException si el hilo es interrumpido
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Laboratorio de Construcción (Versión Mejorada) ===");
        System.out.println("4 equipos disponibles para 6 estudiantes\n");
        EstudianteMejorado[] estudiantes = new EstudianteMejorado[6];
        for (int i = 0; i < 6; i++) {
            estudiantes[i] = new EstudianteMejorado("estudiante " + (i + 1));
        }
        
        // Iniciar todos los estudiantes
        for (EstudianteMejorado estudiante : estudiantes) {
            estudiante.start();
        }
        
        for (EstudianteMejorado estudiante : estudiantes) {
            estudiante.join();
        }
        
        System.out.println("\nTodos los estudiantes han terminado sus ejercicios.");
    }
}
