import javax.swing.*;
import java.awt.GridBagLayout;

public class ResultFrame extends JFrame {

    ResultFrame(String name, int score) {

        setTitle("Examination Result");
        setSize(300,200);
        setLayout(null);

        JLabel student = new JLabel("Name: " + name);
        student.setBounds(70, 30, 200, 30);

        JLabel result = new JLabel("Your Score: " + score + "/5");
        result.setBounds(70, 70, 200, 30);

        JButton close = new JButton("Close");
        close.setBounds(90, 110, 100, 30);
        close.addActionListener(e -> System.exit(0));

        add(student);
        add(result);
        add(close);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}