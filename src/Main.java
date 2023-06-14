import java.util.*;
import java.io.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void login() {
        System.out.println("Please enter your username: ");
        String userInputName = scanner.nextLine();
        boolean foundUser = false;

        if (userInputName.isEmpty()) {
            return;
        }

        for (User element : UserManager.userArrayList) {
            if (element.userName.equals(userInputName)) {
                foundUser = true;
                break;
            }
        }

        if (!foundUser) {
            System.out.println("Sorry, I could not find " + userInputName );
            return;
        }

        System.out.println("Please enter the password for " + userInputName);
        String userInputPassword = scanner.nextLine();
        boolean authenticated = UserManager.authenticateUserCLI (userInputName, userInputPassword);

        if (!authenticated) {
            System.out.println("Invalid password. Please try again.");
            return;
        }

        System.out.println("Login successful. Welcome, " +userInputName);
        while(true) {
            CLI.printWelcomeMessage();
            System.out.print("Enter a command (or 'exit' to save and exit): ");
            String userInput = scanner.nextLine();
            if (userInput.equals("GUI")) {
                GUI.displayTasksGUI(userInputName);
            } else if (userInput.equals("add")) {
                CLI.addTaskCLI(userInputName);
            } else if (userInput.equals("delete")) {
                CLI.deleteTaskCLI(userInputName);
            } else if (userInput.equals("complete")) {
                CLI.completeTaskCLI(userInputName);
            } else if (userInput.equals("list")) {
                CLI.listTaskCLI(userInputName);
            } else if (userInput.equals("exit")) {
                TaskManager.exitAndSave();
                break;
            } else {
                System.out.println("Sorry, I did not catch that!");
            }
        }
    }

    public static void taskSelector(String userInputName){
        System.out.println("Login successful. Welcome, " +userInputName);
        while(true) {
            CLI.printWelcomeMessage();
            System.out.print("Enter a command (or 'exit' to save and exit): ");
            String userInput = scanner.nextLine();
            if (userInput.equals("GUI")) {
                GUI.displayTasksGUI(userInputName);
            } else if (userInput.equals("add")) {
                CLI.addTaskCLI(userInputName);
            } else if (userInput.equals("delete")) {
                CLI.deleteTaskCLI(userInputName);
            } else if (userInput.equals("complete")) {
                CLI.completeTaskCLI(userInputName);
            } else if (userInput.equals("list")) {
                CLI.listTaskCLI(userInputName);
            } else if (userInput.equals("exit")) {
                TaskManager.exitAndSave();
                break;
            } else {
                System.out.println("Sorry, I did not catch that!");
            }
        }

    }

    public static void main(String[] args) {

        GUI gui = new GUI(); // This will be used later as we pass functionality

        String fileName = "todoListFile.txt";
        File todolist = new File(fileName);

        String userList = "userList.txt";
        File userListFile = new File(userList);

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
                // in the last version taskData had 3 properties. If the reader finds tasks without IDs, it assigns them an ID and shifts all the other properties to their appropriate locations.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    UserManager.userArrayList.add(newUser);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the user list file: " + e.getMessage());
            e.printStackTrace();
        }

        int maxID = 0;
        for (Task task : TaskManager.taskList) {
            if (task.getId() > maxID) {
                maxID = task.getId();
            }
        }
        TaskManager.nextID = maxID + 1;

        while (true) {
            CLI.loginActionCLI();
        }
    }
}