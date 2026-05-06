package com.wordy.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.wordy.common.WordyLoginUI;
import com.wordy.player.service.GameStateManager;

public class WordyDashboard extends JFrame {

    private String currentUsername;

    public WordyDashboard() {
        this(null);
    }

    public WordyDashboard(String username) {
        this.currentUsername = username;
        
        // Update the GameStateManager singleton with the logged-in user
        if (username != null) {
            GameStateManager manager = GameStateManager.getInstance();
            manager.setCurrentUser(username, "PLAYER");
        }
        
        setTitle("Wordy - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(null);
        setLocationRelativeTo(null);

        // Main Container Panel
        JPanel mainContainer = new JPanel();
        mainContainer.setBackground(new Color(80, 80, 80));
        mainContainer.setBounds(20, 20, 940, 620);
        mainContainer.setLayout(null);
        add(mainContainer);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(80, 80, 80));
        headerPanel.setBounds(0, 0, 940, 100);
        headerPanel.setLayout(null);
        mainContainer.add(headerPanel);

        // Wordy Title
        JLabel wordyTitle = new JLabel("Wordy");
        wordyTitle.setFont(new Font("Arial", Font.BOLD, 28));
        wordyTitle.setForeground(Color.WHITE);
        wordyTitle.setBounds(40, 30, 200, 40);
        headerPanel.add(wordyTitle);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutBtn.setBounds(820, 35, 80, 25);
        logoutBtn.setBackground(new Color(200, 200, 200));
        logoutBtn.setForeground(Color.BLACK);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call server logout using backend service
                GameStateManager manager = GameStateManager.getInstance();
                if (manager.isLoggedIn()) {
                    boolean logoutSuccess = manager.logout();
                    if (logoutSuccess) {
                        JOptionPane.showMessageDialog(null, 
                            "Successfully logged out", 
                            "Logout", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Error during logout", 
                            "Logout Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                dispose();
                WordyLoginUI login = new WordyLoginUI((username, userRole) -> {
                    if ("PLAYER".equalsIgnoreCase(userRole)) {
                        new WordyDashboard(username).setVisible(true);
                    } else if ("ADMIN".equalsIgnoreCase(userRole)) {
                        JOptionPane.showMessageDialog(null, 
                            "You logged in as an admin. Please use AdminApp to access the admin dashboard.",
                            "Admin Access", JOptionPane.INFORMATION_MESSAGE);
                        new WordyLoginUI((u, r) -> {}).setVisible(true);
                    }
                });
                login.setVisible(true);
            }
        });
        headerPanel.add(logoutBtn);

        // Divider Line
        JPanel divider = new JPanel();
        divider.setBackground(Color.WHITE);
        divider.setBounds(0, 100, 1000, 2);
        mainContainer.add(divider);

        // Content Area Panel
        JPanel contentArea = new JPanel();
        contentArea.setBackground(new Color(80, 80, 80));
        contentArea.setBounds(0, 102, 940, 548);
        contentArea.setLayout(null);
        mainContainer.add(contentArea);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Player!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(350, 20, 300, 50);
        contentArea.add(welcomeLabel);

        // Start Game Button
        JPanel startGamePanel = new JPanel();
        startGamePanel.setBackground(new Color(200, 200, 200));
        startGamePanel.setBounds(100, 90, 750, 100);
        startGamePanel.setLayout(null);
        startGamePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startGamePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Join game through backend service
                GameStateManager manager = GameStateManager.getInstance();
                if (manager.isLoggedIn()) {
                    if (manager.joinGame()) {
                        JOptionPane.showMessageDialog(WordyDashboard.this, 
                            "Successfully joined game! Waiting for other players...", 
                            "Game Joined", JOptionPane.INFORMATION_MESSAGE);
                        // Open waiting screen
                        new WordyWaitingScreen(WordyDashboard.this).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(WordyDashboard.this, 
                            "Failed to join game. Try again.", 
                            "Join Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(WordyDashboard.this, 
                        "You must be logged in to play.", 
                        "Not Logged In", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        contentArea.add(startGamePanel);

        JLabel startGameTitle = new JLabel("Start Game!");
        startGameTitle.setFont(new Font("Arial", Font.BOLD, 20));
        startGameTitle.setForeground(new Color(80, 80, 80));
        startGameTitle.setBounds(20, 10, 300, 30);
        startGamePanel.add(startGameTitle);


        JLabel startGameDesc = new JLabel("Start a game, and compete with other players");
        startGameDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        startGameDesc.setForeground(new Color(100, 100, 100));
        startGameDesc.setBounds(20, 45, 500, 25);
        startGamePanel.add(startGameDesc);

        // LeaderBoard Button
        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setBackground(new Color(200, 200, 200));
        leaderboardPanel.setBounds(100, 210, 750, 100);
        leaderboardPanel.setLayout(null);
        leaderboardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leaderboardPanel.addMouseListener(new java.awt.event.MouseAdapter() { // Temporary Function LeaderBoard Button
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new WordyLeaderBoard(currentUsername).setVisible(true);
            }
        });
        contentArea.add(leaderboardPanel);

        JLabel leaderboardTitle = new JLabel("LeaderBoard");
        leaderboardTitle.setFont(new Font("Arial", Font.BOLD, 20));
        leaderboardTitle.setForeground(new Color(80, 80, 80));
        leaderboardTitle.setBounds(20, 10, 300, 30);
        leaderboardPanel.add(leaderboardTitle);

        JLabel leaderboardDesc = new JLabel("View Rankings, and best words in the games");
        leaderboardDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        leaderboardDesc.setForeground(new Color(100, 100, 100));
        leaderboardDesc.setBounds(20, 45, 500, 25);
        leaderboardPanel.add(leaderboardDesc);

        // Rules Panel
        JPanel rulesPanel = new JPanel();
        rulesPanel.setBackground(new Color(200, 200, 200));
        rulesPanel.setBounds(100, 330, 750, 140);
        rulesPanel.setLayout(null);
        contentArea.add(rulesPanel);

        JLabel rulesTitle = new JLabel("Rules of the Game");
        rulesTitle.setFont(new Font("Arial", Font.BOLD, 20));
        rulesTitle.setForeground(new Color(80, 80, 80));
        rulesTitle.setBounds(20, 10, 300, 30);
        rulesPanel.add(rulesTitle);

        JLabel rulesDesc = new JLabel("achuchuchuchuhcuhcuchu"); // Description of the Rules of the game
        rulesDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        rulesDesc.setForeground(new Color(100, 100, 100));
        rulesDesc.setBounds(20, 50, 500, 70);
        rulesDesc.setVerticalAlignment(SwingConstants.TOP);
        rulesPanel.add(rulesDesc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordyDashboard().setVisible(true));
    }
}