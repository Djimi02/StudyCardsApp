package com.example;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class AddQuestionsPanel extends JPanel implements ActionListener {

    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 600;

    private final SpringLayout layout = new SpringLayout();
    private final GameFrame parentFrame;

    private final DataRepository dataRepository;

    /* GUI */
    private JTextField questionTextTF = new JTextField(40);
    private JLabel questionTextLabel = new JLabel("Question:");
    private JTextField answer1TF = new JTextField(40);
    private JTextField answer2TF = new JTextField(40);
    private JTextField answer3TF = new JTextField(40);
    private JTextField answer4TF = new JTextField(40);
    private JCheckBox answer1CB = new JCheckBox("Correct", false);
    private JCheckBox answer2CB = new JCheckBox("Correct", false);
    private JCheckBox answer3CB = new JCheckBox("Correct", false);
    private JCheckBox answer4CB = new JCheckBox("Correct", false);
    private JButton addQuestionBTN = new JButton("Add question");
    
    public AddQuestionsPanel(GameFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.dataRepository = DataRepository.getInstance();

        this.addQuestionBTN.addActionListener(this);

        this.setLayout(layout);

        this.add(questionTextTF);
        this.add(questionTextLabel);
        this.add(answer1TF);
        this.add(answer2TF);
        this.add(answer3TF);
        this.add(answer4TF);
        this.add(answer1CB);
        this.add(answer2CB);
        this.add(answer3CB);
        this.add(answer4CB);
        this.add(addQuestionBTN);

        // First row constraints
        layout.putConstraint(SpringLayout.NORTH, questionTextLabel, 10, SpringLayout.NORTH, parentFrame.getContentPane());
        layout.putConstraint(SpringLayout.WEST, questionTextLabel, 10, SpringLayout.WEST, parentFrame.getContentPane());

        layout.putConstraint(SpringLayout.NORTH, questionTextTF, 10, SpringLayout.NORTH, parentFrame.getContentPane());
        layout.putConstraint(SpringLayout.WEST, questionTextTF, 10, SpringLayout.EAST, questionTextLabel);

        // Second row constraints
        layout.putConstraint(SpringLayout.NORTH, answer1CB, 10, SpringLayout.SOUTH, questionTextTF);
        layout.putConstraint(SpringLayout.WEST, answer1CB, 10, SpringLayout.EAST, parentFrame.getContentPane());

        layout.putConstraint(SpringLayout.NORTH, answer1TF, 0, SpringLayout.NORTH, answer1CB);
        layout.putConstraint(SpringLayout.WEST, answer1TF, 10, SpringLayout.EAST, answer1CB);
        
        // Third row constraints
        layout.putConstraint(SpringLayout.NORTH, answer2CB, 10, SpringLayout.SOUTH, answer1CB);
        layout.putConstraint(SpringLayout.WEST, answer2CB, 10, SpringLayout.EAST, parentFrame.getContentPane());

        layout.putConstraint(SpringLayout.NORTH, answer2TF, 0, SpringLayout.NORTH, answer2CB);
        layout.putConstraint(SpringLayout.WEST, answer2TF, 10, SpringLayout.EAST, answer2CB);

        // Fourth row constraints
        layout.putConstraint(SpringLayout.NORTH, answer3CB, 10, SpringLayout.SOUTH, answer2CB);
        layout.putConstraint(SpringLayout.WEST, answer3CB, 10, SpringLayout.EAST, parentFrame.getContentPane());

        layout.putConstraint(SpringLayout.NORTH, answer3TF, 0, SpringLayout.NORTH, answer3CB);
        layout.putConstraint(SpringLayout.WEST, answer3TF, 10, SpringLayout.EAST, answer3CB);

        // Fifth row constraints
        layout.putConstraint(SpringLayout.NORTH, answer4CB, 10, SpringLayout.SOUTH, answer3CB);
        layout.putConstraint(SpringLayout.WEST, answer4CB, 10, SpringLayout.EAST, parentFrame.getContentPane());

        layout.putConstraint(SpringLayout.NORTH, answer4TF, 0, SpringLayout.NORTH, answer4CB);
        layout.putConstraint(SpringLayout.WEST, answer4TF, 10, SpringLayout.EAST, answer4CB);

        // Sixth row constraints
        layout.putConstraint(SpringLayout.NORTH, addQuestionBTN, 10, SpringLayout.SOUTH, answer4TF);
        layout.putConstraint(SpringLayout.WEST, addQuestionBTN, 250, SpringLayout.WEST, parentFrame.getContentPane());

        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.requestFocus();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String questionTXT = questionTextTF.getText().trim();
        String answer1TXT = answer1TF.getText().trim();
        String answer2TXT = answer2TF.getText().trim();
        String answer3TXT = answer3TF.getText().trim();
        String answer4TXT = answer4TF.getText().trim();

        if (questionTXT.isEmpty()) {
            questionTextTF.setBackground(Color.red);
            return;
        }

        if (answer1TXT.isEmpty()) {
            answer1TF.setBackground(Color.red);
            return;
        } else if (answer2TXT.isEmpty()) {
            answer2TF.setBackground(Color.red);
            return;
        }

        Map<String, Boolean> questionsAndAnswers = new HashMap<>();
        questionsAndAnswers.put(answer1TXT, answer1CB.isSelected());
        questionsAndAnswers.put(answer2TXT, answer2CB.isSelected());

        if (!answer3TXT.isEmpty()) {
            questionsAndAnswers.put(answer3TXT, answer3CB.isSelected());
        }
        if (!answer4TXT.isEmpty()) {
            questionsAndAnswers.put(answer4TXT, answer4CB.isSelected());
        }

        dataRepository.addQuestion(new Question(questionTXT, questionsAndAnswers));

        clearFields();
    }

    public void clearFields() {
        questionTextTF.setText("");
        questionTextTF.setBackground(Color.white);

        answer1TF.setText("");
        answer1TF.setBackground(Color.white);

        answer2TF.setText("");
        answer2TF.setBackground(Color.white);

        answer3TF.setText("");
        answer3TF.setBackground(Color.white);

        answer4TF.setText("");
        answer4TF.setBackground(Color.white);

        answer1CB.setSelected(false);
        answer2CB.setSelected(false);
        answer3CB.setSelected(false);
        answer4CB.setSelected(false);
    }
}
