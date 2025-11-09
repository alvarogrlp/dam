package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class BatallaPokemon {
    // Estado compartido
    private final AtomicBoolean juegoTerminado = new AtomicBoolean(false);
    private volatile int hpPikachu = 100;
    private volatile int hpCharmander = 100;
    private volatile String turno = "Pikachu"; // Alternancia estricta
    private final Object mutex = new Object(); // Mutex para sincronización
    private final Random random = new Random();

    // Método para atacar
    private void atacar(String atacante, boolean esPikachu) {
        int danio = random.nextInt(16) + 5; // Daño entre 5 y 20
        
        if (esPikachu) {
            hpCharmander -= danio;
            System.out.println(atacante + " ataca con " + danio + " de daño. HP Charmander: " + Math.max(0, hpCharmander));
            
            if (hpCharmander <= 0 && !juegoTerminado.get()) {
                juegoTerminado.set(true);
                System.out.println(atacante + " ha ganado la batalla!");
            }
        } else {
            hpPikachu -= danio;
            System.out.println(atacante + " ataca con " + danio + " de daño. HP Pikachu: " + Math.max(0, hpPikachu));
            
            if (hpPikachu <= 0 && !juegoTerminado.get()) {
                juegoTerminado.set(true);
                System.out.println(atacante + " ha ganado la batalla!");
            }
        }
        
        try {
            Thread.sleep(random.nextInt(401) + 200); // Sleep entre 200 y 600ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Runnable para Pikachu
    class HiloPikachu implements Runnable {
        @Override
        public void run() {
            while (!juegoTerminado.get()) {
                synchronized (mutex) {
                    // Esperar mientras no sea el turno de Pikachu
                    while (!turno.equals("Pikachu") && !juegoTerminado.get()) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    
                    if (juegoTerminado.get()) {
                        break;
                    }
                    
                    // Atacar
                    atacar("Pikachu", true);
                    
                    // Cambiar turno
                    turno = "Charmander";
                    mutex.notifyAll(); // Notificar al otro hilo
                }
            }
        }
    }

    // Runnable para Charmander
    class HiloCharmander implements Runnable {
        @Override
        public void run() {
            while (!juegoTerminado.get()) {
                synchronized (mutex) {
                    // Esperar mientras no sea el turno de Charmander
                    while (!turno.equals("Charmander") && !juegoTerminado.get()) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    
                    if (juegoTerminado.get()) {
                        break;
                    }
                    
                    // Atacar
                    atacar("Charmander", false);
                    
                    // Cambiar turno
                    turno = "Pikachu";
                    mutex.notifyAll(); // Notificar al otro hilo
                }
            }
        }
    }

    // Método principal para iniciar la batalla
    public void iniciarBatalla() throws InterruptedException {
        Thread t1 = new Thread(new HiloPikachu());
        Thread t2 = new Thread(new HiloCharmander());
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        System.out.println("\n¡Batalla terminada!");
    }

    // Main
    public static void main(String[] args) {
        try {
            BatallaPokemon batalla = new BatallaPokemon();
            batalla.iniciarBatalla();
        } catch (InterruptedException e) {
            System.err.println("Batalla interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
