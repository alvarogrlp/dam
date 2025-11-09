package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class CiudadEnPeligroTest {

    @Test
    public void testSoloNeutralizaElOtroSeDetiene() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        CiudadEnPeligro c = new CiudadEnPeligro();
        c.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        assertTrue(c.isAmenazaNeutralizada(), "La amenaza debe haber sido neutralizada");
        assertTrue(c.getGanador().equals("Superman") || c.getGanador().equals("Batman"), 
                   "El ganador debe ser Superman o Batman");
        
        int count = salida.split("Amenaza neutralizada", -1).length - 1;
        assertEquals(1, count, "Solo debe haber una neutralización de amenaza");
    }
}
