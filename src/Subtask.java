public class Subtask extends Task {
    private int epicId;

    public Subtask(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask with epicId = " + getEpicId() + createStringForToString();
    }
}
