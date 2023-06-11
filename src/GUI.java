import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {

    static JCheckBox createCheckBox(Task task) {
        JCheckBox checkBox = new JCheckBox(task.getId() + " - " + task.getName());
        checkBox.setFont(new Font("Montserrat", Font.PLAIN, 14));
        checkBox.setSelected(task.isComplete());
        return checkBox;
    }

    public static void displayTasksGUI(String userInputName) {
        JFrame frame = new JFrame("Task List");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TodoApp.exitAndSave();
                frame.dispose();
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Task");
        buttonPanel.add(addButton);

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(taskPanel);

        for (Task task : TodoApp.taskList) {
            if (task.getAssignedToUser().equals(userInputName)) {
                JCheckBox checkBox = GUI.createCheckBox(task);
                taskPanel.add(checkBox);
            }
        }

        // Add panels to the frame
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add task button functionality
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(frame, "Enter task name:");
                if (taskName != null && !taskName.isEmpty()) {

                    Task newTask = TodoApp.addTask(userInputName, taskName);

                    if (newTask == null) {
                        //TODO: Display error to user.
                        return;
                    }

                    JCheckBox checkBox = GUI.createCheckBox(newTask);
                    taskPanel.add(checkBox);
                    frame.revalidate();

                }
            }
        });

        frame.setVisible(true);
    }
}
