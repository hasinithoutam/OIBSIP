import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    LoginFrame() {

        setTitle("Online Examination");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel label = new JLabel("Enter Your Name:");
        label.setFont(new Font("Arial", Font.BOLD, 22));

        JTextField name = new JTextField(20);
        name.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton login = new JButton("Login");
        login.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridy = 1;
        panel.add(name, gbc);

        gbc.gridy = 2;
        panel.add(login, gbc);

        login.addActionListener(e -> {
            if (name.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter your name");
            } else {
                dispose();
                new ExamFrame(name.getText());
            }
        });

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}