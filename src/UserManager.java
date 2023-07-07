import java.util.ArrayList;
import java.util.List;

public class UserManager {
    static boolean useJSON = true;
    static ArrayList<User> userArrayList = new ArrayList<User>();

    public static void loadUsers() {
        if (useJSON) {
            String usersFileName = "userListJSON.json";
            userArrayList = JSONDataSource.loadUsersFromFile(usersFileName);
        } else {
            String usersFileName = "userList.txt";
            userArrayList = TextFileDataSource.loadUsersFromFile(usersFileName);
        }
    }

    public static void saveUsers() {
        if (useJSON) {
            JSONDataSource.saveUsersToFile(userArrayList);
        } else {
            TextFileDataSource.saveUsersToFile(userArrayList);
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
            saveUsers();
            return true;
        }
        return false;
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
