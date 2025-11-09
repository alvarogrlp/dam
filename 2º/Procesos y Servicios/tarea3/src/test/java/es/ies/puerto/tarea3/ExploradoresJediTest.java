package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class ExploradoresJediTest {

    @Test
    public void testUnSoloGanador() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ExploradoresJedi e = new ExploradoresJedi();
        e.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        assertTrue(e.isPistaEncontrada(), "La pista debe haber sido encontrada");
        
        int count = salida.split("halló una pista", -1).length - 1;
        assertEquals(1, count, "Solo debe haber un hallazgo de pista");
    }
}
