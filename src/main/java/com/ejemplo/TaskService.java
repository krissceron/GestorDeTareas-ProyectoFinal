package com.ejemplo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Servicio que gestiona las tareas.
 */
public final class TaskService {
  private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);

  /**
   * Crea una nueva tarea con la descripción especificada.
   *
   * @param description la descripción de la tarea, no puede ser null ni estar en blanco
   * @return la nueva tarea creada con un ID único generado automáticamente
   * @throws IllegalArgumentException si la descripción es null o está en blanco
   */
  public Task createTask(final String description) {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("La descripción no puede ser nula o vacía.");
    }
    final long id = sequence.incrementAndGet();
    final Task newTask = new Task(id, description);
    tasks.put(id, newTask);
    return newTask;
  }

  public List<Task> getAllTasks() {
    return new ArrayList<>(tasks.values());
  }

  public Optional<Task> findTaskById(final long id) {
    return Optional.ofNullable(tasks.get(id));
  }

  public boolean deleteTask(final long id) {
    return tasks.remove(id) != null;
  }
}