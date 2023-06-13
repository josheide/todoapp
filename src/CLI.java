import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CLI {

    static Scanner scanner = new Scanner(System.in);

    public static void printWelcomeMessage () {
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

        Task task = TodoApp.addTask(userInputName, userTask);
        if (task == null) {
            System.out.println("Something went wrong.");
            return;
        }
        System.out.println(userTask + " has been added with the unique identifier: " + task.getId());
    }

    public static void listTaskCLI (String userInputName) {

        System.out.println("You are currently logged in as " + userInputName + " and you have the following items on your to do list: ");

        if (TodoApp.taskList.size() == 0) {
            System.out.println("Your to-do list is empty. Please add an item to your list using the 'add' command!");
        }

        listTasksForUser(userInputName);
    }

    public static void listTasksForUser(String userInputName) {
        boolean foundTasks = false;
        for (Task element : TodoApp.taskList) {
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

        boolean taskCompleteSuccess = TodoApp.completeTask(taskNumber);

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

        boolean taskDeleteSuccess = TodoApp.deleteTask(taskNumber, userInputName);

        if (taskDeleteSuccess == false) {
            System.out.println("Something went wrong.");
        }
    }
}