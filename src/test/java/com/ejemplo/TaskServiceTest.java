package com.ejemplo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias para TaskService")
class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    @DisplayName("Crea una tarea con descripción válida")
    void createTask_validDescription_createsTask() {
        String description = "Tarea de prueba";
        Task task = taskService.createTask(description);

        assertNotNull(task);
        assertEquals(1L, task.getId());
        assertEquals(description, task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    @DisplayName("Lanza una excepción si la descripción es nula")
    void createTask_nullDescription_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(null);
        });
    }

    @Test
    @DisplayName("Lanza una excepción si la descripción está en blanco")
    void createTask_blankDescription_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask("   ");
        });
    }

    @Test
    @DisplayName("Encuentra una tarea existente por ID")
    void findTaskById_existingId_returnsTask() {
        Task created = taskService.createTask("Buscar por ID");
        Optional<Task> result = taskService.findTaskById(created.getId());

        assertTrue(result.isPresent());
        assertEquals(created.getId(), result.get().getId());
    }

    @Test
    @DisplayName("Devuelve vacío si el ID no existe")
    void findTaskById_nonExistingId_returnsEmpty() {
        Optional<Task> result = taskService.findTaskById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Elimina una tarea existente")
    void deleteTask_existingId_returnsTrue() {
        Task task = taskService.createTask("Eliminar esta");
        boolean deleted = taskService.deleteTask(task.getId());

        assertTrue(deleted);
        assertTrue(taskService.findTaskById(task.getId()).isEmpty());
    }

    @Test
    @DisplayName("No permite eliminar tarea inexistente")
    void deleteTask_nonExistingId_returnsFalse() {
        boolean result = taskService.deleteTask(12345L);
        assertFalse(result);
    }

    @Test
    @DisplayName("Devuelve todas las tareas creadas")
    void getAllTasks_returnsAllTasks() {
        taskService.createTask("Tarea 1");
        taskService.createTask("Tarea 2");

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
    }

    @Test
    @DisplayName("getAllTasks debería es inmutable")
    void getAllTasks_modificationDoesNotAffectOriginal() {
        taskService.createTask("No modificar");
        List<Task> tasks = taskService.getAllTasks();

        tasks.clear(); // Esto no debería afectar la colección interna

        assertEquals(1, taskService.getAllTasks().size());
    }

    @Test
    @DisplayName("Asigna IDs únicos a cada tarea")
    void createTask_generatesUniqueIds() {
        Task t1 = taskService.createTask("ID único 1");
        Task t2 = taskService.createTask("ID único 2");

        assertNotEquals(t1.getId(), t2.getId());
    }
}