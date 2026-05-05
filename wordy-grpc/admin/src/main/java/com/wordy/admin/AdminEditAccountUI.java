package com.wordy.admin;

import com.wordy.common.WordyLoginUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

public class AdminEditAccountUI extends JFrame {

    public AdminEditAccountUI(String username) {
        setTitle("Wordy - Admin");
        setSize(1000, 700);
        setMinimumSize(new Dimension(1000, 700));
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(30, 30, 30));


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(20, 20, 940, 620);
        mainPanel.setBackground(new Color(80, 80, 80));
        add(mainPanel);


        JLabel topTitle = new JLabel("Wordy - Admin");
        topTitle.setForeground(Color.WHITE);
        topTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topTitle.setBounds(30, 20, 300, 40);
        mainPanel.add(topTitle);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        styleTopButton(logoutBtn);
        logoutBtn.setBounds(820, 35, 80, 25);
        mainPanel.add(logoutBtn);
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // close ALL open windows of the admin
                for (Window window : Window.getWindows()) {
                    window.dispose();
                }

                // open login only
                new WordyLoginUI().setVisible(true);
            }
        });


        int frameWidth = 940;
        int frameHeight = 700;

        int panelWidth = 820;
        int panelHeight = 500;

        int centerX = (frameWidth - panelWidth) / 2;
        int centerY = (frameHeight - panelHeight) / 2;

        JPanel line = new JPanel();
        line.setBackground(Color.WHITE);
        line.setBounds(0, 80, 940, 2);
        mainPanel.add(line);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(centerX, centerY, panelWidth, panelHeight);
        formPanel.setBackground(new Color(210, 210, 210));
        mainPanel.add(formPanel);

        JLabel title = new JLabel("Edit Player Account");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(20, 20, 400, 40);
        formPanel.add(title);

        JLabel subtitle = new JLabel("Update Player information");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setBounds(20, 55, 300, 30);
        formPanel.add(subtitle);

        int leftLabelX = 60;
        int leftFieldX = 60;

        int rightLabelX = 380;
        int rightFieldX = 380;

        int fieldWidth = 260;
        int fieldHeight = 28;

        int y = 110;
        int gap = 60;


        JLabel userLbl = new JLabel("Username :");
        userLbl.setBounds(leftLabelX, y, 200, 20);
        formPanel.add(userLbl);

        y += 20;
        JTextField userField = new JTextField(username);
        userField.setBounds(leftFieldX, y, fieldWidth, fieldHeight);
        userField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        formPanel.add(userField);

        y -= 20;
        JLabel statusLbl = new JLabel("Status :");
        statusLbl.setBounds(rightLabelX, y, 200, 20);
        formPanel.add(statusLbl);

        y += 20;
        JTextField statusField = new JTextField("Online");
        statusField.setBounds(rightFieldX, y, 290, fieldHeight);
        statusField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        formPanel.add(statusField);

        y += gap;
        JLabel passLbl = new JLabel("Password :");
        passLbl.setBounds(leftLabelX, y, 200, 20);
        formPanel.add(passLbl);

        y += 20;
        JPasswordField passField = new JPasswordField();
        passField.setBounds(leftFieldX, y, fieldWidth, fieldHeight);
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        formPanel.add(passField);

        y += gap;
        JLabel memberLbl = new JLabel("Member Since :");
        memberLbl.setBounds(leftLabelX, y, 200, 20);
        formPanel.add(memberLbl);

        y += 20;
        JTextField memberField = new JTextField("2026/01/01");
        memberField.setBounds(leftFieldX, y, fieldWidth, fieldHeight);
        memberField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        formPanel.add(memberField);

        y += gap;
        JLabel remarksLbl = new JLabel("Remarks :");
        remarksLbl.setBounds(leftLabelX, y, 200, 20);
        formPanel.add(remarksLbl);

        y += 20;
        JTextField remarksField = new JTextField();
        remarksField.setBounds(leftFieldX, y, fieldWidth, fieldHeight);
        remarksField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        formPanel.add(remarksField);


        JButton saveBtn = new JButton("Save Changes");
        JButton cancelBtn = new JButton("Cancel");
        JButton deleteBtn = new JButton("Delete Account");

        styleBottomButton(saveBtn);
        styleBottomButton(cancelBtn);
        styleBottomButton(deleteBtn);

        int btnY = 420;
        int btnHeight = 35;

        saveBtn.setBounds(350, btnY, 160, btnHeight);
        cancelBtn.setBounds(520, btnY, 100, btnHeight);
        deleteBtn.setBounds(630, btnY, 170, btnHeight);

        formPanel.add(saveBtn);
        formPanel.add(cancelBtn);
        formPanel.add(deleteBtn);

        cancelBtn.addActionListener(e -> dispose());

        saveBtn.addActionListener(e -> {

            JDialog dialog = new JDialog(this, "Save Changes", true);
            dialog.setSize(350, 180);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(new Color(240, 240, 240));
            panel.setBounds(0, 0, 350, 180);
            dialog.add(panel);

            JLabel msg = new JLabel("Changes saved successfully!");
            msg.setBounds(0, 30, 350, 30);
            msg.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(msg);

            JButton okBtn = new JButton("OK");

            okBtn.setFocusPainted(false);
            okBtn.setBorderPainted(false);
            okBtn.setBackground(new Color(200, 200, 200));
            okBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            okBtn.setBounds(130, 90, 80, 30);
            panel.add(okBtn);

            okBtn.addActionListener(ev -> dialog.dispose());

            dialog.setVisible(true);
        });

        deleteBtn.addActionListener(e -> {

            JDialog dialog = new JDialog(this, "Confirm Delete", true);
            dialog.setSize(350, 180);
            dialog.setLayout(null);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(new Color(240, 240, 240));
            panel.setBounds(0, 0, 350, 180);
            dialog.add(panel);

            JLabel msg = new JLabel("Are you sure you want to delete this account?");
            msg.setBounds(30, 30, 300, 30);
            panel.add(msg);

            JButton yesBtn = new JButton("Yes");
            JButton noBtn = new JButton("No");

            yesBtn.setFocusPainted(false);
            yesBtn.setBorderPainted(false);
            yesBtn.setBackground(new Color(200, 200, 200));

            noBtn.setFocusPainted(false);
            noBtn.setBorderPainted(false);
            noBtn.setBackground(new Color(200, 200, 200));

            yesBtn.setBounds(70, 90, 80, 30);
            noBtn.setBounds(180, 90, 80, 30);

            panel.add(yesBtn);
            panel.add(noBtn);

            yesBtn.addActionListener(ev -> {
                String usernameToDelete = userField.getText();

                JOptionPane.showMessageDialog(this,
                        "Account '" + usernameToDelete + "' deleted.");

                dialog.dispose();
                dispose();
            });

            // NO action
            noBtn.addActionListener(ev -> dialog.dispose());

            dialog.setVisible(true);
        });

        setVisible(true);
    }

    private void styleTopButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleBottomButton(JButton button) {
        button.setBackground(new Color(100, 100, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.PLAIN, 13));

        button.setOpaque(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminEditAccountUI("Name"));
    }
}
