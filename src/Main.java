// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.*;
import java.io.*;

public class Main {

    static ArrayList<Task> taskList = new ArrayList<Task>();
    static ArrayList<User> userArrayList = new ArrayList<User>();
    static int nextID = 1;
    static Scanner scanner = new Scanner(System.in);

    public static void welcomeMessage(){
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

    public static boolean authenticateUser(String username, String password) {
        for (User element : userArrayList) {
            if (element.userName.equals(username) && element.password.equals(password)) {
                return true;
            }
        }
        return false;
    }
    public static void addTask() {
        System.out.println("Please type the task that you would like to add!");
        String userTask = scanner.nextLine();

        if (!userTask.isEmpty()) {
            System.out.println("Please type the name of the user the task is assigned to!");
            String userAssigned = scanner.nextLine();

            if (!userAssigned.isEmpty()) {
                taskList.add(new Task(nextID, userTask, false, userAssigned));
                System.out.println(userTask + " has been added with the unique identifier: " + nextID); //
                nextID++;

            } else if (userAssigned.isEmpty()) {
                System.out.println("You must assign the task to a person! Please try to add this task again!");
            }
        } else {
            System.out.println("You cannot enter an empty field as a task, please try again!");
        }
    }

    public static void deleteTask() {
        System.out.println("Please enter the name of the user you want to delete the task of");
        String userName = scanner.nextLine();

        if (!userName.isEmpty()) {
            boolean foundUser = false;
            for (Task element : taskList) {
                if (element.assignedToUser.equals(userName)) {
                    foundUser = true;
                    if (element.isComplete) {
                        System.out.println("- " + element.getId() + " - " + element.name + " - completed by " + element.assignedToUser);
                    } else {
                        System.out.println("- " + element.getId() + " - " + element.name + " - incomplete, assigned to " + element.assignedToUser);
                    }
                }
            }
            if (!foundUser) {
                System.out.println("There are no tasks saved for the user: " + userName);
            } else {
                System.out.println("Please enter the ID of the task you would like to remove:");

                String userInputTaskNumber = scanner.next();
                scanner.nextLine(); // Without this line the program gets confused and immediately spits out "Sorry, I did not catch that"

                try {
                    int taskNumber = Integer.parseInt(userInputTaskNumber);

                    boolean foundTask = false;
                    for (Task task : taskList) {
                        if (task.getId() == taskNumber && task.assignedToUser.equals(userName)) {
                            taskList.remove(task);
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
    public static void completeTask() {

        System.out.println("Please enter the ID of the task you would like to complete:");

        String userInputTaskNumber = scanner.next();
        scanner.nextLine(); // Without this line the program gets confused and acts as if I gave it an empty string as a command.

        try {
            int taskNumber = Integer.parseInt(userInputTaskNumber);

            boolean foundTask = false;
            for (Task task : taskList) {
                if (task.getId() == taskNumber) {
                    task.isComplete = true;
                    foundTask = true;
                    System.out.println("Congratulations, you completed: " + task.name);
                    break;
                } else if (task.getId() == taskNumber && task.isComplete) {
                    System.out.println("You already completed " + task.name);
                }
            } if (!foundTask) {
                System.out.println("There is no task with that ID");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid task number.");
        }

    }
    public static void listTask() {
        if (taskList.size() == 0) {
            System.out.println("Your to-do list is empty. Please add an item to your list using the 'add' command!");
        } else {
            System.out.println("Please enter the name of the user you want to list tasks for (or press enter to list all tasks):");
            String userName = scanner.nextLine().trim();
            if (!userName.isEmpty()) {
                // List tasks for a specific user
                for (Task element : taskList) {
                    if (element.assignedToUser.equals(userName)) {
                        System.out.println("- " + element.getId() + " - " + element.name + " - " + (element.isComplete ? "completed" : "incomplete") + " - assigned to " + element.assignedToUser);
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
                        System.out.println("- " + task.getId() + " - " + task.name + " - " + (task.isComplete ? "completed" : "incomplete") + " - assigned to " + task.assignedToUser);
                    }
                }
            }
        }
    }
    public static void exitAndSave() {

        String filePath = "todoListFile.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Task task : taskList) {
                writer.write(task.getId() + " - " + task.name + " - " + task.isComplete + " - " + task.assignedToUser + "\n");
            }
            writer.close(); // VERY IMPORTANT, otherwise the save is not complete

        } catch (Exception e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void openingMessage(){
        System.out.println("Welcome! What would you like to do?");
        System.out.println("1. Add a new user");
        System.out.println("2. Delete an existing user");
        System.out.println("3. Log in");
        System.out.println("4. Exit");
        System.out.println(" ");
    }

    public static void loginAction() {
        boolean exit = false;
        while (!exit) {
            openingMessage();
            System.out.println("Please enter the your choice (the number!): ");
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
                    System.out.println("Sorry, but " + userInputName + "already has an account registered.");
                    break;
                }

                if (!foundUser) {
                    System.out.println("To register an account, please enter a password!");
                    String userInputPassword = scanner.nextLine();

                    System.out.println("Please confirm your password!");
                    String userInputPassword2 = scanner.nextLine();

                    if (userInputPassword.equals(userInputPassword2) && !userInputPassword.isEmpty()) {
                        userArrayList.add(new User(userInputName,userInputPassword));

                        String filePath = "userList.txt";

                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                            for (User user : userArrayList) {
                                writer.write(user.userName + " - " + user.password + "\n");
                            }
                            writer.close(); // VERY IMPORTANT, otherwise the save is not complete

                        } catch (Exception e) {
                            System.out.println("An error occurred while writing to the file.");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Passwords do not match. Please try again!");
                    }
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
                    System.out.println("Please enter the password for " + userInputName);
                    String userInputPassword = scanner.nextLine();

                    boolean authenticated = authenticateUser(userInputName, userInputPassword);

                    if (authenticated) {
                        System.out.println("Login successful. Welcome, " +userInputName);

                        while(true) {

                            welcomeMessage();

                            System.out.print("Enter a command (or 'exit' to save and exit): ");

                            String userInput = scanner.nextLine();

                            if (userInput.equals("add")) {
                                addTask();
                            } else if (userInput.equals("delete")) {
                                deleteTask();
                            } else if (userInput.equals("complete")) {
                                completeTask();
                            } else if (userInput.equals("list")) {
                                listTask();
                            } else if (userInput.equals("exit")) {
                                exitAndSave();
                                break;
                            } else {
                                System.out.println("Sorry, I did not catch that!");
                            }
                        }
                    } else {
                        System.out.println("Invalid password. Please try again.");
                    }
                }

                    break;
                }
            }
        }
    public static void deleteUser() {
        System.out.println("Please enter the username of the user to delete: ");
        String username = scanner.nextLine();

        boolean foundUser = false;
        for (User user : userArrayList) {
            if (user.userName.equals(username)) {
                userArrayList.remove(user);
                foundUser = true;
                break;
            }
        }

        if (foundUser) {
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

            System.out.println("User " + username + " has been deleted successfully!");
        } else {
            System.out.println("User " + username + " does not exist. Deletion failed.");
        }
    }

    public static void main(String[] args) {
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
                int taskID = taskHasNoID ? nextID++ : Integer.parseInt(taskData[0]);
                String taskName = taskData[taskHasNoID ? 0 : 1];
                boolean taskIsComplete = Boolean.parseBoolean(taskData[taskHasNoID ? 1 : 2]);
                String taskUser = taskData[taskHasNoID ? 2 : 3];
                Task newTask = new Task(taskID, taskName, taskIsComplete, taskUser);
                taskList.add(newTask);
                // in the last version taskData had 3 properties. If the reader finds tasks without IDs, it assigns them an ID and shifts all the other properties to their appropriate locations.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (!userListFile.exists()) {
                userListFile.createNewFile();
            }
            BufferedReader userReader = new BufferedReader(new FileReader(userListFile));
            String userLine;
            while ((userLine = userReader.readLine()) != null) {
                String[] userData = userLine.split(" - ");
                String userName = userData[0];
                String userPassword = userData[1];
                User newUser = new User(userName, userPassword);
                userArrayList.add(newUser);
            }
            userReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int maxID = 0;
        for (Task task : taskList) {
            if (task.getId() > maxID) {
                maxID = task.getId();
            }
        }
        nextID = maxID + 1;

        while (true) {

            loginAction();

        }
    }
}