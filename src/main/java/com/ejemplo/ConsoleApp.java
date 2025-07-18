package com.ejemplo;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Aplicación de consola para gestionar tareas.
 */
public final class ConsoleApp {
  private TaskService taskService = new TaskService();
  private Scanner scanner = new Scanner(System.in);

  /**
   * Se presenta menu de opciones del sistema.
   */  
  public void run() {
    System.out.println("=== TASK MANAGER ===");
    System.out.println("¡Bienvenido al gestor de tareas!");
    boolean running = true;
    while (running) {
      showMenu();
      int option = getMenuOption();
        
      switch (option) {
        case 1:
          createTask();
          break;
        case 2:
          listAllTasks();
          break;
        case 3:
          findTask();
          break;
        case 4:
          markTaskAsCompleted();
          break;
        case 5:
          markTaskAsIncomplete();
          break;
        case 6:
          deleteTask();
          break;
        case 7:
          showCompletedTasks();
          break;
        case 8:
          showPendingTasks();
          break;
        case 0:
          running = false;
          System.out.println("¡Hasta luego!");
          break;
        default:
          System.out.println("Opción inválida. Por favor intenta de nuevo.");
      }
      if (running) {
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
      }
    }
    scanner.close();
  }
    
  private void showMenu() {
    System.out.println("\n" + "=".repeat(30));
    System.out.println("MENU PRINCIPAL");
    System.out.println("=".repeat(30));
    System.out.println("1. Crear nueva tarea");
    System.out.println("2. Listar todas las tareas");
    System.out.println("3. Buscar tarea por ID");
    System.out.println("4. Marcar tarea como completada");
    System.out.println("5. Marcar tarea como pendiente");
    System.out.println("6. Eliminar tarea");
    System.out.println("7. Ver tareas completadas");
    System.out.println("8. Ver tareas pendientes");
    System.out.println("0. Salir");
    System.out.println("=".repeat(30));
    System.out.print("Selecciona una opción: ");
  }
    
  private int getMenuOption() {
    try {
      int option = Integer.parseInt(scanner.nextLine().trim());
      return option;
    } catch (NumberFormatException e) {
      return -1; // Opción inválida
    }
  }
    
  private void createTask() {
    System.out.println("\n--- CREAR NUEVA TAREA ---");
    System.out.print("Ingresa la descripción de la tarea: ");
    String description = scanner.nextLine().trim();
    
    try {
      Task newTask = taskService.createTask(description);
      System.out.println("  Tarea creada exitosamente:");
      System.out.println("  ID: " + newTask.getId());
      System.out.println("  Descripción: " + newTask.getDescription());
      System.out.println("  Estado: " + (newTask.isCompleted() ? "Completada" : "Pendiente"));
    } catch (IllegalArgumentException e) {
      System.out.println("  Error: " + e.getMessage());
    }
  }
    
  private void listAllTasks() {
    System.out.println("\n--- TODAS LAS TAREAS ---");
    List<Task> tasks = taskService.getAllTasks();
    
    if (tasks.isEmpty()) {
      System.out.println("No hay tareas registradas.");
      return;
    }
    System.out.println("Total de tareas: " + tasks.size());
    System.out.println("-".repeat(50));
    
    for (Task task : tasks) {
      printTask(task);
    }
  }
    
  private void findTask() {
    System.out.println("\n--- BUSCAR TAREA ---");
    long id = getTaskId();
    
    if (id == -1) {
      return;
    }
    Optional<Task> taskOpt = taskService.findTaskById(id);
    if (taskOpt.isPresent()) {
      System.out.println("  Tarea encontrada:");
      printTask(taskOpt.get());
    } else {
      System.out.println("  No se encontró una tarea con ID: " + id);
    }
  }
    
  private void markTaskAsCompleted() {
    System.out.println("\n--- MARCAR COMO COMPLETADA ---");
    long id = getTaskId();
    
    if (id == -1) {
      return;
    }
    Optional<Task> taskOpt = taskService.findTaskById(id);
    if (taskOpt.isPresent()) {
      Task task = taskOpt.get();
      if (task.isCompleted()) {
        System.out.println("  La tarea ya está completada.");
      } else {
        task.markAsCompleted();
        System.out.println("  Tarea marcada como completada:");
        printTask(task);
      }
    } else {
      System.out.println("  No se encontró una tarea con ID: " + id);
    }
  }
    
  private void markTaskAsIncomplete() {
    System.out.println("\n--- MARCAR COMO PENDIENTE ---");
    long id = getTaskId();
    
    if (id == -1) {
      return;
    }
    Optional<Task> taskOpt = taskService.findTaskById(id);
    if (taskOpt.isPresent()) {
      Task task = taskOpt.get();
      if (!task.isCompleted()) {
        System.out.println("  La tarea ya está pendiente.");
      } else {
        task.markAsIncomplete();
        System.out.println("  Tarea marcada como pendiente:");
        printTask(task);
      }
    } else {
      System.out.println("  No se encontró una tarea con ID: " + id);
    }
  }
    
  private void deleteTask() {
    System.out.println("\n--- ELIMINAR TAREA ---");
    long id = getTaskId();
    
    if (id == -1) {
      return;
    }
    // Mostrar la tarea antes de eliminarla
    Optional<Task> taskOpt = taskService.findTaskById(id);
    if (taskOpt.isPresent()) {
      System.out.println("Tarea a eliminar:");
      printTask(taskOpt.get());
        
      System.out.print("¿Estás seguro? (s/n): ");
      String confirmation = scanner.nextLine().trim().toLowerCase();
        
      if (confirmation.equals("s") || confirmation.equals("si")) {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
          System.out.println("  Tarea eliminada exitosamente.");
        } else {
          System.out.println("  Error al eliminar la tarea.");
        }
      } else {
        System.out.println("Operación cancelada.");
      }
    } else {
      System.out.println("  No se encontró una tarea con ID: " + id);
    }
  }
    
  private void showCompletedTasks() {
    System.out.println("\n--- TAREAS COMPLETADAS ---");
    List<Task> allTasks = taskService.getAllTasks();
    List<Task> completedTasks = allTasks.stream()
            .filter(Task::isCompleted)
            .toList();
    
    if (completedTasks.isEmpty()) {
      System.out.println("No hay tareas completadas.");
      return;
    }
    System.out.println("Total de tareas completadas: " + completedTasks.size());
    System.out.println("-".repeat(50));
    
    for (Task task : completedTasks) {
      printTask(task);
    }
  }
    
  private void showPendingTasks() {
    System.out.println("\n--- TAREAS PENDIENTES ---");
    List<Task> allTasks = taskService.getAllTasks();
    List<Task> pendingTasks = allTasks.stream()
            .filter(task -> !task.isCompleted())
            .toList();
    
    if (pendingTasks.isEmpty()) {
      System.out.println("No hay tareas pendientes. ¡Buen trabajo!");
      return;
    }
    System.out.println("Total de tareas pendientes: " + pendingTasks.size());
    System.out.println("-".repeat(50));
    
    for (Task task : pendingTasks) {
      printTask(task);
    }
  }
    
  private long getTaskId() {
    System.out.print("Ingresa el ID de la tarea: ");
    try {
      return Long.parseLong(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
      System.out.println("  ID inválido. Debe ser un número.");
      return -1;
    }
  }
    
  private void printTask(Task task) {
    String status = task.isCompleted() ? "  Completada" : "  Pendiente";
    System.out.println("ID: " + task.getId() + " | " + status);
    System.out.println("Descripción: " + task.getDescription());
    System.out.println("-".repeat(50));
  }
}