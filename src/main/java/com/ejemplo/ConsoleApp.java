package com.ejemplo;
 
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
 
/**
* Aplicación de consola para gestionar tareas.
*/
public final class ConsoleApp {
 
    private static int OPTION_CREATE = 1;
    private static int OPTION_LIST = 2;
    private static int OPTION_FIND = 3;
    private static int OPTION_COMPLETE = 4;
    private static int OPTION_INCOMPLETE = 5;
    private static int OPTION_DELETE = 6;
    private static int OPTION_SHOW_COMPLETED = 7;
    private static int OPTION_SHOW_PENDING = 8;
    private static int OPTION_EXIT = 0;
 
    private TaskService taskService = new TaskService();
    private Scanner scanner = new Scanner(System.in);
 
    /**
     * Ejecuta el ciclo principal de la aplicación.
     */
    public void run() {
        System.out.println("=== TASK MANAGER ===");
        System.out.println("¡Bienvenido al gestor de tareas!");
 
        boolean running = true;
        while (running) {
            showMenu();
            int option = getMenuOption();
 
            switch (option) {
                case OPTION_CREATE:
                    createTask();
                    break;
                case OPTION_LIST:
                    listAllTasks();
                    break;
                case OPTION_FIND:
                    findTask();
                    break;
                case OPTION_COMPLETE:
                    markTaskAsCompleted();
                    break;
                case OPTION_INCOMPLETE:
                    markTaskAsIncomplete();
                    break;
                case OPTION_DELETE:
                    deleteTask();
                    break;
                case OPTION_SHOW_COMPLETED:
                    showCompletedTasks();
                    break;
                case OPTION_SHOW_PENDING:
                    showPendingTasks();
                    break;
                case OPTION_EXIT:
                    running = false;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor intenta de nuevo.");
                    break;
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
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
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
 
        Optional<Task> taskOpt = taskService.findTaskById(id);
        if (taskOpt.isPresent()) {
            System.out.println("Tarea a eliminar:");
            printTask(taskOpt.get());
 
            System.out.print("¿Estás seguro? (s/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
 
            if ("s".equals(confirmation) || "si".equals(confirmation)) {
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
        List<Task> completedTasks = taskService.getAllTasks().stream()
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
        List<Task> pendingTasks = taskService.getAllTasks().stream()
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