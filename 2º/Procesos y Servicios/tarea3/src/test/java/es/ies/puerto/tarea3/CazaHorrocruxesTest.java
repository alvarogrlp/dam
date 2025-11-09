package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class CazaHorrocruxesTest {

    @Test
    public void testUnGanadorYUnSoloHallazgo() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        CazaHorrocruxes sim = new CazaHorrocruxes();
        sim.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        assertTrue(sim.isEncontrado(), "El Horrocrux debe haber sido encontrado");
        assertTrue(sim.getGanador().equals("Harry") || 
                   sim.getGanador().equals("Hermione") || 
                   sim.getGanador().equals("Ron"), "El ganador debe ser Harry, Hermione o Ron");
        
        int count = salida.split("encontró un Horrocrux", -1).length - 1;
        assertEquals(1, count, "Debe haber exactamente un hallazgo de Horrocrux");
    }
}
