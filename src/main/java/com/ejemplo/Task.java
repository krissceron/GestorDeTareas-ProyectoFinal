import java.util.Objects;

public class Task {
    private final long id;
    private String description;
    private boolean completed;
    
    // Constructor completo
    public Task(long id, String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }
    
    // Constructor sin estado de completado (por defecto false)
    public Task(long id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }
    
    // Getters
    public long getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    // Setters
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    //Métodos
    
    // Método de conveniencia para marcar como completada
    public void markAsCompleted() {
        this.completed = true;
    }
    
    // Método de conveniencia para marcar como no completada
    public void markAsIncomplete() {
        this.completed = false;
    }
    
    // equals() - dos tareas son iguales si tienen el mismo id
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id == task.id;
    }
    
    // hashCode() - basado en el id
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    // toString() - representación en string de la tarea
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}