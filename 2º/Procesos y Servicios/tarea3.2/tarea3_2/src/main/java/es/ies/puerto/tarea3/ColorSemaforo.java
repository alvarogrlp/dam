package es.ies.puerto.tarea3;

/**
 * Simula un semáforo de tráfico con tres estados: ROJO, ÁMBAR, VERDE.
 * El semáforo cambia automáticamente después de ciertos intervalos de tiempo.
 * @author Álvaro
 * @version 1.0
 */
public class ColorSemaforo implements Runnable {
    
    private enum Color {
        ROJO, AMBAR, VERDE
    }
    
    private Color colorActual;
    private volatile boolean running;
    
    public ColorSemaforo() {
        this.colorActual = Color.ROJO;
        this.running = true;
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                System.out.println("Semáforo: " + colorActual);
                
                switch (colorActual) {
                    case ROJO:
                        Thread.sleep(3000);
                        colorActual = Color.VERDE;
                        break;
                    case VERDE:
                        Thread.sleep(3000);
                        colorActual = Color.AMBAR;
                        break;
                    case AMBAR:
                        Thread.sleep(1000);
                        colorActual = Color.ROJO;
                        break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
        System.out.println("Semáforo detenido.");
    }
    
    public void detener() {
        running = false;
    }
    
    /**
     * Método principal que ejecuta la simulación del semáforo durante 20 segundos
     * @param args argumentos de línea de comandos
     * @throws InterruptedException si el hilo es interrumpido
     */
    public static void main(String[] args) throws InterruptedException {
        ColorSemaforo semaforo = new ColorSemaforo();
        Thread hiloSemaforo = new Thread(semaforo);
        
        System.out.println("Iniciando simulación del semáforo...");
        hiloSemaforo.start();
        
        Thread.sleep(20000);
        
        System.out.println("Deteniendo semáforo después de 20 segundos...");
        semaforo.detener();
        hiloSemaforo.interrupt();
        hiloSemaforo.join();
        
        System.out.println("Programa finalizado.");
    }
}
