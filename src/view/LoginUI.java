package view;

import control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginUI extends JFrame {
  private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);

  private final Controller controller;
  private final JTextField usernameField;
  private final JTextField passwdField;
  private final JButton loginButton;
  private final JButton signupButton;

  public LoginUI(Controller controller) {
    this.controller = controller;
    usernameField = new JTextField();
    passwdField = new JPasswordField();
    loginButton = new JButton("LOGIN");
    signupButton = new JButton("SIGNUP");

    JLabel usernameLabel = new JLabel("USER NAME");
    JLabel passwdLabel = new JLabel("PASSWORD");
    JPanel panel = new JPanel();
    panel.setLayout(null);

    usernameLabel.setBounds(40, 40, 90, 30);
    usernameLabel.setFont(DEFAULT_FONT);
    usernameField.setBounds(130, 40, 180, 30);
    passwdLabel.setBounds(40, 80, 90, 30);
    passwdLabel.setFont(DEFAULT_FONT);
    passwdField.setBounds(130, 80, 180, 30);
    loginButton.setBounds(130, 140, 80, 40);
    signupButton.setBounds(230, 140, 80, 40);

    panel.add(usernameLabel);
    panel.add(usernameField);
    panel.add(passwdLabel);
    panel.add(passwdField);
    panel.add(loginButton);
    panel.add(signupButton);
    add(panel);
    addEventListener();

    setTitle("Login Page");
    setLocationRelativeTo(null);
    setSize(new Dimension(400, 280));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void addEventListener() {
    loginButton.addActionListener(
        e -> {
          String username = usernameField.getText();
          String password = passwdField.getText();

          controller.tryLogin(username, password);
        });

    signupButton.addActionListener(
        e -> {
          String username = usernameField.getText();
          String password = passwdField.getText();

          controller.trySignup(username, password);
        });

    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            controller.exit();
          }
        });
  }
}
