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

    public static boolean authenticateUserCLI (String username, String password) {
        for (User element : UserManager.userArrayList) {
            if (element.userName.equals(username) && element.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static boolean addUser(String userInputName, String userInputPassword, String userInputPassword2) {

//        loadUsersFromFile();

        if (userInputPassword.equals(userInputPassword2) && !userInputPassword.isEmpty()) {
            userArrayList.add(new User(userInputName, userInputPassword));

            saveUsers();
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
        saveUsers();
        return true; // user and tasks were found and deleted
    }
}
