
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    static private HashMap<Integer, Task> taskList = new HashMap<>();
    static private HashMap<Integer, Epic> epicList = new HashMap<>();
    static private HashMap<Integer, Task> subtaskList = new HashMap<>();
    static int id = 0;

    public static HashMap<Integer, Task> getTaskList() {
        return taskList;
    }

    public static HashMap<Integer, Epic> getEpicList() {
        return epicList;
    }

    public static HashMap<Integer, Task> getSubtaskList() {
        return subtaskList;
    }
    public static void removeAllTask() {
        taskList.clear();
    }
    public static void removeAllEpic() {
        subtaskList.clear();
        epicList.clear();
    }
    public static void removeAllSubtask() {
       for(Epic empty : epicList.values()) {
           empty.clearSubtasksSet();
       }
        subtaskList.clear();
    }
    public static Task getTaskById(int id){

      return taskList.get(id);
    }
    public static Epic getEpicById(int id){
        return epicList.get(id);
    }
    public static Task getSubtaskById(int id){
        return subtaskList.get(id);
    }
    public static void createTask(Task newTask){
        taskList.put(id, newTask);
        newTask.setId(id);
        id++;
    }
    public static void createEpic(Epic newEpic) {
        epicList.put(id, newEpic);
        newEpic.setId(id);
        id++;
    }

    public static void createSubtask(Task newSubtask, int epicId) {
        if(!epicList.containsKey(epicId)){return;}
        subtaskList.put(id, newSubtask);
        newSubtask.setId(id);
        newSubtask.setEpicId(epicId);
        epicList.get(epicId).addSubtaskToEpic(newSubtask);
        epicList.get(epicId).setStatus(getEpicStatus(epicList.get(epicId)));
        id++;
    }

    public static void updateTask(Task newTask, int id){
        if(!taskList.containsKey(id)){return;}
        newTask.setId(id);
        taskList.put(id, newTask);
            }

    public static void updateEpic(Epic newEpic, int id){
        if(!epicList.containsKey(id)){return;}
        Epic oldEpic = epicList.get(id);
        newEpic.setId(id);
        newEpic.setSubtasksSet(oldEpic.getSubtasksSet());
        epicList.put(id, newEpic);

    }
    public static void updateSubtask(Task newSubtask, int id){
        if(!subtaskList.containsKey(id)){return;}
       Task someSubtask = subtaskList.get(id);
       newSubtask.setEpicId(someSubtask.getEpicId());
        subtaskList.put(id, newSubtask);
        newSubtask.setId(id);
        epicList.get(newSubtask.getEpicId()).setStatus(getEpicStatus(epicList.get(newSubtask.getEpicId())));
    }

    public static void removeTask(int id){
        if(!taskList.containsKey(id)){return;}
        taskList.remove(id);
    }

    public static void removeEpic(int epicId) {
        if(!epicList.containsKey(epicId)){return;}
        Epic epic = epicList.get(epicId);
                for(int i : epic.getSubtasksSet()) {
                    subtaskList.remove(i);
                    }
        epicList.remove(epicId);
    }

    public static void removeSubtask(int id){
        if(!subtaskList.containsKey(id)){return;}
        Task subtask = subtaskList.get(id);
        Epic epic = epicList.get(subtask.getEpicId());
        epic.removeSubtaskIdFromSubtasksSet(subtask);
        subtaskList.remove(id);
    }

    public static Status getEpicStatus(Epic epic){
        Status state;
        state = subtaskList.get(epic.getSubtasksSet().getFirst()).getStatus();
        if(state == Status.IN_PROGRESS){
           return Status.IN_PROGRESS;
        } for(int i : epic.getSubtasksSet()){
            if(state != subtaskList.get(i).getStatus()){
               return Status.IN_PROGRESS;
            }
        }return state;
    }

    public static ArrayList<Task> getSubtaskListByEpic(Epic epic){
        ArrayList<Task> subtaskListByEpic = new ArrayList<>();
       for(int i = 0; i <epic.getSubtasksSet().size(); i++) {
           subtaskListByEpic.add(getSubtaskById(i));

       }return subtaskListByEpic;
    }

    public static String PrintAll(){
        String output = "-----Tasks-----\n";
        for(Task current : taskList.values()){
            output += current;
        }
        output += "-----Epics----\n";
        for(Epic current : epicList.values()){
            output += current;
            for(Integer i : current.getSubtasksSet()){
                output += subtaskList.get(i);
            }
            output += "------------\n";
        }
        return output;
    }

}
