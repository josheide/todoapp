import java.io.*;

public class Main {

    public static void main(String[] args) {

        GUI gui = new GUI(); // This will be used later as we pass functionality

        String fileName = "todoListFile.txt";
        TaskManager.loadTasksFromFile(fileName);

        String userList = "userList.txt";
        UserManager.loadUsersFromFile(userList);

        while (true) {
            CLI.userSelectionCLI();
        }
    }
}