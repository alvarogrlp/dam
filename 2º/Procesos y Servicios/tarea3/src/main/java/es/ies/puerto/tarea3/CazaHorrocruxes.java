package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CazaHorrocruxes {
    private final AtomicBoolean encontrado = new AtomicBoolean(false);
    private final AtomicReference<String> ganador = new AtomicReference<>(null);

    class Buscador implements Runnable {
        private final String nombre;
        private final String ubicacion;

        public Buscador(String nombre, String ubicacion) {
            this.nombre = nombre;
            this.ubicacion = ubicacion;
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

            if (!encontrado.get()) {
                if (encontrado.compareAndSet(false, true)) {
                    ganador.set(nombre);
                    System.out.println(nombre + " encontró un Horrocrux en " + ubicacion + ". ¡Búsqueda terminada!");
                }
            }
        }
    }

    public void main() throws InterruptedException {
        Thread t1 = new Thread(new Buscador("Harry", "Bosque Prohibido"));
        Thread t2 = new Thread(new Buscador("Hermione", "Biblioteca Antigua"));
        Thread t3 = new Thread(new Buscador("Ron", "Mazmorras del castillo"));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

    public boolean isEncontrado() {
        return encontrado.get();
    }

    public String getGanador() {
        return ganador.get();
    }

    public static void main(String[] args) {
        try {
            CazaHorrocruxes caza = new CazaHorrocruxes();
            caza.main();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
