import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class JSONTaskDataSource {

    public static ArrayList<Task> loadTasksFromFile(String fileName) {
        ArrayList<Task> taskList = new ArrayList<Task>();
        File taskListFile = new File(fileName);

        try {
            if (!taskListFile.exists()) {
                taskListFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(taskListFile));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            if (jsonBuilder.length() == 0) {
                System.out.println("The task list file is empty.");
                return taskList;
            }

            Gson gson = new GsonBuilder().create();
            Task[] tasks = gson.fromJson(jsonBuilder.toString(), Task[].class);

            taskList.clear();
            for (Task task : tasks) {
                taskList.add(task);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading the task list file: " + e.getMessage());
            e.printStackTrace();
        }
        return taskList;
    }

    public static void saveTasksToFile(ArrayList<Task> taskList) {
        String filePath = "taskListJSON.json";
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(taskList);

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
