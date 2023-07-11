import java.io.*;
import java.util.ArrayList;

public class TextFileDataSource {

    public static ArrayList<User> loadUsersFromFile(String fileName) {
        ArrayList<User> userArrayList = new ArrayList<User>();
        File userListFile = new File(fileName);
        try {
            if (!userListFile.exists()) {
                userListFile.createNewFile();
            }
            try (BufferedReader userReader = new BufferedReader(new FileReader(userListFile))) {
                String userLine;
                while ((userLine = userReader.readLine()) != null) {
                    String[] userData = userLine.split(" - ");
                    String userName = userData[0];
                    String userPassword = userData[1];
                    User newUser = new User(userName, userPassword);
                    userArrayList.add(newUser);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the user list file: " + e.getMessage());
            e.printStackTrace();
        }
        return userArrayList;
    }

    public static void saveUsersToFile(ArrayList<User> userArrayList) {
        String filePath = "userList.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (User user : userArrayList) {
                writer.write(user.userName + " - " + user.password + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    /// TASKS MANAGEMENT ///

    public static ArrayList<Task> loadTasksFromFile(String fileName) {

        ArrayList<Task> taskArrayList = new ArrayList<>();
        File todolist = new File(fileName);

        try {
            if (!todolist.exists()) {
                todolist.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(todolist));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(" - ");
                int taskID = Integer.parseInt(taskData[0]);
                String taskName = taskData[1];
                boolean taskIsComplete = Boolean.parseBoolean(taskData[2]);
                String taskUser = taskData[3];
                Task newTask = new Task(taskID, taskName, taskIsComplete, taskUser);
                TaskManager.taskList.add(newTask);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskArrayList;
    }

    public static void saveTasksToFile(ArrayList<Task> taskList) {
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
