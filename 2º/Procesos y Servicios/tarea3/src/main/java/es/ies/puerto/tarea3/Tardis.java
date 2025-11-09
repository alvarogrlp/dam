package es.ies.puerto.tarea3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Tardis {
    private final AtomicBoolean destinoAlcanzado = new AtomicBoolean(false);
    private final AtomicReference<String> eraGanadora = new AtomicReference<>(null);

    class Viaje implements Runnable {
        private final String era;

        public Viaje(String era) {
            this.era = era;
        }

        @Override
        public void run() {
            Random random = new Random();
            int tiempo = random.nextInt(1501) + 500; // 500-2000ms
            
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (!destinoAlcanzado.get()) {
                if (destinoAlcanzado.compareAndSet(false, true)) {
                    eraGanadora.set(era);
                    System.out.println("La TARDIS llegó primero a " + era);
                }
            }
        }
    }

    public void main() throws InterruptedException {
        String[] eras = {"Roma Antigua", "Futuro Lejano", "Era Victoriana", "Año 3000"};
        List<Thread> hilos = new ArrayList<>();
        
        for (String e : eras) {
            Thread t = new Thread(new Viaje(e));
            hilos.add(t);
            t.start();
        }
        
        for (Thread t : hilos) {
            t.join();
        }
    }

    public boolean isDestinoAlcanzado() {
        return destinoAlcanzado.get();
    }

    public String getEraGanadora() {
        return eraGanadora.get();
    }

    public static void main(String[] args) {
        try {
            Tardis t = new Tardis();
            t.main();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
