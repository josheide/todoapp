public class Main {

    public static void main(String[] args) {

        GUI gui = new GUI(); // This will be used later as we pass functionality

        String fileName = "todoListFile.txt";
        String fileNameJSON = "todoListFile.json";
        TaskManager.loadTasksFromFile(fileName);

        String userList = "userList.txt";
        String userListJSON = "userListJSON.json";
        UserManager.loadUsersFromFileJSON(userListJSON);

        CLI.start();
    }
}