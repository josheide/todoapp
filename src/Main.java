// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main {

    static ArrayList<User> userArrayList = new ArrayList<User>();
    static Scanner scanner = new Scanner(System.in);



    public static boolean authenticateUser(String username, String password) {
        for (User element : userArrayList) {
            if (element.userName.equals(username) && element.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static void deleteTask(String userInputName) {
        System.out.println("You are currently logged in as " + userInputName + " and you have the following items on your to do list: ");

        if (!userInputName.isEmpty()) {
            boolean foundUser = false;

            for (Task element : TodoApp.taskList) {
                if (element.assignedToUser.equals(userInputName)) {
                    foundUser = true;

                    if (element.isComplete) {
                        System.out.println("- " + element.getId() + " - " + element.name + " - completed by " + element.assignedToUser);
                    } else {
                        System.out.println("- " + element.getId() + " - " + element.name + " - incomplete, assigned to " + element.assignedToUser);
                    }
                }
            }
            if (!foundUser) {
                System.out.println("There are no tasks saved for the user: " + userInputName);
            } else {
                System.out.println("Please enter the ID of the task you would like to remove:");

                String userInputTaskNumber = scanner.next();
                scanner.nextLine(); // Without this line the program gets confused and immediately spits out "Sorry, I did not catch that"

                try {
                    int taskNumber = Integer.parseInt(userInputTaskNumber);

                    boolean foundTask = false;
                    for (Task task : TodoApp.taskList) {
                        if (task.getId() == taskNumber && task.assignedToUser.equals(userInputName)) {
                            TodoApp.taskList.remove(task);
                            foundTask = true;
                            System.out.println(task.name + " has been removed from the to-do list.");
                            break;
                        }
                    }
                    if (!foundTask) {
                        System.out.println("This task is not currently on the to-do list.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid task number.");
                }
            }
        }
    }

//    public static void completeTask(String userInputName) {
//        System.out.println("You are currently logged in as " + userInputName + " and you have the following items on your to do list: ");
//
//        for (Task element : TodoApp.taskList) {
//            if (element.assignedToUser.equals(userInputName)) {
//
//                if (element.isComplete) {
//                    System.out.println("- " + element.getId() + " - " + element.name + " - completed by " + element.assignedToUser);
//                } else {
//                    System.out.println("- " + element.getId() + " - " + element.name + " - incomplete, assigned to " + element.assignedToUser);
//                }
//            }
//        }
//
//        System.out.println("Please enter the ID of the task you would like to complete:");
//
//        String userInputTaskNumber = scanner.next();
//        scanner.nextLine(); // Without this line the program gets confused and acts as if I gave it an empty string as a command.
//
//        try {
//            int taskNumber = Integer.parseInt(userInputTaskNumber);
//
//            boolean foundTask = false;
//            for (Task task : TodoApp.taskList) {
//                if (task.getId() == taskNumber && !task.isComplete) {
//                    task.isComplete = true;
//                    foundTask = true;
//                    System.out.println("Congratulations, you completed: " + task.name);
//                    break;
//                } else if (task.getId() == taskNumber && task.isComplete) {
//                    System.out.println("You already completed " + task.name);
//                    foundTask = true;
//                }
//            }
//            if (!foundTask) {
//                System.out.println("There is no task with that ID");
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid input. Please enter a valid task number.");
//        }
//    }

    public static void listTasks(String userInputName) {
        System.out.println("You are currently logged in as " + userInputName);

        if (TodoApp.taskList.size() == 0) {
            System.out.println("Your to-do list is empty. Please add an item to your list using the 'add' command!");
        } else {
            if (!userInputName.isEmpty()) {
                for (Task element : TodoApp.taskList) {
                    if (element.assignedToUser.equals(userInputName)) {
                        System.out.println("- " + element.getId() + " - " + element.name + " - " + (element.isComplete ? "completed" : "incomplete") + " - assigned to " + element.assignedToUser);
                    }
                }
            }
        }
    }

    public static void loginAction() {
        boolean exit = false;
        while (!exit) {
            CLI.printOpeningMessage();
            System.out.println("Please enter your choice (the number!): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    login();
                    exit = true;
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again!");
                    break;
            }
        }
    }

    public static void addUser() {

        System.out.println("Please enter the username for the new user: ");
        String userInputName = scanner.nextLine();

        boolean foundUser = false;

        if (!userInputName.isEmpty()) {
            for (User element : userArrayList) {
                if (element.userName.equals(userInputName)) {
                    foundUser = true;
                    System.out.println("Sorry, but " + userInputName + " already has an account registered.");
                    break;
                }
            }

            if (!foundUser) {
                System.out.println("To register an account, please enter a password!");
                String userInputPassword = scanner.nextLine();

                System.out.println("Please confirm your password!");
                String userInputPassword2 = scanner.nextLine();

                if (userInputPassword.equals(userInputPassword2) && !userInputPassword.isEmpty()) {
                    userArrayList.add(new User(userInputName, userInputPassword));
                    saveUsers();
                } else {
                    System.out.println("Passwords do not match. Please try again!");
                }
            }
        }
    }

    public static void login() {

        System.out.println("Please enter your username: ");
        String userInputName = scanner.nextLine();
        boolean foundUser = false;

        if (!userInputName.isEmpty()) {
            for (User element : userArrayList) {
                if (element.userName.equals(userInputName)) {
                    foundUser = true;
                    break;

                }
            }

            if (foundUser){

                System.out.println("Please enter the password for " + userInputName);
                String userInputPassword = scanner.nextLine();

                boolean authenticated = authenticateUser(userInputName, userInputPassword);

            if (authenticated) {

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
                                deleteTask(userInputName);
                            } else if (userInput.equals("complete")) {
                                CLI.completeTaskCLI(userInputName);
                            } else if (userInput.equals("list")) {
                                listTasks(userInputName);
                            } else if (userInput.equals("exit")) {
                                TodoApp.exitAndSave();
                                break;
                            } else {
                                System.out.println("Sorry, I did not catch that!");
                            }
                        }
                    } else {
                        System.out.println("Invalid password. Please try again.");
                    }
                } else{
                    System.out.println("Sorry, I could not find " + userInputName );
                }
            }
        }

    public static void deleteUser() {
        System.out.println("Please enter the username of the user to delete: ");
        String username = scanner.nextLine()  ;

        boolean foundUser = false;
        for (User user : userArrayList) {
            if (user.userName.equals(username)) {
                userArrayList.remove(user);
                foundUser = true;
                break;
            }
        }

        List<Task> tasksToRemove = new ArrayList<>();

        for (Task task : TodoApp.taskList) {
            if (task.assignedToUser.equals(username)) {
                tasksToRemove.add(task);
            }
        }
        TodoApp.taskList.removeAll(tasksToRemove);
        TodoApp.exitAndSave();

        if (foundUser) {
            saveUsers();

            System.out.println("User " + username + " has been deleted successfully!");
        } else {
            System.out.println("User " + username + " does not exist. Deletion failed.");
        }
    }

    public static void saveUsers(){
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
                int taskID = taskHasNoID ? TodoApp.nextID++ : Integer.parseInt(taskData[0]);
                String taskName = taskData[taskHasNoID ? 0 : 1];
                boolean taskIsComplete = Boolean.parseBoolean(taskData[taskHasNoID ? 1 : 2]);
                String taskUser = taskData[taskHasNoID ? 2 : 3];
                Task newTask = new Task(taskID, taskName, taskIsComplete, taskUser);
                TodoApp.taskList.add(newTask);
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
                    userArrayList.add(newUser);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the user list file: " + e.getMessage());
            e.printStackTrace();
        }

        int maxID = 0;
        for (Task task : TodoApp.taskList) {
            if (task.getId() > maxID) {
                maxID = task.getId();
            }
        }
        TodoApp.nextID = maxID + 1;

        while (true) {
            loginAction();
        }
    }
}