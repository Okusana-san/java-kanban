import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasksSet;

    public Epic(String taskName, String description) {
        super(taskName, description, Status.NONE);// можно сделать по умолчанию NEW, но так мне кажется перспективнее, безопаснее(вопрос про эпики, оставшиеся без подзадач меня тревожит)
        setEpicId(getId());
        subtasksSet = new ArrayList<>();
    }

    public void addSubtaskToEpic(Task subtask) {
        subtasksSet.add(subtask.getId());
    }

    public ArrayList<Integer> getSubtasksSet() {
        return subtasksSet;
    }

    public void removeSubtaskIdFromSubtasksSet(Task subtask) {
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
        return "Epic"+CreateStringForToString();
    }
}
