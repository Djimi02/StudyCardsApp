package com.example;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DataRepository {
    private static DataRepository instance;

    private List<Question> questions;
    private Random random;

    private Connection connection;
    Statement stmt;

    private DataRepository() {
        this.questions = new ArrayList<>();
        this.random = new Random();

        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql:StudyCardsDB", "postgres", "mitkopostgres");

            if (connection != null) {
                stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery("SELECT * FROM \"Questions\"");
                while (resultSet.next()) {
                    String text = resultSet.getString("Text");
                    String[] answers = (String[])resultSet.getArray("Answers").getArray();
                    Boolean[] answersBol = (Boolean[])resultSet.getArray("AnswersBol").getArray();
                    HashMap<String, Boolean> map = new HashMap<>();
                    for (int i = 0; i < answers.length; i++) {
                        map.put(answers[i], answersBol[i]);
                    }
                    Question question = new Question(text, map);
                    this.questions.add(question);
                }

                stmt.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }

        return instance;
    }

    public void addQuestion(Question question) {
        if (question == null) {
            return;
        }

        this.questions.add(question);

        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql:StudyCardsDB", "postgres", "mitkopostgres");

            if (connection != null) {

                String sql = "INSERT INTO \"Questions\" (\"Text\",\"Answers\",\"AnswersBol\") VALUES (?,?,?)";
                PreparedStatement stmt1 = connection.prepareStatement(sql);
                Array answers = connection.createArrayOf("text", question.getAnswers().keySet().toArray());
                Array answersBol = connection.createArrayOf("boolean", question.getAnswers().values().toArray());
                stmt1.setString(1, question.getText());
                stmt1.setArray(2, answers);
                stmt1.setArray(3, answersBol);
                stmt1.executeUpdate();

                stmt1.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(Question question) {
        if (question == null) {
            return;
        }

        this.questions.remove(question);

        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql:StudyCardsDB", "postgres", "mitkopostgres");

            if (connection != null) {
                String sql = "DELETE FROM \"Questions\" WHERE \"Text\" = ?";
                PreparedStatement stmt1 = connection.prepareStatement(sql);
                stmt1.setString(1, question.getText());
                stmt1.executeUpdate();

                stmt1.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        return this.questions.get(random.nextInt(questions.size()));
    }
}
