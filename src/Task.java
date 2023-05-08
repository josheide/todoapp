
class Task {
    int id;
    static int nextId = 1; // static ensures that the variable is shared
    String name;
    boolean isComplete;
    String assignedToUser;



    Task(int id, String name, boolean isComplete, String assignedToUser) {

        this.id = nextId;
        nextId++;

        this.name = name;
        this.isComplete = isComplete;
        this.assignedToUser = assignedToUser;

    }
}