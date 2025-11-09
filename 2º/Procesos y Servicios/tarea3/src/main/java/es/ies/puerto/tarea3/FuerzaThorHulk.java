package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FuerzaThorHulk {
    private final int durationMS = 5000;
    private final AtomicBoolean tiempoTerminado = new AtomicBoolean(false);
    private final AtomicInteger totalThor = new AtomicInteger(0);
    private final AtomicInteger totalHulk = new AtomicInteger(0);

    class Temporizador implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(durationMS);
                tiempoTerminado.set(true);
                System.out.println("¡Tiempo!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    class Thor implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!tiempoTerminado.get()) {
                int peso = random.nextInt(16) + 5; // 5-20
                totalThor.addAndGet(peso);
                try {
                    Thread.sleep(random.nextInt(71) + 50); // 50-120ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    class Hulk implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!tiempoTerminado.get()) {
                int peso = random.nextInt(16) + 5; // 5-20
                totalHulk.addAndGet(peso);
                try {
                    Thread.sleep(random.nextInt(71) + 50); // 50-120ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void main() throws InterruptedException {
        Thread t0 = new Thread(new Temporizador());
        Thread t1 = new Thread(new Thor());
        Thread t2 = new Thread(new Hulk());

        t0.start();
        t1.start();
        t2.start();

        t0.join();
        t1.join();
        t2.join();

        int thor = totalThor.get();
        int hulk = totalHulk.get();
        
        if (thor > hulk) {
            System.out.println("Thor gana con " + thor + " vs " + hulk);
        } else if (hulk > thor) {
            System.out.println("Hulk gana con " + hulk + " vs " + thor);
        } else {
            System.out.println("Empate: " + thor);
        }
    }

    public int getTotalThor() {
        return totalThor.get();
    }

    public int getTotalHulk() {
        return totalHulk.get();
    }

    public boolean isTiempoTerminado() {
        return tiempoTerminado.get();
    }

    public static void main(String[] args) {
        try {
            FuerzaThorHulk f = new FuerzaThorHulk();
            f.main();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
