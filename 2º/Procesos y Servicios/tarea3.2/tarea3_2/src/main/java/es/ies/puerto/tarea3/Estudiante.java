package es.ies.puerto.tarea3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase que representa un estudiante que necesita usar un equipo del laboratorio.
 * Hay 4 equipos disponibles para 6 estudiantes, por lo que solo 4 pueden trabajar
 * simultáneamente.
 * @author Álvaro
 * @version 1.0
 */
public class Estudiante extends Thread {
    
    private String nombre;
    private static Semaphore semaforo = new Semaphore(4);
    
    /**
     * Constructor que inicializa el estudiante con un nombre
     * @param nombre el nombre del estudiante
     */
    public Estudiante(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        try {
            semaforo.acquire();
            
            System.out.println("El " + nombre + " ha comenzado a utilizar el equipo");
            
            int tiempoUso = ThreadLocalRandom.current().nextInt(3000, 5001);
            Thread.sleep(tiempoUso);
            
            System.out.println("El " + nombre + " ha finalizado con el equipo");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El " + nombre + " fue interrumpido");
        } finally {
            semaforo.release();
        }
    }
    
    /**
     * Método principal que ejecuta la simulación del laboratorio
     * @param args argumentos de línea de comandos
     * @throws InterruptedException si el hilo es interrumpido
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Laboratorio de Construcción ===");
        System.out.println("4 equipos disponibles para 6 estudiantes\n");
        Estudiante[] estudiantes = new Estudiante[6];
        for (int i = 0; i < 6; i++) {
            estudiantes[i] = new Estudiante("estudiante " + (i + 1));
        }
        
        for (Estudiante estudiante : estudiantes) {
            estudiante.start();
        }
        
        for (Estudiante estudiante : estudiantes) {
            estudiante.join();
        }
        
        System.out.println("\nTodos los estudiantes han terminado sus ejercicios.");
    }
}
