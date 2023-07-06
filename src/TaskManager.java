import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TaskManager {

    static ArrayList<Task> taskList = new ArrayList<Task>();
    static int nextID = 1;


    public static void loadTasksFromFile(String fileName) {

        File todolist = new File(fileName);

        try {
            if (!todolist.exists()) {
                todolist.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(todolist));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(" - ");
                boolean taskHasNoID = taskData.length == 3;
                int taskID = taskHasNoID ? TaskManager.nextID++ : Integer.parseInt(taskData[0]);
                String taskName = taskData[taskHasNoID ? 0 : 1];
                boolean taskIsComplete = Boolean.parseBoolean(taskData[taskHasNoID ? 1 : 2]);
                String taskUser = taskData[taskHasNoID ? 2 : 3];
                Task newTask = new Task(taskID, taskName, taskIsComplete, taskUser);
                TaskManager.taskList.add(newTask);
            }

            int maxID = 0;
            for (Task task : TaskManager.taskList) {
                if (task.getId() > maxID) {
                    maxID = task.getId();
                }
            }
            TaskManager.nextID = maxID + 1;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Task addTask(String userInputName, String userTask) {
        if (userTask.isEmpty()) {
//            return "You cannot enter an empty field as a task, please try again!";
            return null;
        }

        if (userInputName.isEmpty()) {
//            return "You cannot enter an empty user name, please try again!";
            return null;
        }

        Task newTask = new Task(nextID, userTask, false, userInputName);
        taskList.add(newTask);
        nextID++;
        return newTask;
    }

    public static boolean completeTask (int taskNumber){

        for (Task task : taskList) {
            if (task.getId() != taskNumber) {
                // Not the task we are looking for.
                continue;
            }
                // We found the task we are looking for.
            if (task.isComplete) {
                return false; // task exists but you already completed it
            }

            task.isComplete = true;
            return true;
        }
        // not found
        return false;
    }

    public static boolean deleteTask (int taskNumber, String userInputName) {
        for (Task task : taskList) {
            if (task.getId() == taskNumber && task.assignedToUser.equals(userInputName)) {

                taskList.remove(task);
                return true;
            }
        }
        return false;
    }

    public static void exitAndSave() {
        String filePath = "todoListFile.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Task task : taskList) {
                writer.write(task.getId() + " - " + task.name + " - " + task.isComplete + " - " + task.assignedToUser + "\n");
            }
            writer.close(); // VERY IMPORTANT, otherwise the save is not complete

        } catch (Exception e) {
            //TODO: Exception should not be handled here.
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
