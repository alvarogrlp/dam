package es.ies.puerto.tarea3;

import java.util.concurrent.Semaphore;

/**
 * Versión mejorada del semáforo de tráfico usando semáforos para controlar
 * la alternancia estricta: ROJO -> VERDE -> ÁMBAR -> ROJO -> ...
 * @author Álvaro
 * @version 1.0
 */
public class ColorSemaforoMejorado implements Runnable {
    
    /**
     * Enumeración que representa los tres posibles estados del semáforo
     */
    private enum Color {
        ROJO, VERDE, AMBAR
    }
    
    private final Color miColor;
    private volatile boolean running;
    
    private static final Semaphore semaforoRojo = new Semaphore(1);
    private static final Semaphore semaforoVerde = new Semaphore(0);
    private static final Semaphore semaforoAmbar = new Semaphore(0);
    
    /**
     * Constructor que inicializa el semáforo con un color específico
     * @param color el color que representará este hilo
     */
    public ColorSemaforoMejorado(Color color) {
        this.miColor = color;
        this.running = true;
    }
    
    @Override
    public void run() {
        try {
            while (running) {
                Semaphore miSemaforo = obtenerSemaforo(miColor);
                Semaphore siguienteSemaforo = obtenerSiguienteSemaforo(miColor);
                
                miSemaforo.acquire();
                
                if (!running) {
                    siguienteSemaforo.release();
                    break;
                }
                
                System.out.println("Semáforo: " + miColor);
                
                switch (miColor) {
                    case ROJO:
                        Thread.sleep(3000);
                        break;
                    case VERDE:
                        Thread.sleep(3000);
                        break;
                    case AMBAR:
                        Thread.sleep(1000);
                        break;
                }
                
                siguienteSemaforo.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Obtiene el semáforo correspondiente al color dado
     * @param color el color del cual obtener su semáforo
     * @return el semáforo asociado al color
     */
    private Semaphore obtenerSemaforo(Color color) {
        switch (color) {
            case ROJO:
                return semaforoRojo;
            case VERDE:
                return semaforoVerde;
            case AMBAR:
                return semaforoAmbar;
            default:
                throw new IllegalStateException("Color desconocido: " + color);
        }
    }
    
    /**
     * Obtiene el semáforo del siguiente color en la secuencia
     * @param color el color actual
     * @return el semáforo del siguiente color en la secuencia
     */
    private Semaphore obtenerSiguienteSemaforo(Color color) {
        switch (color) {
            case ROJO:
                return semaforoVerde;
            case VERDE:
                return semaforoAmbar;
            case AMBAR:
                return semaforoRojo;
            default:
                throw new IllegalStateException("Color desconocido: " + color);
        }
    }
    
    public void detener() {
        running = false;
    }
    
    /**
     * Método principal que ejecuta la simulación del semáforo mejorado durante 20 segundos
     * @param args argumentos de línea de comandos
     * @throws InterruptedException si el hilo es interrumpido
     */
    public static void main(String[] args) throws InterruptedException {
        ColorSemaforoMejorado semaforoRojo = new ColorSemaforoMejorado(Color.ROJO);
        ColorSemaforoMejorado semaforoVerde = new ColorSemaforoMejorado(Color.VERDE);
        ColorSemaforoMejorado semaforoAmbar = new ColorSemaforoMejorado(Color.AMBAR);
        
        Thread hiloRojo = new Thread(semaforoRojo);
        Thread hiloVerde = new Thread(semaforoVerde);
        Thread hiloAmbar = new Thread(semaforoAmbar);
        
        System.out.println("Iniciando simulación del semáforo mejorado (con alternancia)...");
        hiloRojo.start();
        hiloVerde.start();
        hiloAmbar.start();
        
        Thread.sleep(20000);
        
        System.out.println("\nDeteniendo semáforo después de 20 segundos...");
        semaforoRojo.detener();
        semaforoVerde.detener();
        semaforoAmbar.detener();
        
        hiloRojo.interrupt();
        hiloVerde.interrupt();
        hiloAmbar.interrupt();
        
        hiloRojo.join();
        hiloVerde.join();
        hiloAmbar.join();
        
        System.out.println("Programa finalizado.");
    }
}
