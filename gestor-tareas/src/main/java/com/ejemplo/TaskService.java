import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TaskService {
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public Task createTask(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("La descripción de la tarea no puede ser nula o estar en blanco.");
        }
        long id = sequence.incrementAndGet();
        Task newTask = new Task(id, description);
        tasks.put(id, newTask);
        return newTask;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> findTaskById(long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public boolean deleteTask(long id) {
        return tasks.remove(id) != null;
    }
}