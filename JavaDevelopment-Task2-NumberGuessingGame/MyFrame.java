import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

class MyFrame extends JFrame implements ActionListener {

    JButton easy, medium, hard;
    JLabel l;

    Random r = new Random();

    int range, max;
    int round = 0;
    int wins = 0, losses = 0;

    ArrayList<String> history = new ArrayList<>();

    MyFrame() {

        easy = new JButton("Easy");
        medium = new JButton("Medium");
        hard = new JButton("Hard");

       easy.setBackground(new Color(46, 204, 113));
       medium.setBackground(new Color(241, 196, 15));
       hard.setBackground(new Color(231, 76, 60));

       easy.setForeground(Color.WHITE);
       medium.setForeground(Color.BLACK);
       hard.setForeground(Color.WHITE);

       easy.setOpaque(true);
       medium.setOpaque(true);
       hard.setOpaque(true);

       easy.setContentAreaFilled(true);
       medium.setContentAreaFilled(true);
       hard.setContentAreaFilled(true);

       easy.setBorderPainted(false);
       medium.setBorderPainted(false);
       hard.setBorderPainted(false);

       easy.setFocusPainted(false);
       medium.setFocusPainted(false);
       hard.setFocusPainted(false);
        l = new JLabel("Select Difficulty", JLabel.CENTER);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(easy, gbc);

        gbc.gridx = 1;
        add(medium, gbc);

        gbc.gridx = 2;
        add(hard, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(l, gbc);

        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);

        setSize(400, 220);
        setMinimumSize(new Dimension(300, 180));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addComponentListener(new ComponentAdapter() {

        public void componentResized(ComponentEvent e) {

        int h = getContentPane().getHeight();

        int buttonSize = Math.max(14, Math.min(40, h / 20));
        int labelSize = Math.max(16, Math.min(32, h / 25));

        easy.setFont(new Font("Arial", Font.BOLD, buttonSize));
        medium.setFont(new Font("Arial", Font.BOLD, buttonSize));
        hard.setFont(new Font("Arial", Font.BOLD, buttonSize));

        l.setFont(new Font("Arial", Font.BOLD, labelSize));
    }
});
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == easy) {
            range = 50;
            max = 10;
        }
        else if(e.getSource() == medium) {
            range = 100;
            max = 7;
        }
        else if(e.getSource() == hard) {
            range = 200;
            max = 5;
        }

        playGame();
    }

    void playGame() {

        boolean again = true;

        while(again) {

            round++;

            int number = r.nextInt(range) + 1;
            int attempts = 0;
            boolean won = false;

            while(attempts < max) {

                JPanel panel = new JPanel(new BorderLayout(10, 15));
panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

JLabel question = new JLabel(
    "<html>Enter a number between 1 and " + range +
    "<br>Round: " + round +
    "<br>Attempt: " + (attempts + 1) + "/" + max +
    "</html>"
);

question.setFont(new Font("Arial", Font.BOLD, 16));

JTextField textField = new JTextField();
textField.setFont(new Font("Arial", Font.PLAIN, 17));
textField.setPreferredSize(new Dimension(300, 35));

panel.add(question, BorderLayout.NORTH);
panel.add(textField, BorderLayout.CENTER);

panel.setPreferredSize(new Dimension(380, 150));

int result = JOptionPane.showConfirmDialog(
    this,
    panel,
    "Number Guessing Game",
    JOptionPane.OK_CANCEL_OPTION,
    JOptionPane.PLAIN_MESSAGE
);

if(result != JOptionPane.OK_OPTION) {
    return;
}

String s = textField.getText();

                if(s == null) {
                    return;
                }

                int user;

                try {
                    user = Integer.parseInt(s);
                }
                catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Enter a valid number!");
                    continue;
                }

                if(user < 1 || user > range) {
                    JOptionPane.showMessageDialog(this,
                        "Enter a number within the given range!");
                    continue;
                }

                attempts++;

                if(number == user) {

                    JOptionPane.showMessageDialog(this,
                        "Correct! You Won!");

                    won = true;
                    wins++;
                    break;
                }
                else if(number > user) {

                    JOptionPane.showMessageDialog(this,
                        "Too Low!");
                }
                else {

                    JOptionPane.showMessageDialog(this,
                        "Too High!");
                }

                if(attempts == max) {

                    JOptionPane.showMessageDialog(this,
                        "You Lost! Number was " + number);

                    losses++;
                }
            }

            if(won) {
                history.add("Round " + round +
                    " - Guessed in " + attempts + " attempts");
            }
            else {
                history.add("Round " + round +
                    " - Lost after " + attempts + " attempts");
            }

            int choice = JOptionPane.showConfirmDialog(
                this,
                "Play Again?",
                "Next Round",
                JOptionPane.YES_NO_OPTION
            );

            if(choice == JOptionPane.YES_OPTION) {
                again = true;
            }
            else {
                again = false;

                String summary = "Game Summary\n\n";

                for(String s : history) {
                    summary += s + "\n";
                }

                summary += "\nTotal Rounds: " + round;
                summary += "\nWins: " + wins;
                summary += "\nLosses: " + losses;

                JOptionPane.showMessageDialog(this, summary);
            }
        }
    }
}