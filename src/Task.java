
class Task {
    String name;
    boolean isComplete;
    String assignedToUser;
    int id;
    static int nextId = 1; // static ensures that the variable is shared


    Task(int id, String name, boolean isComplete, String assignedToUser) {

        this.name = name;
        this.isComplete = isComplete;
        this.assignedToUser = assignedToUser;
        this.id = nextId;
        nextId++;
    }
}