package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BatallaMagos {
    private final AtomicInteger energiaGandalf = new AtomicInteger(120);
    private final AtomicInteger energiaSaruman = new AtomicInteger(120);
    private final AtomicBoolean combateTerminado = new AtomicBoolean(false);
    private final Object m = new Object();

    private void lanzarHechizo(String atacante, AtomicInteger energiaRival) {
        Random random = new Random();
        int dano = random.nextInt(18) + 8; // 8-25
        int nuevaEnergia = energiaRival.addAndGet(-dano);
        System.out.println(atacante + " lanza hechizo por " + dano + ". Energía rival: " + nuevaEnergia);
        
        if (nuevaEnergia <= 0 && !combateTerminado.get()) {
            if (combateTerminado.compareAndSet(false, true)) {
                System.out.println(atacante + " gana la batalla mágica.");
            }
        }
    }

    class Gandalf implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!combateTerminado.get()) {
                synchronized (m) {
                    if (!combateTerminado.get()) {
                        lanzarHechizo("Gandalf", energiaSaruman);
                    }
                }
                try {
                    Thread.sleep(random.nextInt(401) + 200); // 200-600ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    class Saruman implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (!combateTerminado.get()) {
                synchronized (m) {
                    if (!combateTerminado.get()) {
                        lanzarHechizo("Saruman", energiaGandalf);
                    }
                }
                try {
                    Thread.sleep(random.nextInt(401) + 200); // 200-600ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void main() throws InterruptedException {
        Thread t1 = new Thread(new Gandalf());
        Thread t2 = new Thread(new Saruman());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public boolean isCombateTerminado() {
        return combateTerminado.get();
    }

    public int getEnergiaGandalf() {
        return energiaGandalf.get();
    }

    public int getEnergiaSaruman() {
        return energiaSaruman.get();
    }

    public static void main(String[] args) {
        try {
            BatallaMagos b = new BatallaMagos();
            b.main();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
