
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private int id = 0;

    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    public void removeAllTask() {
        taskList.clear();
    }

    public void removeAllEpic() {
        subtaskList.clear();
        epicList.clear();
    }

    public void removeAllSubtask() {
        for (Epic empty : epicList.values()) {
            empty.clearSubtasksSet();
            empty.setStatus(Status.NEW);
        }
        subtaskList.clear();
    }

    public Task getTaskById(int id) {
        return taskList.get(id);
    }

    public Epic getEpicById(int id) {
        return epicList.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtaskList.get(id);
    }

    public void createTask(Task newTask) {
        taskList.put(id, newTask);
        newTask.setId(id);
        id++;
    }

    public void createEpic(Epic newEpic) {
        epicList.put(id, newEpic);
        newEpic.setId(id);
        id++;
    }

    public void createSubtask(Subtask newSubtask, int epicId) {
        if (!epicList.containsKey(epicId)) {
            return;
        }
        subtaskList.put(id, newSubtask);
        newSubtask.setId(id);
        newSubtask.setEpicId(epicId);
        epicList.get(epicId).addSubtaskToEpic(newSubtask);
        epicList.get(epicId).setStatus(getEpicStatusForOrder(epicList.get(epicId)));
        id++;
    }

    public void updateTask(Task newTask, int id) {
        if (!taskList.containsKey(id)) {
            return;
        }
        newTask.setId(id);
        taskList.put(id, newTask);
    }

    public void updateEpic(Epic newEpic, int id) {
        if (!epicList.containsKey(id)) {
            return;
        }
        Epic oldEpic = epicList.get(id);
        newEpic.setId(id);
        newEpic.setSubtasksSet(oldEpic.getSubtasksSet());
        epicList.put(id, newEpic);
    }

    public void updateSubtask(Subtask newSubtask, int id) {
        if (!subtaskList.containsKey(id)) {
            return;
        }
        Subtask someSubtask = subtaskList.get(id);
        newSubtask.setEpicId(someSubtask.getEpicId());
        subtaskList.put(id, newSubtask);
        newSubtask.setId(id);
        epicList.get(newSubtask.getEpicId()).setStatus(getEpicStatusForOrder(epicList.get(newSubtask.getEpicId())));
    }

    public void removeTask(int id) {
        if (!taskList.containsKey(id)) {
            return;
        }
        taskList.remove(id);
    }

    public void removeEpic(int epicId) {
        if (!epicList.containsKey(epicId)) {
            return;
        }
        Epic epic = epicList.get(epicId);
        for (int i : epic.getSubtasksSet()) {
            subtaskList.remove(i);
        }
        epicList.remove(epicId);
    }

    public void removeSubtask(int id) {
        if (!subtaskList.containsKey(id)) {
            return;
        }
        Subtask subtask = subtaskList.get(id);
        Epic epic = epicList.get(subtask.getEpicId());
        epic.removeSubtaskIdFromSubtasksSet(subtask);
        epic.setStatus(getEpicStatusForOrder(epic));
        subtaskList.remove(id);
    }

    /* по тексту ТЗ вполне понятно, как должен быть реализован этот метод: в лоб. Я нашла иное решение, которое
    показалось мне и моему наставнику интереснее(и даже работает, невероятно!). Я настойчиво обращала на это Ваше
    внимание, но всё же оказалась вынуждена написать иную реализацию. Видимо, так проще: она ближе к авторскому решению.
    Свою реализацию я оставлю здесь и всё равно буду её использовать в пятом спринте.
      ***  статус эпика ставится в зависимости от цепочки статусов его подзадач.
     Если вся цепочка(реализация через цикл) состоит из одинаковых звеньев,
    то звено равно статусу эпика(новый или сделанный), другие варианты: если первое звено IN-PROGRESS,
     то быстро выходим  - эпик в таком же статусе, если есть надежда на NEW или DONE,
     то сравниваем звенья, при первом же неравенстве звеньев статус IN-PROGRESS.
        private Status getEpicStatus(Epic epic) {
            if (epic.getSubtasksSet().isEmpty()) {
                return Status.NEW;
            }
            Status state;
            state = subtaskList.get(epic.getSubtasksSet().getFirst()).getStatus();
            if (state == Status.IN_PROGRESS) {
                return Status.IN_PROGRESS;
            }
            for (int i : epic.getSubtasksSet()) {
                if (state != subtaskList.get(i).getStatus()) {
                    return Status.IN_PROGRESS;
                }
            }
            return state;
        }
    */
    private Status getEpicStatusForOrder(Epic epic) {
        if (epic.getSubtasksSet().isEmpty()) {
            return Status.NEW;
        }
        boolean isNew = true;
        boolean isDone = true;
        for (int i = 0; i < epic.getSubtasksSet().size(); i++) {
            Status state = subtaskList.get(epic.getSubtasksSet().get(i)).getStatus();
            if (state == Status.IN_PROGRESS) {
                return Status.IN_PROGRESS;
            } else if (state == Status.NEW) {
                isDone = false;
            } else {
                isNew = false;
            }
        }
        if (isNew) {
            return Status.NEW;
        } else if (isDone) {
            return Status.DONE;
        }
        return Status.IN_PROGRESS;
    }

    public ArrayList<Subtask> getSubtaskListByEpic(Epic epic) {
        ArrayList<Subtask> subtaskListByEpic = new ArrayList<>();
        for (int i = 0; i < epic.getSubtasksSet().size(); i++) {
            subtaskListByEpic.add(getSubtaskById(i));
        }
        return subtaskListByEpic;
    }

    public String PrintAll() {
        String output = "-----Tasks-----\n";
        for (Task current : taskList.values()) {
            output += current;
        }
        output += "-----Epics----\n";
        for (Epic current : epicList.values()) {
            output += current;
            for (Integer i : current.getSubtasksSet()) {
                output += subtaskList.get(i);
            }
            output += "------------\n";
        }
        return output;
    }

}
