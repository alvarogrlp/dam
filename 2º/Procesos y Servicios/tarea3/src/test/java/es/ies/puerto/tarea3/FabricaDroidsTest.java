package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class FabricaDroidsTest {

    @Test
    public void testNoSeActivaAntesDeEnsamblaryCuentaCorrecta() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        FabricaDroids fab = new FabricaDroids();
        fab.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        for (int k = 1; k <= fab.getN(); k++) {
            String droidId = "Droid-" + k;
            int idxE = salida.indexOf("Ensamblado " + droidId);
            int idxA = salida.indexOf("Activado " + droidId);
            
            assertTrue(idxE != -1, "Droid-" + k + " debe haber sido ensamblado");
            assertTrue(idxA != -1, "Droid-" + k + " debe haber sido activado");
            assertTrue(idxE < idxA, "Droid-" + k + " debe ser ensamblado antes de ser activado");
        }
        
        assertEquals(fab.getN(), fab.getActivados(), "Todos los droids deben estar activados");
    }
}
