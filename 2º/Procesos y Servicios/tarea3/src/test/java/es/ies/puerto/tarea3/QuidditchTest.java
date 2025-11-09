package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class QuidditchTest {

    @Test
    public void testTerminaCuandoSnitchAtrapada() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Quidditch q = new Quidditch();
        q.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        assertTrue(salida.contains("¡Snitch dorada atrapada!"), "La snitch debe haber sido atrapada");
        assertTrue(salida.contains("Marcador final:"), "Debe mostrar el marcador final");
        assertTrue(q.isSnitchAtrapada(), "El estado debe indicar que la snitch fue atrapada");
    }
}
