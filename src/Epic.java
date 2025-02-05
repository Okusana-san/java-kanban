import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Integer> subtasksSet;

    public Epic(String taskName, String description) {
        super(taskName, description, Status.NONE);
        setEpicId(getId());
        subtasksSet = new ArrayList<>();
        setEpicId(-2);
    }

    public void addSubtaskToEpic(Task subtask){
        subtasksSet.add(subtask.getId());
    }

    public ArrayList<Integer> getSubtasksSet() {
        return subtasksSet;
    }

    public void removeSubtaskIdFromSubtasksSet(Task subtask){
        subtasksSet.remove((Integer)subtask.getId());
    }
    public void setSubtasksSet(ArrayList<Integer> subtasks) {
        subtasksSet.addAll(subtasks);
    }
    public void clearSubtasksSet(){
        subtasksSet.clear();
    }
}
