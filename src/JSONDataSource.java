import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class JSONDataSource {

    public static ArrayList<User> loadUsersFromFile(String fileName) {
        ArrayList<User> userArrayList = new ArrayList<User>();
        File userListFile = new File(fileName);

        try {
            if (!userListFile.exists()) {
                userListFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(userListFile));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            if (jsonBuilder.length() == 0) {
                System.out.println("The user list file is empty.");
                return userArrayList;
            }

            // Convert the JSON data to a list of users
            Gson gson = new GsonBuilder().create();
            User[] users = gson.fromJson(jsonBuilder.toString(), User[].class);

            // Populate the userArrayList
            userArrayList.clear();
            for (User user : users) {
                userArrayList.add(user);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading the user list file: " + e.getMessage());
            e.printStackTrace();
        }
        return userArrayList;
    }

    public static void saveUsersToFile(ArrayList<User> userArrayList) {
        String filePath = "userListJSON.json";
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(userArrayList);

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
