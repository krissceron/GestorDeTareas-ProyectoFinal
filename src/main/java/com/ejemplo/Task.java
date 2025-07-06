package com.ejemplo;

import java.util.Objects;

/**
 * Representa una tarea con un id, descripción y estado de completado.
 */
public final class Task {
  private long id;
  private String description;
  private boolean completed;
  
  /**
   * Constructor para crear una nueva tarea.
   * 
   * @param id el identificador único de la tarea
   * @param description la descripción de la tarea
   * @param completed el estado de completado de la tarea (true si está completada, false si no)
   */
  public Task(long id, String description, boolean completed) {
    this.id = id;
    this.description = description;
    this.completed = completed;
  }

  public Task(long id, String description) {
    this(id, description, false);
  }

  public long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public void markAsCompleted() {
    this.completed = true;
  }

  public void markAsIncomplete() {
    this.completed = false;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Task task = (Task) obj;
    return id == task.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Task{"
      + "id=" + id
      + ", description='" + description + '\''
      + ", completed=" + completed
      + '}';
  }
}