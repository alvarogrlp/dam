package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CiudadEnPeligro {
    private final AtomicBoolean amenazaNeutralizada = new AtomicBoolean(false);
    private final String[] zonasA = {"Norte", "Centro", "Este"};
    private final String[] zonasB = {"Oeste", "Sur"};
    private final AtomicReference<String> ganador = new AtomicReference<>(null);

    class Superman implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            for (String zona : zonasA) {
                if (amenazaNeutralizada.get()) break;
                try {
                    Thread.sleep(random.nextInt(301) + 200); // 200-500ms
                    System.out.println("Superman salvó " + zona);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (!amenazaNeutralizada.get()) {
                if (amenazaNeutralizada.compareAndSet(false, true)) {
                    ganador.set("Superman");
                    System.out.println("Amenaza neutralizada por Superman");
                }
            }
        }
    }

    class Batman implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            for (String zona : zonasB) {
                if (amenazaNeutralizada.get()) break;
                try {
                    Thread.sleep(random.nextInt(301) + 300); // 300-600ms
                    System.out.println("Batman salvó " + zona);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (!amenazaNeutralizada.get()) {
                if (amenazaNeutralizada.compareAndSet(false, true)) {
                    ganador.set("Batman");
                    System.out.println("Amenaza neutralizada por Batman");
                }
            }
        }
    }

    public void main() throws InterruptedException {
        Thread t1 = new Thread(new Superman());
        Thread t2 = new Thread(new Batman());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public boolean isAmenazaNeutralizada() {
        return amenazaNeutralizada.get();
    }

    public String getGanador() {
        return ganador.get();
    }

    public static void main(String[] args) {
        try {
            CiudadEnPeligro c = new CiudadEnPeligro();
            c.main();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
