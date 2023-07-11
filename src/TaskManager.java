import java.util.ArrayList;

public class TaskManager {
    static boolean useJSON = true;
    static ArrayList<Task> taskList = new ArrayList<Task>();

    public static void loadTasks() {
        if (useJSON) {
            String tasksFileName = "taskListJSON.json";
            taskList = JSONTaskDataSource.loadTasksFromFile(tasksFileName);
        } else {
            String tasksFileName = "todoListFile.txt";
            taskList = TextFileDataSource.loadTasksFromFile(tasksFileName);
        }
    }

    public static void saveTasks() {
        if (useJSON) {
            JSONTaskDataSource.saveTasksToFile(taskList);
            System.out.println("Task list has been saved.");

        }
        else {
            TextFileDataSource.saveTasksToFile(taskList);
            System.out.println("Task list has been saved.");
        }
    }

    public static Task addTask(String assignedToUser, String taskName) {
        int taskId = generateTaskId();
        Task task = new Task(taskId, taskName, false, assignedToUser);
        taskList.add(task);
        return task;
    }

    public static boolean deleteTask(int taskId, String assignedToUser) {
        for (Task task : taskList) {
            if (task.getId() == taskId && task.getAssignedToUser().equals(assignedToUser)) {
                taskList.remove(task);
                return true;
            }
        }
        return false;
    }

    public static boolean completeTask(int taskId, String assignedToUser) {
        for (Task task : taskList) {
            if (task.getId() == taskId && task.getAssignedToUser().equals(assignedToUser)) {
                task.isComplete = true ;
                return true;
            }
        }
        return false;
    }

    private static int generateTaskId() {
        int maxId = 0;
        for (Task task : taskList) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }
        return maxId + 1;
    }
}
