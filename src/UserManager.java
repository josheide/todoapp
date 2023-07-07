import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    static ArrayList<User> userArrayList = new ArrayList<User>();

    public static void loadUsersFromFile(String userList) {
         File userListFile = new File(userList);
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
    }

    public static void loadUsersFromFileJSON(String userList) {
        File userListFile = new File(userList);

        try {
            if (!userListFile.exists()) {
                userListFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(userListFile));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            if (jsonBuilder.length() == 0) {
                System.out.println("The user list file is empty.");
                return;
            }

            // Convert the JSON data to a list of users
            Gson gson = new GsonBuilder().create();
            User[] users = gson.fromJson(jsonBuilder.toString(), User[].class);

            // Populate the userArrayList
            userArrayList.clear();
            for (User user : users) {
                userArrayList.add(user);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading the user list file: " + e.getMessage());
            e.printStackTrace();
        }
    }

        public static boolean authenticateUser(String username, String password) {
        for (User element : UserManager.userArrayList) {
            if (element.userName.equals(username) && element.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static boolean addUser(String userInputName, String userInputPassword, String userInputPassword2) {
        if (userInputPassword.equals(userInputPassword2) && !userInputPassword.isEmpty()) {
            userArrayList.add(new User(userInputName, userInputPassword));
            saveUsersJSON();
            return true;
        }
        return false;
    }

    public static void saveUsers() {
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

    public static void saveUsersJSON() {
        String filePath = "userListJSON.json";
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(userArrayList);

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }


    public static boolean deleteUser(String username) {
        boolean userFound = false;

        for (User user : UserManager.userArrayList) {
            if (user.userName.equals(username)) {
                UserManager.userArrayList.remove(user);
                userFound = true;
                break;
            }
        }

        if (!userFound) {
            return false;
        }
        List<Task> tasksToRemove = new ArrayList<>();
        for (Task task : TaskManager.taskList) {
            if (task.assignedToUser.equals(username)) {
                tasksToRemove.add(task);
            }
        }
        TaskManager.taskList.removeAll(tasksToRemove);
        TaskManager.exitAndSave();
        saveUsersJSON();
        return true; // user and tasks were found and deleted
    }
}
