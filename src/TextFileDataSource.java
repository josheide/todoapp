import java.io.*;
import java.util.ArrayList;

public class TextFileDataSource {

    public static ArrayList<User> loadUsersFromFile(String fileName) {
        ArrayList<User> userArrayList = new ArrayList<User>();
        File userListFile = new File(fileName);
        try {
            if (!userListFile.exists()) {
                userListFile.createNewFile();
            }
            try (BufferedReader userReader = new BufferedReader(new FileReader(userListFile))) {
                String userLine;
                while ((userLine = userReader.readLine()) != null) {
                    String[] userData = userLine.split(" - ");
                    String userName = userData[0];
                    String userPassword = userData[1];
                    User newUser = new User(userName, userPassword);
                    userArrayList.add(newUser);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the user list file: " + e.getMessage());
            e.printStackTrace();
        }
        return userArrayList;
    }

    public static void saveUsersToFile(ArrayList<User> userArrayList) {
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
    }
}
