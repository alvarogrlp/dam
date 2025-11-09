package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class FuerzaThorHulkTest {

    @Test
    public void testTerminaPorTiempoYDeclaraResultado() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        FuerzaThorHulk f = new FuerzaThorHulk();
        f.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        assertTrue(salida.contains("¡Tiempo!"), "Debe indicar que el tiempo terminó");
        assertTrue(salida.contains("gana") || salida.contains("Empate"), 
                   "Debe declarar un ganador o empate");
        assertTrue(f.isTiempoTerminado(), "El tiempo debe haber terminado");
    }
}
