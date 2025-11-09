package es.ies.puerto.tarea3;

import java.util.Random;

public class SaiyanRace implements Runnable {
    private String name;
    private int distance = 0;
    private static final int GOAL = 100;
    private static volatile boolean winnerDeclared = false;

    public SaiyanRace(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (distance < GOAL && !winnerDeclared) {
            int step = random.nextInt(10) + 1; // Avance aleatorio de 1 a 10
            distance += step;
            System.out.println(name + " avanzó " + step + " metros. Distancia total: " + distance + " metros.");

            if (distance >= GOAL && !winnerDeclared) {
                winnerDeclared = true;
                System.out.println(name + " ha ganado la carrera!");
            }
            try {
                Thread.sleep(500); // Pausa entre pasos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread goku = new Thread(new SaiyanRace("Goku"));
        Thread vegeta = new Thread(new SaiyanRace("Vegeta"));

        goku.start();
        vegeta.start();
    }
}