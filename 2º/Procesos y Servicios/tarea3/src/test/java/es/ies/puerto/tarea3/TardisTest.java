package es.ies.puerto.tarea3;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class TardisTest {

    @Test
    public void testExisteUnaEraGanadora() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Tardis t = new Tardis();
        t.main();

        System.setOut(originalOut);
        String salida = outContent.toString();

        assertTrue(t.isDestinoAlcanzado(), "El destino debe haber sido alcanzado");
        assertNotNull(t.getEraGanadora(), "Debe haber una era ganadora");
        
        int count = salida.split("llegó primero", -1).length - 1;
        assertEquals(1, count, "Solo debe haber una era que llegó primero");
    }
}
