package com.ejemplo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias para Task")
class TaskTest {

  @Test
  @DisplayName("Constructor con estado completado asigna los valores correctamente")
  void constructorWithCompleted_setsValuesCorrectly() {
    Task task = new Task(1L, "Descripción", true);

    assertEquals(1L, task.getId());
    assertEquals("Descripción", task.getDescription());
    assertTrue(task.isCompleted());
  }

  @Test
  @DisplayName("Constructor sin estado completado lo asigna como falso por defecto")
  void constructorWithoutCompleted_setsCompletedFalseByDefault() {
    Task task = new Task(2L, "Descripción por defecto");

    assertEquals(2L, task.getId());
    assertEquals("Descripción por defecto", task.getDescription());
    assertFalse(task.isCompleted());
  }

  @Test
  @DisplayName("Setters modifican la descripción y estado correctamente")
  void setters_modifyDescriptionAndCompleted() {
    Task task = new Task(3L, "Original");

    task.setDescription("Modificada");
    task.setCompleted(true);

    assertEquals("Modificada", task.getDescription());
    assertTrue(task.isCompleted());
  }

  @Test
  @DisplayName("markAsCompleted marca la tarea como completada")
  void markAsCompleted_setsCompletedTrue() {
    Task task = new Task(4L, "Completar");
    task.markAsCompleted();

    assertTrue(task.isCompleted());
  }

  @Test
  @DisplayName("markAsIncomplete marca la tarea como no completada")
  void markAsIncomplete_setsCompletedFalse() {
    Task task = new Task(5L, "Incompleta", true);
    task.markAsIncomplete();

    assertFalse(task.isCompleted());
  }

  @Test
  @DisplayName("equals devuelve true para tareas con el mismo ID")
  void equals_sameId_returnsTrue() {
    Task task1 = new Task(6L, "Tarea A");
    Task task2 = new Task(6L, "Tarea B");

    assertEquals(task1, task2);
  }

  @Test
  @DisplayName("equals devuelve false para tareas con distinto ID")
  void equals_differentId_returnsFalse() {
    Task task1 = new Task(7L, "Tarea A");
    Task task2 = new Task(8L, "Tarea B");

    assertNotEquals(task1, task2);
  }

  @Test
  @DisplayName("equals devuelve false si se compara con null o tipo distinto")
  void equals_withNullOrDifferentType_returnsFalse() {
    Task task = new Task(9L, "Tarea");

    assertNotEquals(task, null);
    assertNotEquals(task, "No es una tarea");
  }

  @Test
  @DisplayName("hashCode es consistente con equals")
  void hashCode_isConsistentWithEquals() {
    Task task1 = new Task(10L, "Uno");
    Task task2 = new Task(10L, "Dos");

    assertEquals(task1, task2);
    assertEquals(task1.hashCode(), task2.hashCode());
  }

  @Test
  @DisplayName("toString contiene información relevante")
  void toString_containsExpectedContent() {
    Task task = new Task(11L, "Texto", true);
    String result = task.toString();

    assertTrue(result.contains("11"));
    assertTrue(result.contains("Texto"));
    assertTrue(result.contains("true"));
  }
}