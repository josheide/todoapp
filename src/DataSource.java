import java.util.ArrayList;

public interface DataSource {
    ArrayList<User> loadUsersFromFile(String fileName);
    void saveUsersToFile(ArrayList<User> userArrayList);
}
