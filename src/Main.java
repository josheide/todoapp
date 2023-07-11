public class Main {

    public static void main(String[] args) {

        GUI gui = new GUI(); // This will be used later as we pass functionality

        TaskManager.loadTasks();
        UserManager.loadUsers();

        CLI.start();
    }
}