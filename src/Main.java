import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        GUI gui = new GUI(); // This will be used later as we pass functionality

        String fileName = "todoListFile.txt";
        String fileNameJSON = "todoListFile.json";
        TaskManager.loadTasksFromFile(fileName);

        UserManager.loadUsers();

        CLI.start();
    }
}