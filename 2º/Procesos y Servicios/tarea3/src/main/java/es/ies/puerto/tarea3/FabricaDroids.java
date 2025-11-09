package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FabricaDroids {
    private final BlockingQueue<String> ensamblados = new LinkedBlockingQueue<>();
    private final int N = 10;
    private final AtomicInteger activados = new AtomicInteger(0);

    class Ensamblador implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            for (int i = 1; i <= N; i++) {
                try {
                    Thread.sleep(random.nextInt(201) + 100); // 100-300ms
                    String d = "Droid-" + i;
                    System.out.println("Ensamblado " + d);
                    ensamblados.put(d);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    class Activador implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            int cuenta = 0;
            while (cuenta < N) {
                try {
                    String d = ensamblados.take();
                    System.out.println("Activado " + d);
                    activados.incrementAndGet();
                    cuenta++;
                    Thread.sleep(random.nextInt(101) + 50); // 50-150ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void main() throws InterruptedException {
        Thread tE = new Thread(new Ensamblador());
        Thread tA = new Thread(new Activador());

        tE.start();
        tA.start();

        tE.join();
        tA.join();
    }

    public int getN() {
        return N;
    }

    public int getActivados() {
        return activados.get();
    }

    public static void main(String[] args) {
        try {
            FabricaDroids fabrica = new FabricaDroids();
            fabrica.main();
            System.out.println("Total activados: " + fabrica.getActivados());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
