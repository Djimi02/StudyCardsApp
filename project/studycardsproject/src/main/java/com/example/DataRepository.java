package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

            if (connection == null) {
                System.out.println(" CONNECTION LOST");
            } else {
                System.out.println("CONNECTION HERE");
                stmt = connection.createStatement();
                String sql = "CREATE TABLE COMPANY " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " AGE            INT     NOT NULL, " +
                " ADDRESS        CHAR(50), " +
                " SALARY         REAL)";
                stmt.executeUpdate(sql);
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
        this.questions.add(question);
    }

    public void printAll() {
        for (Question question : questions) {
            System.out.println(question.toString());
        }
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        return this.questions.get(random.nextInt(questions.size()));
    }
}
