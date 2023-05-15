
public class Task {
    private int id;
    String name;
    boolean isComplete;
    String assignedToUser;

    Task(int id, String name, boolean isComplete, String assignedToUser) {
        this.id = id;
        this.name = name;
        this.isComplete = isComplete;
        this.assignedToUser = assignedToUser;
    }

    int getId() {
        return id;
    }
}