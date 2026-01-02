package ui;

import auth.AuthService;
import auth.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Secure Vault - Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center window

        // Main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField(20);

        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField(20);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        // Row 0 - Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsername, gbc);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Row 1 - Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Row 2 - Buttons
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(btnLogin, gbc);
        gbc.gridx = 1;
        panel.add(btnRegister, gbc);

        // Button actions
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            User user = AuthService.login(username, password);
            if (user != null) {
                dispose(); // close login window
                new DashboardFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        });

        btnRegister.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            boolean success = AuthService.register(username, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!");
            }
        });

        add(panel);
    }
}
