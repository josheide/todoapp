// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /**
         * For my array to take two value, a string and a boolean I created a class
         * Would a dictionary work?
         */
        class Task {
            String name;
            boolean isComplete;

            // The Task class also has a constructor method, which takes two arguments: name and isComplete.

            Task(String name, boolean isComplete) {
                this.name = name;
                this.isComplete = isComplete;
            }
        }

        ArrayList<Task> inputList = new ArrayList<Task>();

        String userInput = " ";

        Scanner scanner = new Scanner(System.in);
        System.out.println("In this app you can do three things!");
        System.out.println("- You can add tasks to your to do list, type: 'add' ");
        System.out.println("- You can remove items from your to do list, type: 'delete' ");
        System.out.println("- Or you can ask the program to show your to do list, type: 'list' ");
        System.out.println("- You can complete a task by typing 'complete' ");
        System.out.println("");

        while (!userInput.equals("exit")) {

            System.out.println("Please type what you would like to do now: ");
            System.out.println("If you would like to exit the app, type: 'exit'");

            userInput = scanner.nextLine();

            if (userInput.equals("add")) {

                System.out.println("Please type the task that you would like to add!");
                String userTask = scanner.nextLine();
                inputList.add (new Task(userTask, false));
                System.out.println (userTask + " has been added!");

            }

            else if (userInput.equals("delete")) {

                System.out.println("Please enter the name of the task you would like to remove:");

                String userTask = scanner.nextLine();

                boolean foundTask = false;

                for (Task task : inputList) {
                    if (task.name.equals(userTask)) {
                        inputList.remove(task);
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

                for (Task task : inputList) {
                    if (task.name.equals(userTask)) {
                        task.isComplete = true;
                        foundTask = true;
                        break;
                    }
                }
                if (!foundTask) {
                    System.out.println("Task not found.");
                }
            }

            else if (userInput.equals("list")) {
                for (Task element : inputList) {
                    if(element.isComplete){
                        System.out.println(element.name + " - is complete");
                    }
                    else {
                        System.out.println(element.name + " - not complete");
                    }
                }
            }

            else if (userInput.equals("exit")) {
                userInput = "exit";
            }

            else {
                System.out.println("Sorry, I did not catch that!");
            }
        }
    }
}