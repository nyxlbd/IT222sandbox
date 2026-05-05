package com.wordy.admin;

import javax.swing.*;
import java.awt.*;

public class ManagePlayersUI extends JFrame {

    public ManagePlayersUI() {
        setTitle("Wordy - Admin");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 30));

        // ===== Main Container =====
        JPanel mainContainer = new JPanel();
        mainContainer.setBounds(20, 20, 940, 620);
        mainContainer.setBackground(new Color(80, 80, 80));
        mainContainer.setLayout(null);
        add(mainContainer);

        // ===== Header =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 940, 80);
        headerPanel.setBackground(new Color(80, 80, 80));
        headerPanel.setLayout(null);
        mainContainer.add(headerPanel);

        JLabel title = new JLabel("Wordy - Admin");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(30, 20, 200, 40);
        headerPanel.add(title);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(820, 25, 80, 25);
        logoutBtn.setBackground(new Color(200, 200, 200));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        headerPanel.add(logoutBtn);

        // ===== Divider Title =====
        JPanel divider = new JPanel();
        divider.setBounds(0, 80, 940, 70);
        divider.setLayout(null);
        divider.setOpaque(false); // no background

        JLabel sectionTitle = new JLabel("Player Management");
        sectionTitle.setBounds(20, 15, 400, 40);
        sectionTitle.setHorizontalAlignment(SwingConstants.LEFT);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        sectionTitle.setForeground(Color.WHITE);

        divider.add(sectionTitle);
        mainContainer.add(divider);

        // ===== Search Bar =====
        JTextField searchField = new JTextField("Search by Username");
        searchField.setBounds(680, 170, 200, 25);
        mainContainer.add(searchField);

        // ===== Table Panel =====
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(20, 210, 900, 360);
        tablePanel.setBackground(new Color(220, 220, 220));
        tablePanel.setLayout(null);
        mainContainer.add(tablePanel);

        // ===== Table Headers =====
        String[] headers = {"Username", "Status", "Wins", "Joined", "Actions"};
        int[] xPos = {80, 220, 360, 500, 680};

        for (int i = 0; i < headers.length; i++) {
            JLabel headerLabel = new JLabel(headers[i]);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            headerLabel.setBounds(xPos[i], 10, 120, 30);
            tablePanel.add(headerLabel);
        }

        // ===== Sample Data =====
        for (int i = 0; i < 5; i++) {
            int y = 50 + (i * 60);

            JLabel username = new JLabel("Name");
            username.setBounds(xPos[0], y, 100, 30);
            tablePanel.add(username);

            JLabel status = new JLabel("Online");
            status.setBounds(xPos[1], y, 100, 30);
            tablePanel.add(status);

            JLabel wins = new JLabel("100");
            wins.setBounds(xPos[2], y, 100, 30);
            tablePanel.add(wins);

            JLabel joined = new JLabel("2026/01/01");
            joined.setBounds(xPos[3], y, 120, 30);
            tablePanel.add(joined);

            // Edit Button
            JButton editBtn = new JButton("Edit");
            editBtn.setBounds(xPos[4], y, 60, 25);
            editBtn.setBackground(new Color(60, 60, 60)); // darker
            editBtn.setForeground(Color.WHITE);
            editBtn.setFocusPainted(false);
            editBtn.setBorder(BorderFactory.createLineBorder(new Color(90,90,90), 1, true));
            tablePanel.add(editBtn);

            // Delete Button
            JButton deleteBtn = new JButton("Delete");
            deleteBtn.setBounds(xPos[4] + 70, y, 70, 25);
            deleteBtn.setBackground(new Color(60, 60, 60)); // darker
            deleteBtn.setForeground(Color.WHITE);
            deleteBtn.setFocusPainted(false);
            deleteBtn.setBorder(BorderFactory.createLineBorder(new Color(90,90,90), 1, true));
            tablePanel.add(deleteBtn);

            // Row Divider
            if (i < 4) {
                JSeparator sep = new JSeparator();
                sep.setBounds(20, y + 40, 860, 1);
                sep.setForeground(new Color(180, 180, 180));
                tablePanel.add(sep);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagePlayersUI().setVisible(true));
    }
}