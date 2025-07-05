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

  public Task createTask(final String description) {
    if (description == null || description.isBlank()) {
        throw new IllegalArgumentException("La descripción de la tarea no puede ser nula o estar en blanco.");
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