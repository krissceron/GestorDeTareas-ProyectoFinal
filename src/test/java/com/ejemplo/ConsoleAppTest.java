package com.ejemplo;

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

    @Test
    @DisplayName("Simula eliminar una tarea existente")
    void simulateDeleteTask() {
        String simulatedInput = String.join(System.lineSeparator(),
                "1",                    // Crear tarea
                "Eliminar esta",       // Descripción
                "",                    // Enter
                "6",                   // Eliminar
                "1",                   // ID
                "s",                   // Confirmar eliminación
                "",                    // Enter
                "0"                    // Salir
        );
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
    
        ConsoleApp app = new ConsoleApp();
        app.run();
    
        String output = testOut.toString(StandardCharsets.UTF_8);
    
        assertTrue(output.contains("Tarea eliminada exitosamente"));
        assertFalse(output.contains("Error al eliminar la tarea"));
    }
    
    @Test
    @DisplayName("Simula marcar una tarea como completada")
    void simulateMarkTaskAsCompleted() {
        String simulatedInput = String.join(System.lineSeparator(),
                "1",                 // Crear tarea
                "Completar",         // Descripción
                "",                  // Enter
                "4",                 // Marcar como completada
                "1",                 // ID
                "",                  // Enter
                "7",                 // Mostrar completadas
                "",                  // Enter
                "0"                  // Salir
        );
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
    
        ConsoleApp app = new ConsoleApp();
        app.run();
    
        String output = testOut.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Tarea marcada como completada"));
        assertTrue(output.contains("TAREAS COMPLETADAS"));
        assertTrue(output.contains("Completar"));
    }
    
    @Test
    @DisplayName("Simula mostrar tareas pendientes")
    void simulateShowPendingTasks() {
        String simulatedInput = String.join(System.lineSeparator(),
                "1",             // Crear tarea
                "Pendiente",     // Descripción
                "",              // Enter
                "8",             // Ver pendientes
                "",              // Enter
                "0"              // Salir
        );
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
    
        ConsoleApp app = new ConsoleApp();
        app.run();
    
        String output = testOut.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("TAREAS PENDIENTES"));
        assertTrue(output.contains("Pendiente"));
    }
    
    @Test
    @DisplayName("Simula buscar una tarea existente")
    void simulateFindTaskById() {
        String simulatedInput = String.join(System.lineSeparator(),
                "1",         // Crear tarea
                "Buscarme",  // Descripción
                "",          // Enter
                "3",         // Buscar
                "1",         // ID
                "",          // Enter
                "0"          // Salir
        );
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
    
        ConsoleApp app = new ConsoleApp();
        app.run();
    
        String output = testOut.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Tarea encontrada"));
        assertTrue(output.contains("Buscarme"));
    }
 
    @Test
    @DisplayName("Simula listar todas las tareas")
    void simulateListAllTasks() {
    String simulatedInput = String.join(System.lineSeparator(),
            "1",             // Crear tarea 1
            "Primera",       // Descripción
            "",              // Enter
            "1",             // Crear tarea 2
            "Segunda",       // Descripción
            "",              // Enter
            "2",             // Listar todas
            "",              // Enter
            "0"              // Salir
    );
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
 
    ConsoleApp app = new ConsoleApp();
    app.run();
 
    String output = testOut.toString(StandardCharsets.UTF_8);
    assertTrue(output.contains("Primera"));
    assertTrue(output.contains("Segunda"));
    assertTrue(output.contains("Total de tareas: 2"));
    }
}