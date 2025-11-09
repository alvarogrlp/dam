package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class MilleniumFalconTest {

    @Test
    public void testFinalizaConEscapeODestruccion() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        MilleniumFalcon m = new MilleniumFalcon();
        m.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        boolean destruida = salida.contains("se destruye");
        boolean escapan = salida.contains("escapan");
        
        assertTrue(destruida ^ escapan, "Debe terminar con escape o destrucción, pero no ambos");
    }
}
