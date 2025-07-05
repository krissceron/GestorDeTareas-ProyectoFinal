public class Main {
public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        tm.addTask("Complete project");
        tm.listTasks();
        tm.removeTask(1);
    }
}