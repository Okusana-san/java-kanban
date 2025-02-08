import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasksSet;

    public Epic(String taskName, String description) {
        super(taskName, description, Status.NEW);
        subtasksSet = new ArrayList<>();
    }

    public void addSubtaskToEpic(Subtask subtask) {
        subtasksSet.add(subtask.getId());
    }

    public ArrayList<Integer> getSubtasksSet() {
        return subtasksSet;
    }

    public void removeSubtaskIdFromSubtasksSet(Subtask subtask) {
        subtasksSet.remove((Integer)subtask.getId());
    }

    public void setSubtasksSet(ArrayList<Integer> subtasks) {
        subtasksSet.addAll(subtasks);
    }

    public void clearSubtasksSet() {
        subtasksSet.clear();
    }

    @Override
    public String toString() {
        return "Epic" + createStringForToString();
    }
}
