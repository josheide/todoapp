import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    static boolean useJSON = true;
    static ArrayList<Task> taskList = new ArrayList<Task>();

    public static void loadTasks() {
        if (useJSON) {
            String tasksFileName = "taskListJSON.json";
            taskList = JSONTaskDataSource.loadTasksFromFile(tasksFileName);
        } else {
            String tasksFileName = "taskList.txt";
            taskList = TextFileDataSource.loadTasksFromFile(tasksFileName);
        }
    }

    public static void saveTasks() {
        if (useJSON) {
            JSONTaskDataSource.saveTasksToFile(taskList);
        }
        else {
            TextFileDataSource.saveTasksToFile(taskList);
        }
    }

    public static Task addTask(String assignedToUser, String taskName) {
        int taskId = generateTaskId();
        Task task = new Task(taskId, taskName, assignedToUser);
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
                task.setComplete(true);
                return true;
            }
        }
        return false;
    }

    public static void listTasksForUser(String assignedToUser) {
        boolean foundTasks = false;
        for (Task task : taskList) {
            if (task.getAssignedToUser().equals(assignedToUser)) {
                System.out.println("- " + task.getId() + " - " + task.getName() + " - " + (task.isComplete() ? "Completed" : "Incomplete"));
                foundTasks = true;
            }
        }
        if (!foundTasks) {
            System.out.println("There are no tasks for the user: " + assignedToUser);
        }
    }

    public static void exitAndSave() {
        saveTasks();
        System.out.println("Task list has been saved.");
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
