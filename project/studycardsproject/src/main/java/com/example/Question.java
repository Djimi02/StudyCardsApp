package com.example;

import java.util.Map;

public class Question {

    private String text;
    private Map<String, Boolean> answers;

    public Question(String text, Map<String,Boolean> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String,Boolean> getAnswers() {
        return this.answers;
    }

    public void setAnswers(Map<String,Boolean> answers) {
        this.answers = answers;
    }


    @Override
    public String toString() {
        String output = "Question: " + this.text + "\n" +
            "Answers: \n";

        for (String answer : this.answers.keySet()) {
            output += answer + " - " + answers.get(answer) + "\n";
        }

        return output;
    }

}
