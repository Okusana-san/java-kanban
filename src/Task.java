
import java.util.Objects;

public class Task {
    private String taskName;
    private String description;
    private Status status;
    private int id;

    public Task(String taskName, String description, Status status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String taskDescription) {
        description = taskDescription;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        if (taskName != null) {
            hash = hash + taskName.hashCode();
        }
        hash = hash * 11;

        if (description != null) {
            hash = hash + description.hashCode();
        }
        return hash;
    }

    protected String createStringForToString() {
        String output = " id = " + getId() + "; Name = \"" + getTaskName() + "\"; Status - " + getStatus().toString()
                + "\n" + "Description: \"" + getDescription() + "\"\n" + "-<>--<>--<>--<>--<>--<>-\n";
        return output;
    }

    @Override
    public String toString() {
        return "Task" + createStringForToString();
    }
}

