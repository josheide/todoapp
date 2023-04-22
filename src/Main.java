// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * For my array to take two value, a string and a boolean I created a class 'Task'
 * Task class has a constructor method, which takes two arguments: name and isComplete.
 */

class Task {
    String name;
    boolean isComplete;

    Task(String name, boolean isComplete) {
        this.name = name;
        this.isComplete = isComplete;
    }
}

public class Main {
    public static void main(String[] args) {

        String filePath = "C:/Users/tc110/Dropbox/Prog/todoapp/todolist.txt";
        File todolist = new File("C:/Users/tc110/Dropbox/Prog/todoapp/todolist.txt");

        ArrayList<Task> taskList = new ArrayList<Task>();

        try (BufferedReader reader = new BufferedReader(new FileReader(todolist))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(" - ");
                taskList.add(new Task(taskData[0], Boolean.parseBoolean(taskData[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userInput = " ";

        Scanner scanner = new Scanner(System.in);
        System.out.println("In this app you can manage your to-do list! Here's how it works:");
        System.out.println(" ");
        System.out.println("- You can add tasks to your to do list by typing: 'add' ");
        System.out.println("- You can remove items from your to do list by typing: 'delete' ");
        System.out.println("- You  can ask the program to show your to do list by typing: 'list' ");
        System.out.println("- You can complete a task by typing 'complete' ");
        System.out.println("");

        while (!userInput.equals("exit")) {

            System.out.println(" ");
            System.out.println("Please type what you would like to do now: ");
            System.out.println("If you would like to save your tasks and exit the app, type: 'exit'");

            userInput = scanner.nextLine();

            if (userInput.equals("add")) {

                System.out.println("Please type the task that you would like to add!");
                String userTask = scanner.nextLine();
                taskList.add (new Task(userTask, false));
                System.out.println (userTask + " has been added!");
            }

            else if (userInput.equals("delete")) {

                System.out.println("Please enter the name of the task you would like to remove:");
                String userTask = scanner.nextLine();

                boolean foundTask = false;
                for (Task task : taskList) {
                    if (task.name.equals(userTask)) {
                        taskList.remove(task);
                        foundTask = true;
                        System.out.println(userTask + " has been removed from the to-do list.");
                        break;
                    }
                }
                if (!foundTask) {
                    System.out.println(userTask + " is not currently on the to-do list.");
                }
            }

            else if (userInput.equals("complete")) {
                System.out.println("Enter the name of the task you want to mark as complete: ");
                String userTask = scanner.nextLine();

                boolean foundTask = false;
                for (Task task : taskList) {
                    if (task.name.equals(userTask) && task.isComplete == false) {
                        task.isComplete = true;
                        foundTask = true;
                        System.out.println("Congratulations, you completed: " +userTask);
                        break;
                    }
                    else if (task.name.equals(userTask) && task.isComplete) {
                        System.out.println("You already completed " +userTask);
                    }

                    }
                if (!foundTask) {
                    System.out.println(userTask + " is currently not on the to-do list.");
                }
            }

            else if (userInput.equals("list")) {
                for (Task element : taskList) {
                    if(element.isComplete){
                        System.out.println(element.name + " - is complete");
                    }
                    else {
                        System.out.println(element.name + " - not complete");
                    }
                }
            }

            else if (userInput.equals("exit")) {

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/tc110/Dropbox/Prog/todoapp/todolist.txt"));
                    for (Task task : taskList) {
                        writer.write(task.name + " - " + task.isComplete + "\n");
                    }
                    writer.close();
                    System.out.println("Tasks written to file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                userInput = "exit";
            }

            else {
                System.out.println("Sorry, I did not catch that!");
            }
        }
    }
}