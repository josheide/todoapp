import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class TodoApp {

    static ArrayList<Task> taskList = new ArrayList<Task>();
    static int nextID = 1;

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
