package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MilleniumFalcon {
    private final AtomicBoolean fin = new AtomicBoolean(false);
    private final AtomicBoolean destruida = new AtomicBoolean(false);
    private final int tiempoMisionMS = 4000;
    private long inicio;
    
    private final AtomicInteger velocidad = new AtomicInteger(0);
    private final AtomicInteger escudos = new AtomicInteger(100);

    class HanSolo implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!fin.get()) {
                velocidad.addAndGet(random.nextInt(11) + 5); // 5-15
                if (random.nextInt(100) + 1 <= 5) { // 5% probabilidad
                    destruida.set(true);
                    fin.set(true);
                    System.out.println("Fallo de hiperimpulsor. ¡La nave se destruye!");
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                if (System.currentTimeMillis() - inicio >= tiempoMisionMS) {
                    fin.set(true);
                }
            }
        }
    }

    class Chewbacca implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!fin.get()) {
                int cambio = random.nextInt(16) - 10; // -10 a +5
                escudos.addAndGet(cambio);
                if (escudos.get() <= 0) {
                    destruida.set(true);
                    fin.set(true);
                    System.out.println("¡Escudos agotados! La nave se destruye!");
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                if (System.currentTimeMillis() - inicio >= tiempoMisionMS) {
                    fin.set(true);
                }
            }
        }
    }

    public void main() throws InterruptedException {
        inicio = System.currentTimeMillis();
        Thread t1 = new Thread(new HanSolo());
        Thread t2 = new Thread(new Chewbacca());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        if (!destruida.get()) {
            System.out.println("¡Han y Chewie escapan! Vel=" + velocidad.get() + ", Escudos=" + escudos.get());
        }
    }

    public boolean isDestruida() {
        return destruida.get();
    }

    public int getVelocidad() {
        return velocidad.get();
    }

    public int getEscudos() {
        return escudos.get();
    }

    public static void main(String[] args) {
        try {
            MilleniumFalcon m = new MilleniumFalcon();
            m.main();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
