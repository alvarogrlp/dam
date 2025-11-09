package es.ies.puerto.tarea3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ExploradoresJedi {
    private final AtomicBoolean pistaEncontrada = new AtomicBoolean(false);
    private final AtomicReference<String> ganador = new AtomicReference<>(null);

    class Jedi implements Runnable {
        private final String nombre;
        private final String planeta;

        public Jedi(String nombre, String planeta) {
            this.nombre = nombre;
            this.planeta = planeta;
        }

        @Override
        public void run() {
            Random random = new Random();
            int tiempo = random.nextInt(1101) + 400; // 400-1500ms
            
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (!pistaEncontrada.get()) {
                if (pistaEncontrada.compareAndSet(false, true)) {
                    ganador.set(nombre);
                    System.out.println(nombre + " halló una pista en " + planeta + ". Fin de búsqueda.");
                }
            }
        }
    }

    public void main() throws InterruptedException {
        Thread t1 = new Thread(new Jedi("Kenobi", "Tatooine"));
        Thread t2 = new Thread(new Jedi("Skywalker", "Dagobah"));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public boolean isPistaEncontrada() {
        return pistaEncontrada.get();
    }

    public String getGanador() {
        return ganador.get();
    }

    public static void main(String[] args) {
        try {
            ExploradoresJedi e = new ExploradoresJedi();
            e.main();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
