import javax.swing.*;
import java.awt.*;

public class GUI {
    public static void openGUI() {
        JFrame frame = new JFrame("To-Do List Application");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JCheckBox checkBox1 = new JCheckBox("Fontos tennivalo");
        checkBox1.setFont(new Font("Montserrat", Font.PLAIN, 16));
        checkBox1.setForeground(Color.DARK_GRAY);
        panel.add(checkBox1);

        JCheckBox checkBox2 = new JCheckBox("Halaszthatatlan feladat");
        checkBox2.setFont(new Font("Montserrat", Font.PLAIN, 16));
        checkBox2.setForeground(Color.DARK_GRAY);
        panel.add(checkBox2);

        JCheckBox checkBox3 = new JCheckBox("Az asszony azt mondta, hogy...");
        checkBox3.setFont(new Font("Montserrat", Font.PLAIN, 16));
        checkBox3.setForeground(Color.DARK_GRAY);
        panel.add(checkBox3);

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
