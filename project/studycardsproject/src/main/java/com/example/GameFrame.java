package com.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame implements ActionListener {

    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 300;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu optionsMenu = new JMenu("Options");
    private JMenuItem showQuestionsMenuItem = new JMenuItem("Show cards");
    private JMenuItem addQuestionsMenuItem = new JMenuItem("Add card");

    private AddQuestionsPanel activeAddQuestPanel;
    private ShowQuestionsPanel activeShowQuestionsPanel;
    
    public GameFrame() {
        showQuestionsMenuItem.addActionListener(this);
        addQuestionsMenuItem.addActionListener(this);
        optionsMenu.add(showQuestionsMenuItem);
        optionsMenu.add(addQuestionsMenuItem);
        menuBar.add(optionsMenu);
        this.setJMenuBar(menuBar);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Quiz app");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showQuestionsMenuItem) {
            this.activeShowQuestionsPanel = new ShowQuestionsPanel(this);
            this.getContentPane().removeAll();
            this.add(activeShowQuestionsPanel);
            this.revalidate();
            this.activeShowQuestionsPanel.loadQuestion(DataRepository.getInstance().getRandomQuestion());
        } else if (e.getSource() == addQuestionsMenuItem) {
            this.activeAddQuestPanel = new AddQuestionsPanel(this);
            this.getContentPane().removeAll();
            this.add(activeAddQuestPanel);
            this.revalidate();
        }
    }
}