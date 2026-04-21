import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Question {
    String question;
    String[] options;
    int answer;

    Question(String question, String[] options, int answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }
}

public class QuizGUI extends JFrame implements ActionListener {

    ArrayList<Question> quiz = new ArrayList<>();

    JLabel questionLabel;
    JLabel timerLabel;

    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup group;

    JButton nextButton;

    int currentQuestion = 0;
    int score = 0;
    int timeLeft = 120; // 2 Minutes

    Timer timer;

    QuizGUI() {

        // ----------- Questions -----------
        quiz.add(new Question(
                "Java is platform ____ language",
                new String[]{"Dependent", "Independent", "Both", "None"},
                1));

        quiz.add(new Question(
                "Which loop executes at least once?",
                new String[]{"for", "while", "do while", "none"},
                2));

        quiz.add(new Question(
                "OOP stands for?",
                new String[]{"Object Oriented Programming", "Only One Program", "Open Object Programming", "None"},
                0));

        // ----------- Frame -----------
        setTitle("Quiz Application");
        setSize(500, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ----------- Timer Label -----------
        timerLabel = new JLabel("Time: 120");
        timerLabel.setBounds(380, 10, 100, 30);
        add(timerLabel);

        // ----------- Question Label -----------
        questionLabel = new JLabel();
        questionLabel.setBounds(30, 50, 420, 30);
        add(questionLabel);

        // ----------- Options -----------
        opt1 = new JRadioButton();
        opt1.setBounds(50, 100, 300, 30);

        opt2 = new JRadioButton();
        opt2.setBounds(50, 130, 300, 30);

        opt3 = new JRadioButton();
        opt3.setBounds(50, 160, 300, 30);

        opt4 = new JRadioButton();
        opt4.setBounds(50, 190, 300, 30);

        group = new ButtonGroup();
        group.add(opt1);
        group.add(opt2);
        group.add(opt3);
        group.add(opt4);

        add(opt1);
        add(opt2);
        add(opt3);
        add(opt4);

        // ----------- Next Button -----------
        nextButton = new JButton("Next");
        nextButton.setBounds(180, 240, 100, 30);
        nextButton.addActionListener(this);
        add(nextButton);

        // ----------- Timer Logic -----------
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);

            if (timeLeft <= 0) {
                checkAnswer();
            }
        });

        loadQuestion();
        timer.start();

        setVisible(true);
    }

    void loadQuestion() {

        if (currentQuestion >= quiz.size()) {
            timer.stop();
            JOptionPane.showMessageDialog(this,
                    "Quiz Finished!\nScore: " + score + "/" + quiz.size());
            System.exit(0);
        }

        group.clearSelection();
        timeLeft = 120;

        Question q = quiz.get(currentQuestion);

        questionLabel.setText("Q" + (currentQuestion + 1) + ": " + q.question);

        opt1.setText(q.options[0]);
        opt2.setText(q.options[1]);
        opt3.setText(q.options[2]);
        opt4.setText(q.options[3]);
    }

    void checkAnswer() {

        Question q = quiz.get(currentQuestion);

        int selected = -1;

        if (opt1.isSelected()) selected = 0;
        if (opt2.isSelected()) selected = 1;
        if (opt3.isSelected()) selected = 2;
        if (opt4.isSelected()) selected = 3;

        if (selected == q.answer) {
            score++;
        }

        currentQuestion++;
        loadQuestion();
    }

    public void actionPerformed(ActionEvent e) {
        checkAnswer();
    }

    public static void main(String[] args) {
        new QuizGUI();
    }
}
