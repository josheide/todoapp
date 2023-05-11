
public class Task {
    int id;
    static int nextId = 1; // static ensures that the variable is shared
    String name;
    boolean isComplete;
    String assignedToUser;

    public int getId() {
        return id;
    }

    // This new getter method is used when deleting or completing a task with an ID.


    Task(int nextId, String name, boolean isComplete, String assignedToUser) {

        this.id = nextId;
        this.name = name;
        this.isComplete = isComplete;
        this.assignedToUser = assignedToUser;

    }
}