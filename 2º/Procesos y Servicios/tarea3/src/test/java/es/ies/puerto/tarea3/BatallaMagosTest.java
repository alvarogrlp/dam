package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class BatallaMagosTest {

    @Test
    public void testDebeHaberGanadorYTerminar() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        BatallaMagos b = new BatallaMagos();
        b.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        assertTrue(salida.contains("gana la batalla mágica."), "Debe haber un ganador");
        assertTrue(b.isCombateTerminado(), "El combate debe haber terminado");
        assertTrue(b.getEnergiaGandalf() <= 0 || b.getEnergiaSaruman() <= 0, 
                   "Al menos un mago debe tener energía <= 0");
    }
}
