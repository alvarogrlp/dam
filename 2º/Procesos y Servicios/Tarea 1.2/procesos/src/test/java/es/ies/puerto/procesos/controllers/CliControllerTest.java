package es.ies.puerto.procesos.controllers;

import es.ies.puerto.procesos.domain.Job;
import es.ies.puerto.procesos.services.interfaces.CommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CliControllerTest {

    @Mock
    private CommandService commandService1;

    @Mock
    private CommandService commandService2;

    @Mock
    private CommandService commandService3;

    private CliController cliController;

    @BeforeEach
    void setUp() {
        List<CommandService> services = Arrays.asList(
                commandService1, 
                commandService2, 
                commandService3
        );
        cliController = new CliController(services);
    }

    @Test
    void testRunWithExitOption() throws Exception {
        when(commandService1.getName()).thenReturn("Service 1");
        when(commandService2.getName()).thenReturn("Service 2");
        when(commandService3.getName()).thenReturn("Service 3");
        
        String input = "0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        cliController = new CliController(Arrays.asList(commandService1, commandService2, commandService3));
        cliController.run();
        
        // Verificar que no se ejecutó ningún servicio
        verify(commandService1, never()).execute();
        verify(commandService2, never()).execute();
        verify(commandService3, never()).execute();
    }

    @Test
    void testRunWithCommandExecution() throws Exception {
        Job mockJob = new Job("test-id", "test-command");
        mockJob.setExitCode(0);
        mockJob.setSuccess(true);
        
        when(commandService1.execute()).thenReturn(mockJob);
        when(commandService1.getCommand()).thenReturn("test command");
        when(commandService1.getName()).thenReturn("Test Service");
        when(commandService2.getName()).thenReturn("Service 2");
        when(commandService3.getName()).thenReturn("Service 3");
        
        String input = "1\n\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        cliController = new CliController(Arrays.asList(commandService1, commandService2, commandService3));
        cliController.run();
        
        verify(commandService1, times(1)).execute();
    }

    @Test
    void testRunWithInvalidOption() throws Exception {
        when(commandService1.getName()).thenReturn("Service 1");
        when(commandService2.getName()).thenReturn("Service 2");
        when(commandService3.getName()).thenReturn("Service 3");
        
        String input = "99\n\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        cliController = new CliController(Arrays.asList(commandService1, commandService2, commandService3));
        cliController.run();
        
        verify(commandService1, never()).execute();
    }

    @Test
    void testRunWithEmptyInput() throws Exception {
        when(commandService1.getName()).thenReturn("Service 1");
        when(commandService2.getName()).thenReturn("Service 2");
        when(commandService3.getName()).thenReturn("Service 3");
        
        String input = "\n\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        cliController = new CliController(Arrays.asList(commandService1, commandService2, commandService3));
        cliController.run();
        
        verify(commandService1, never()).execute();
    }

    @Test
    void testRunWithCommandException() throws Exception {
        when(commandService1.execute()).thenThrow(new RuntimeException("Command failed"));
        when(commandService1.getCommand()).thenReturn("failing command");
        when(commandService1.getName()).thenReturn("Failing Service");
        when(commandService2.getName()).thenReturn("Service 2");
        when(commandService3.getName()).thenReturn("Service 3");
        
        String input = "1\n\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        cliController = new CliController(Arrays.asList(commandService1, commandService2, commandService3));
        cliController.run();
        
        verify(commandService1, times(1)).execute();
    }

    @Test
    void testRunWithCustomCommandValid() throws Exception {
        Job mockJob = new Job("test-id", "lsof -i");
        mockJob.setExitCode(0);
        mockJob.setSuccess(true);
        
        when(commandService1.getCommand()).thenReturn("lsof -i");
        when(commandService1.execute()).thenReturn(mockJob);
        when(commandService1.getName()).thenReturn("lsof Service");
        when(commandService2.getName()).thenReturn("Service 2");
        when(commandService3.getName()).thenReturn("Service 3");
        
        String input = "4\nlsof -i\n\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        cliController = new CliController(Arrays.asList(commandService1, commandService2, commandService3));
        cliController.run();
        
        verify(commandService1, times(1)).execute();
    }

    @Test
    void testRunWithCustomCommandInvalid() throws Exception {
        when(commandService1.getName()).thenReturn("Service 1");
        when(commandService2.getName()).thenReturn("Service 2");
        when(commandService3.getName()).thenReturn("Service 3");
        
        String input = "4\nls -la\n\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        cliController = new CliController(Arrays.asList(commandService1, commandService2, commandService3));
        cliController.run();
        
        // Verificar que no se ejecutó el servicio porque el comando no es válido
        verify(commandService1, never()).execute();
    }

    @Test
    void testRunWithCustomCommandEmpty() throws Exception {
        when(commandService1.getName()).thenReturn("Service 1");
        when(commandService2.getName()).thenReturn("Service 2");
        when(commandService3.getName()).thenReturn("Service 3");
        
        String input = "4\n\n\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        cliController = new CliController(Arrays.asList(commandService1, commandService2, commandService3));
        cliController.run();
        
        verify(commandService1, never()).execute();
    }
}
