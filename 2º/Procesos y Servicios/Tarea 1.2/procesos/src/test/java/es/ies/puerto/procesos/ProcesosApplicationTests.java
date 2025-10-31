package es.ies.puerto.procesos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ProcesosApplication
 * Tests basic application structure without loading full Spring context
 */
class ProcesosApplicationTests {

	@Test
	void testApplicationClassExists() {
		// Test that the main application class exists and can be instantiated
		ProcesosApplication app = new ProcesosApplication();
		assertNotNull(app);
	}

	@Test
	void testMainMethodExists() throws NoSuchMethodException {
		// Test that the main method exists
		assertNotNull(ProcesosApplication.class.getMethod("main", String[].class));
	}

}
