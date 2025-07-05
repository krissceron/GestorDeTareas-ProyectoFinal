package com.ejemplo;

import java.util.Objects;

/**
 * Representa una tarea con un id, descripción y estado de completado.
 */
public final class Task {
  private final long id;
  private String description;
  private boolean completed;

  public Task(final long id, final String description, final boolean completed) {
    this.id = id;
    this.description = description;
    this.completed = completed;
  }

  public Task(final long id, final String description) {
    this(id, description, false);
  }

  public final long getId() {
    return id;
  }

  public final String getDescription() {
    return description;
  }

  public final boolean isCompleted() {
    return completed;
  }

  public final void setDescription(final String description) {
    this.description = description;
  }

  public final void setCompleted(final boolean completed) {
    this.completed = completed;
  }

  public final void markAsCompleted() {
    this.completed = true;
  }

  public final void markAsIncomplete() {
    this.completed = false;
  }

  @Override
  public final boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Task task = (Task) obj;
      return id == task.id;
  }

  @Override
  public final int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public final String toString() {
    return "Task{" +
      "id=" + id +
      ", description='" + description + '\'' +
      ", completed=" + completed +
      '}';
  }
}