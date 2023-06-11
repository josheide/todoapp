import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CLI {

    static Scanner scanner = new Scanner(System.in);

    public static void printWelcomeMessage() {
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

    public static void printOpeningMessage() {
        System.out.println("Welcome! What would you like to do?");
        System.out.println("1. Add a new user");
        System.out.println("2. Delete an existing user");
        System.out.println("3. Log in");
        System.out.println("4. Exit");
        System.out.println(" ");
    }

    public static void addTaskCLI(String userInputName) {
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

}
