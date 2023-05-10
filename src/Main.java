// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*; // I replaced .File .FileReader .BufferedReader .BufferedWriter .FileWriter .IOException with *
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        String fileName = "todoListFile.txt";
        File todolist = new File(fileName);

        ArrayList<Task> taskList = new ArrayList<Task>();

        int nextID = taskList.size() + 1;

        try {
            if (!todolist.exists()) {
                todolist.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(todolist));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(" - ");
                taskList.add(new Task(Integer.parseInt(taskData[0]), taskData[1], Boolean.parseBoolean(taskData[2]), taskData[3]));
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

                if (!userAssigned.isEmpty()) {
                    taskList.add(new Task(nextID, userTask, false, userAssigned));
                    System.out.println(userTask + " has been added with the unique identifier: " + taskList.size()); // If you delete a task then the ID that it prints here is off. I tried nextID here and it always prints 1.

                } else if (userAssigned.isEmpty()) {
                    System.out.println("You must assign the task to a person! Please try to add this task again!");
                }
            }

            else if (userInput.equals("delete")) {
                System.out.println("Please enter the name of the user you want to delete the task of");
                String userName = scanner.nextLine();

                if(!userName.isEmpty()) {
                    for (Task element : taskList) {
                        if (element.assignedToUser.equals(userName)) {
                            if (element.isComplete) {
                                System.out.println("- " + element.id + " - " + element.name + " - completed by " + element.assignedToUser);
                            } else {
                                System.out.println("- " + element.id + " - " + element.name + " - incomplete, assigned to " + element.assignedToUser);
                            }
                        }
                    }

                    System.out.println("Please enter the ID of the task you would like to remove:");
                    int taskIdInput = scanner.nextInt();
                    scanner.nextLine(); // Without this line the program takes another task input and immediately spits out "Sorry, I did not catch that"


                    boolean foundTask = false;
                    for (Task task : taskList) {
                        if (task.getId() == taskIdInput && task.assignedToUser.equals(userName)) {
                            taskList.remove(task);
                            foundTask = true;
                            System.out.println(task.name + " has been removed from the to-do list.");
                            break;
                        }
                    }
                    if (!foundTask) {
                        System.out.println("This task is not currently on the to-do list.");
                    }
                }
            }

            else if (userInput.equals("complete")) {
                System.out.println("Enter the name of the task you want to mark as complete: ");
                String userTask = scanner.nextLine();

                System.out.println("Please enter the name of the user the task is assigned to!");
                String userName = scanner.nextLine();

                boolean foundTask = false;
                for (Task task : taskList) {
                    if (task.name.equals(userTask) && task.isComplete == false && task.assignedToUser.equals(userName)) {
                        task.isComplete = true;
                        foundTask = true;
                        System.out.println("Congratulations, you completed: " + userTask);
                        break;
                    }
                    else if (task.name.equals(userTask) && task.isComplete && task.assignedToUser.equals(userName)) {
                        System.out.println("You already completed " + userTask);
                    }
                }

                if (!foundTask) {
                    System.out.println(userTask + " is currently not on the to-do list.");
                }
            }

            else if (userInput.equals("list")) {
                if (taskList.size() == 0) {
                    System.out.println("Your to-do list is empty. Please add an item to your list using the 'add' command!");
                } else {
                    System.out.println("Please enter the name of the user you want to list tasks for (or press enter to list all tasks):");
                    String userName = scanner.nextLine().trim();
                    if (!userName.isEmpty()) {
                        // List tasks for a specific user
                        for (Task element : taskList) {
                            if (element.assignedToUser.equals(userName)) {
                                System.out.println("- " + element.id + " - " + element.name + " - " + (element.isComplete ? "completed" : "incomplete") + " - assigned to " + element.assignedToUser);
                            }
                        }
                    } else {
                        // List all tasks grouped by user
                        HashMap<String, ArrayList<Task>> tasksByUser = new HashMap<>();
                        for (Task element : taskList) {
                            ArrayList<Task> tasksForUser = tasksByUser.getOrDefault(element.assignedToUser, new ArrayList<Task>());
                            tasksForUser.add(element);
                            tasksByUser.put(element.assignedToUser, tasksForUser);
                        }
                        for (String user : tasksByUser.keySet()) {
                            System.out.println(user + ":");
                            ArrayList<Task> tasksForUser = tasksByUser.get(user);
                            for (Task task : tasksForUser) {
                                System.out.println("- " + task.id + " - " + task.name + " - " + (task.isComplete ? "completed" : "incomplete") + " - assigned to " + task.assignedToUser);
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
                        writer.write(task.id + " - " + task.name + " - " + task.isComplete + " - " + task.assignedToUser + "\n");
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