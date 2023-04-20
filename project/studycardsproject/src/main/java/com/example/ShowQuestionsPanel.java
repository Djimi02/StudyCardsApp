package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class ShowQuestionsPanel extends JPanel implements ActionListener {

    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 600;

    private final GameFrame parentFrame;
    private final SpringLayout layout = new SpringLayout();

    private Question activeQuestion;

    // private String[] answerChoices = new String[4];
    private List<String> answerChoices = new ArrayList<>();

    /* GUI */
    private JLabel questionTextJL = new JLabel("TEXT");
    private List<JButton> buttons = new ArrayList<JButton>() {
        {
            add(new JButton("answer1"));
            add(new JButton("answer2"));
            add(new JButton("answer3"));
            add(new JButton("answer4"));
        }
    };
    // private JButton[] buttons = new JButton[] 
    //     {new JButton("answer1"), new JButton("answer2"), new JButton("answer3"), new JButton("answer4")};
    private JButton nextQuestionBTN = new JButton("Next Question");
    
    public ShowQuestionsPanel(GameFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.setLayout(layout);

        this.add(questionTextJL);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).addActionListener(this);
            this.add(buttons.get(i));
        }
        this.add(nextQuestionBTN);
        nextQuestionBTN.addActionListener(this);

        // First row constraints
        layout.putConstraint(SpringLayout.NORTH, questionTextJL, 10, SpringLayout.NORTH, parentFrame.getContentPane());
        layout.putConstraint(SpringLayout.WEST, questionTextJL, 10, SpringLayout.WEST, parentFrame.getContentPane());

        // Second row constraints
        layout.putConstraint(SpringLayout.NORTH, buttons.get(0), 10, SpringLayout.SOUTH, questionTextJL);
        layout.putConstraint(SpringLayout.WEST, buttons.get(0), 10, SpringLayout.WEST, parentFrame.getContentPane());

        layout.putConstraint(SpringLayout.NORTH, buttons.get(1), 10, SpringLayout.SOUTH, questionTextJL);
        layout.putConstraint(SpringLayout.WEST, buttons.get(1), 10, SpringLayout.EAST, buttons.get(0));

        layout.putConstraint(SpringLayout.NORTH, buttons.get(2), 10, SpringLayout.SOUTH, questionTextJL);
        layout.putConstraint(SpringLayout.WEST, buttons.get(2), 10, SpringLayout.EAST, buttons.get(1));

        layout.putConstraint(SpringLayout.NORTH, buttons.get(3), 10, SpringLayout.SOUTH, questionTextJL);
        layout.putConstraint(SpringLayout.WEST, buttons.get(3), 10, SpringLayout.EAST, buttons.get(2));

        // Third row constraints
        layout.putConstraint(SpringLayout.NORTH, nextQuestionBTN, 15, SpringLayout.SOUTH, buttons.get(3));
        layout.putConstraint(SpringLayout.WEST, nextQuestionBTN, 250, SpringLayout.WEST, parentFrame.getContentPane());

        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.requestFocus();
        this.setVisible(true);
    }

    public void loadQuestion(Question question) {
        if (question == null) {
            return;
        }

        this.activeQuestion = question;

        questionTextJL.setText(question.getText());

        // Get the answers from the question
        int numberOfAnswers = 0;
        for (Map.Entry<String, Boolean> set : question.getAnswers().entrySet()) {
            answerChoices.add(set.getKey());
            numberOfAnswers++;
        }

        for (int i = 0; i < buttons.size(); i++) {
            if (i < numberOfAnswers) {
                buttons.get(i).setText(answerChoices.get(i));
                System.out.println(answerChoices.get(i));
            } else {
                buttons.get(i).setVisible(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextQuestionBTN) {
            cleanPanel();

            loadQuestion(DataRepository.getInstance().getRandomQuestion());

            return;
        }

        for (JButton button : buttons) {
            button.setEnabled(false);

            Boolean bol = activeQuestion.getAnswers().get(button.getText());
            if (bol == null) {
                continue;
            }

            if (bol) {
                button.setBackground(Color.green);
            } else {
                button.setBackground(Color.red);
            }
        }
    }

    private void cleanPanel() {
        for (JButton button : this.buttons) {
            button.setBackground(Color.white);
            button.setEnabled(true);
            button.setVisible(true);
            answerChoices.clear();
        }
    }
}
