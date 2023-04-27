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

public class Main {
    public static void main(String[] args) {

        String fileName = "todoListFile.txt";
        File todolist = new File(fileName);

        ArrayList<Task> taskList = new ArrayList<Task>();


        try {
            if (!todolist.exists()) {
                todolist.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(todolist));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(" - ");
                taskList.add(new Task(taskData[0], Boolean.parseBoolean(taskData[1]),taskData[2]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("In this app you can manage your to-do list! Here's how it works:");
        System.out.println(" ");
        System.out.println("- You can add tasks to your to do list by typing: 'add' ");
        System.out.println("- You can remove items from your to do list by typing: 'delete' ");
        System.out.println("- You  can ask  the program to show your to do list by typing: 'list' ");
        System.out.println("- You can complete a task by typing 'complete' ");
        System.out.println("");

        while (true) {
            System.out.print("Enter a task (or 'exit' to save and exit): ");
            String userInput = scanner.nextLine();

            if (userInput.equals("add")) {

                System.out.println("Please type the task that you would like to add!");
                String userTask = scanner.nextLine();

                System.out.println("Please type the name of the user the task is assigned to!");
                String userAssigned = scanner.nextLine();

                taskList.add(new Task(userTask, false, userAssigned));
                System.out.println(userTask + " has been added!");
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
                boolean listAllTasks = true;

                if (taskList.size() == 0) {
                    System.out.println("Your to do list is empty. Please add an item to your list, using the 'add' command!");
                } else {
                    System.out.println("Please enter your name: ");
                    String userName = scanner.nextLine();
                    if (!userName.isEmpty()) {
                        listAllTasks = false;
                    }
                    for (Task element : taskList) {
                        if (listAllTasks || element.assignedToUser.equals(userName)) {
                            if (element.isComplete) {
                                System.out.println(element.name + " - completed by " + element.assignedToUser);
                                System.out.println(" ");
                            } else {
                                System.out.println(element.name + " - incomplete, assigned to " + element.assignedToUser);
                            }
                        }
                    }
                }
            }


            else if (userInput.equals("exit")) {

                String filePath = "todoListFile.txt";

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                    for (Task task : taskList) {
                        writer.write(task.name + " - " + task.isComplete + " - " + task.assignedToUser + "\n");
                    }
                    writer.close(); // VERY IMPORTANT, otherwise the save is not complete

                } catch (Exception e) {
                    System.out.println("An error occurred while writing to the file.");
                    e.printStackTrace();
                }
                break;
            }

            else {
                System.out.println("Sorry, I did not catch that!");
            }
        }
    }
}