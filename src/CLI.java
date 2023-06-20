import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CLI {

    static Scanner scanner = new Scanner(System.in);

    public static void userSelectionCLI() {
        boolean exit = false;
        while (!exit) {
            CLI.printOpeningMessage();
            System.out.println("Please enter your choice (the number!): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    CLI.addUserCLI();
                    break;
                case 2:
                    CLI.deleteUserCLI();
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
            System.out.println("Sorry, I could not find " + userInputName);
            return;
        }

        System.out.println("Please enter the password for " + userInputName);
        String userInputPassword = scanner.nextLine();
        boolean authenticated = UserManager.authenticateUserCLI(userInputName, userInputPassword);

        if (!authenticated) {
            System.out.println("Invalid password. Please try again.");
            return;
        }

        taskSelector(userInputName);

    }

    public static void taskSelector(String userInputName){
        System.out.println("Login successful. Welcome, " +userInputName);
        while(true) {
            CLI.CLIWelcomeMessage();
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

    public static void CLIWelcomeMessage() {
        String filePathWelcome = "welcomeText.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathWelcome))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public static void printOpeningMessage () {
        System.out.println("Welcome! What would you like to do?");
        System.out.println("1. Add a new user");
        System.out.println("2. Delete an existing user");
        System.out.println("3. Log in");
        System.out.println("4. Exit");
        System.out.println(" ");
    }

    public static void addTaskCLI (String userInputName) {

        System.out.println("You are currently logged in as " + userInputName);
        System.out.println("Please type the task that you would like to add!");
        String userTask = scanner.nextLine();

        Task task = TaskManager.addTask(userInputName, userTask);
        if (task == null) {
            System.out.println("Something went wrong.");
            return;
        }
        System.out.println(userTask + " has been added with the unique identifier: " + task.getId());
    }

    public static void listTaskCLI (String userInputName) {

        System.out.println("You are currently logged in as " + userInputName + " and you have the following items on your to do list: ");

        if (TaskManager.taskList.size() == 0) {
            System.out.println("Your to-do list is empty. Please add an item to your list using the 'add' command!");
        }

        listTasksForUser(userInputName);
    }

    public static void listTasksForUser(String userInputName) {
        boolean foundTasks = false;
        for (Task element : TaskManager.taskList) {
            if (element.assignedToUser.equals(userInputName) && element.isComplete) {
                System.out.println("- " + element.getId() + " - " + element.name + " - completed by " + element.assignedToUser);
                foundTasks = true;

            } else if (element.assignedToUser.equals(userInputName) && !element.isComplete) {
                System.out.println("- " + element.getId() + " - " + element.name + " - incomplete, assigned to " + element.assignedToUser);
                foundTasks = true;
            }
        }
        if (!foundTasks) {
            System.out.println("There are no tasks saved for the user: " + userInputName);
        }
    }

    public static void completeTaskCLI (String userInputName) {

        System.out.println("You are currently logged in as " + userInputName + " and you have the following items on your to do list: ");

        listTasksForUser(userInputName);

        System.out.println("Please enter the ID of the task you would like to complete:");
        String userInputTaskNumber = scanner.next();
        scanner.nextLine(); // This is important, do not delete!

        int taskNumber = Integer.parseInt(userInputTaskNumber);

        boolean taskCompleteSuccess = TaskManager.completeTask(taskNumber);

        if (taskCompleteSuccess == false) {
            System.out.println("Something went wrong.");
            return;
        }

        System.out.println("Task " + taskNumber + " has been successfully completed.");

    }

    public static void deleteTaskCLI(String userInputName){
        System.out.println("You are currently logged in as " + userInputName + " and you have the following items on your to-do list:");

        listTasksForUser(userInputName);

        System.out.println("Please enter the ID of the task you would like to remove:");
        String userInputTaskNumber = scanner.next();
        scanner.nextLine();

        int taskNumber = Integer.parseInt(userInputTaskNumber);

        boolean taskDeleteSuccess = TaskManager.deleteTask(taskNumber, userInputName);

        if (taskDeleteSuccess == false) {
            System.out.println("Something went wrong.");
        }
    }

    /// User management starts here! ///

    public static void addUserCLI() {
        System.out.println("Please enter the username for the new user: ");
        String userInputName = scanner.nextLine();

        boolean foundUser = false;

        if (!userInputName.isEmpty()) {
            for (User element : UserManager.userArrayList) {
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

                boolean addUserSuccess = UserManager.addUser(userInputName, userInputPassword, userInputPassword2);

                if (addUserSuccess == false) {
                    System.out.println("Something went wrong.");
                }
            }
        }
    }
    public static void deleteUserCLI(){
        System.out.println("Please enter the username of the user to delete: ");
        String username = scanner.nextLine();

        boolean deleteUserSuccess = UserManager.deleteUser(username);

        if (deleteUserSuccess == true) {
            System.out.println("User " + username + " has been deleted successfully!");
        }

        if (deleteUserSuccess == false) {
            System.out.println("Something went wrong.");
        }

    }
}