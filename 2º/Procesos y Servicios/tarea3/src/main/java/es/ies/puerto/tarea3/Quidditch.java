package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Quidditch {
    private final AtomicBoolean snitchAtrapada = new AtomicBoolean(false);
    private int puntosEquipoA = 0;
    private int puntosEquipoB = 0;
    private final Object m = new Object();

    class CazadorA implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!snitchAtrapada.get()) {
                try {
                    Thread.sleep(random.nextInt(301) + 200); // 200-500ms
                    int g = random.nextInt(2) * 10; // 0 o 10
                    if (g > 0) {
                        synchronized (m) {
                            puntosEquipoA += g;
                            System.out.println("Equipo A anota 10. Total A=" + puntosEquipoA);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    class CazadorB implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!snitchAtrapada.get()) {
                try {
                    Thread.sleep(random.nextInt(301) + 200); // 200-500ms
                    int g = random.nextInt(2) * 10; // 0 o 10
                    if (g > 0) {
                        synchronized (m) {
                            puntosEquipoB += g;
                            System.out.println("Equipo B anota 10. Total B=" + puntosEquipoB);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    class Buscador implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!snitchAtrapada.get()) {
                try {
                    Thread.sleep(random.nextInt(401) + 300); // 300-700ms
                    if (random.nextInt(100) + 1 <= 15) { // 15% probabilidad
                        snitchAtrapada.set(true);
                        System.out.println("¡Snitch dorada atrapada!");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void main() throws InterruptedException {
        Thread t1 = new Thread(new CazadorA());
        Thread t2 = new Thread(new CazadorB());
        Thread t3 = new Thread(new Buscador());

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Marcador final: A=" + puntosEquipoA + " B=" + puntosEquipoB);
    }

    public boolean isSnitchAtrapada() {
        return snitchAtrapada.get();
    }

    public int getPuntosEquipoA() {
        return puntosEquipoA;
    }

    public int getPuntosEquipoB() {
        return puntosEquipoB;
    }

    public static void main(String[] args) {
        try {
            Quidditch q = new Quidditch();
            q.main();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
