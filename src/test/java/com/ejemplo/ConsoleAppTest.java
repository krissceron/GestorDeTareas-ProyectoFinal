import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias para ConsoleApp")
class ConsoleAppTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUpStreams() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Simula crear una tarea y salir")
    void simulateCreateTaskAndExit() {
        // Simula: 1 (crear tarea), "Tarea de prueba", enter, 0 (salir)
        String simulatedInput = String.join(System.lineSeparator(),
                "1",
                "Tarea de prueba",
                "",  // Enter para continuar
                "0"  // salir
        );

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        ConsoleApp app = new ConsoleApp();
        app.run();

        String output = testOut.toString(StandardCharsets.UTF_8);

        // Verifica que se haya creado la tarea con el texto
        assertTrue(output.contains("Tarea creada exitosamente"));
        assertTrue(output.contains("Tarea de prueba"));
        assertTrue(output.contains("Hasta luego"));
    }

    @Test
    @DisplayName("Simula opción inválida en el menú")
    void simulateInvalidOption() {
        String simulatedInput = String.join(System.lineSeparator(),
                "abc",   // opción no numérica
                "",      // enter
                "0"      // salir
        );

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        ConsoleApp app = new ConsoleApp();
        app.run();

        String output = testOut.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("Opción inválida"));
        assertTrue(output.contains("Hasta luego"));
    }

    @Test
    @DisplayName("Simula listar tareas vacías")
    void simulateListEmptyTasks() {
        String simulatedInput = String.join(System.lineSeparator(),
                "2",  // listar todas las tareas
                "",   // enter
                "0"   // salir
        );

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        ConsoleApp app = new ConsoleApp();
        app.run();

        String output = testOut.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("No hay tareas registradas"));
    }
}