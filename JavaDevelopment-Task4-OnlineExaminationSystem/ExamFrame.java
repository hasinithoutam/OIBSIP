import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class ExamFrame extends JFrame {

    String name;
    int current = 0, score = 0;
    int timeLeft = 60;

    String[] questions = {
        "Which keyword creates a class?",
        "Which method starts Java execution?",
        "Java is an example of?",
        "Which keyword is used for inheritance?",
        "Which symbol ends a statement?"
    };

    String[][] options = {
        {"class", "int", "void", "new"},
        {"start()", "main()", "run()", "execute()"},
        {"Procedural", "Object Oriented", "Markup", "Assembly"},
        {"this", "super", "extends", "static"},
        {".", ":", ";", ","}
    };

    int[] answers = {0, 1, 1, 2, 2};
    int[] selected = {-1, -1, -1, -1, -1};

    JLabel question, timerLabel;
    JRadioButton[] buttons = new JRadioButton[4];

    ButtonGroup group = new ButtonGroup();

    JButton previous, next, submit;

    javax.swing.Timer timer;

    ExamFrame(String name) {

        this.name = name;

        setTitle("Online Examination");
        setSize(400, 350);
        addComponentListener(new ComponentAdapter() {
    public void componentResized(ComponentEvent e) {

        int width = getWidth();
        int height = getHeight();

        int questionSize = Math.max(16, Math.min(32, width / 35));
        int optionSize = Math.max(14, Math.min(26, width / 40));
        int buttonSize = Math.max(13, Math.min(22, width / 45));

        question.setFont(new Font("Arial", Font.BOLD, questionSize));
        timerLabel.setFont(new Font("Arial", Font.BOLD, optionSize));

        for (int i = 0; i < 4; i++) {
            buttons[i].setFont(new Font("Arial", Font.PLAIN, optionSize));
        }

        previous.setFont(new Font("Arial", Font.BOLD, buttonSize));
        next.setFont(new Font("Arial", Font.BOLD, buttonSize));
        submit.setFont(new Font("Arial", Font.BOLD, buttonSize));
    }
});
        setLayout(new BorderLayout(10, 10));

        question = new JLabel();
        timerLabel = new JLabel("Time Left: 60 sec");

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(timerLabel);
        top.add(question);

        add(top, BorderLayout.NORTH);

        JPanel optionPanel = new JPanel(new GridLayout(4, 1));

        for (int i = 0; i < 4; i++) {
            buttons[i] = new JRadioButton();
            group.add(buttons[i]);
            optionPanel.add(buttons[i]);
        }

        add(optionPanel, BorderLayout.CENTER);

        previous = new JButton("Previous");
        next = new JButton("Next");
        submit = new JButton("Submit");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(previous);
        buttonPanel.add(next);
        buttonPanel.add(submit);

        add(buttonPanel, BorderLayout.SOUTH);

        previous.addActionListener(e -> {
            saveAnswer();

            if (current > 0) {
                current--;
                showQuestion();
            }
        });

        next.addActionListener(e -> {
            saveAnswer();

            if (current < 4) {
                current++;
                showQuestion();
            }
        });

        submit.addActionListener(e -> finishExam());

        showQuestion();

        timer = new javax.swing.Timer(1000, e -> {
            timeLeft--;

            timerLabel.setText("Time Left: " + timeLeft + " sec");

            if (timeLeft <= 0) {
                timer.stop();
                finishExam();
            }
        });

        timer.start();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void showQuestion() {

        question.setText(questions[current]);
        group.clearSelection();

        for (int i = 0; i < 4; i++) {
            buttons[i].setText(options[current][i]);

            if (selected[current] == i) {
                buttons[i].setSelected(true);
            }
        }

        previous.setEnabled(current > 0);
        next.setVisible(current < questions.length - 1);
    }

    void saveAnswer() {

        selected[current] = -1;

        for (int i = 0; i < 4; i++) {
            if (buttons[i].isSelected()) {
                selected[current] = i;
            }
        }
    }

    void finishExam() {

        saveAnswer();

        if (timer != null) {
            timer.stop();
        }

        score = 0;

        for (int i = 0; i < questions.length; i++) {
            if (selected[i] == answers[i]) {
                score++;
            }
        }

        dispose();
        new ResultFrame(name, score);
    }
}