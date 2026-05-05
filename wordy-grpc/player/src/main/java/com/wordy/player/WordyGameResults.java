package com.wordy.player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WordyGameResults extends JFrame {
    public WordyGameResults(int yourScore, int opponentScore) {
        setTitle("Game Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(null);

        // ===== SAMPLE ROUND SCORES =====
        int[] yourRoundScores = {1, 0, 1};
        int[] opponentRoundScores = {0, 1, 0};

        // Result
        String result = yourScore > opponentScore ? "YOU WIN!" : yourScore < opponentScore ? "YOU LOST" : "TIE";
        JLabel resultLabel = new JLabel(result);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 32));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBounds(0, 20, 400, 60);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel);

        // Scores
        JLabel scoreLabel = new JLabel("Your Score: " + yourScore + "  |  Opponent: " + opponentScore);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(0, 80, 400, 60);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel);

        // ===== ROUND SCORE TABLE (ADDED) =====
        String[] columns = {"Round", "You", "Opponent"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (int i = 0; i < 3; i++) {
            model.addRow(new Object[]{
                    "Round " + (i + 1),
                    yourRoundScores[i],
                    opponentRoundScores[i]
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 120, 300, 80);
        add(scrollPane);

        // Dashboard Button
        JButton dashboardBtn = new JButton("Dashboard");
        dashboardBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        dashboardBtn.setBounds(40, 210, 150, 40); // moved slightly down
        dashboardBtn.setBackground(new Color(50,50,50));
        dashboardBtn.setForeground(Color.WHITE);
        dashboardBtn.setBorderPainted(false);
        dashboardBtn.setFocusPainted(false);
        dashboardBtn.addActionListener(e -> {
            new WordyDashboard().setVisible(true);
            dispose();
        });
        add(dashboardBtn);

        // Play Again Button
        JButton playAgainBtn = new JButton("Play Again");
        playAgainBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        playAgainBtn.setBounds(200, 210, 150, 40); // moved slightly down
        playAgainBtn.setBackground(new Color(50, 50, 50));
        playAgainBtn.setForeground(Color.WHITE);
        playAgainBtn.setBorderPainted(false);
        playAgainBtn.setFocusPainted(false);
        playAgainBtn.addActionListener(e -> {
            new WordyWaitingScreen(WordyGameResults.this).setVisible(true);
            dispose();
        });
        add(playAgainBtn);
    }

    public static void main(String[] args) {
        new WordyGameResults(2, 1).setVisible(true); // example total scores
    }
}