package view;

import control.Controller;
import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class AddBookUI extends JFrame {
  private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);

  private final Controller controller;
  private final JTextField bookNameField;
  private final JTextField expirationField;
  private final JComboBox<String> categoryComboBox;
  private final JTextField descriptionField;
  private final JButton addButton;
  private final JButton backToMainButton;

  public AddBookUI(Controller controller) {
    this.controller = controller;
    bookNameField = new JTextField();
    expirationField = new JPasswordField();
    categoryComboBox = new JComboBox<>();
    descriptionField = new JTextField();
    addButton = new JButton("ADD");
    backToMainButton = new JButton("BACK TO MAIN");

    for (Book.Category category : Book.Category.values()) {
      categoryComboBox.addItem(category.name());
    }

    JPanel panel = new JPanel();
    panel.setLayout(null);

    JLabel bookNameLabel = new JLabel("BOOK NAME");
    JLabel expirationLabel = new JLabel("EXPIRATION DATE");
    JLabel categoryLabel = new JLabel("CATEGORY");
    JLabel descriptionLabel = new JLabel("DESCRIPTION");
    JLabel hintLabel = new JLabel("days");
    bookNameLabel.setBounds(40, 40, 150, 30);
    expirationLabel.setBounds(40, 90, 150, 30);
    hintLabel.setBounds(430, 95, 40, 20);
    categoryLabel.setBounds(40, 140, 150, 30);
    descriptionLabel.setBounds(40, 190, 150, 30);
    bookNameLabel.setFont(DEFAULT_FONT);
    expirationLabel.setFont(DEFAULT_FONT);
    categoryLabel.setFont(DEFAULT_FONT);
    descriptionLabel.setFont(DEFAULT_FONT);

    bookNameField.setBounds(210, 40, 220, 30);
    expirationField.setBounds(210, 90, 220, 30);
    categoryComboBox.setBounds(210, 140, 220, 30);
    descriptionField.setBounds(210, 190, 220, 30);
    addButton.setBounds(80, 240, 120, 40);
    backToMainButton.setBounds(230, 240, 120, 40);

    panel.add(bookNameLabel);
    panel.add(hintLabel);
    panel.add(expirationLabel);
    panel.add(categoryLabel);
    panel.add(descriptionLabel);
    panel.add(bookNameField);
    panel.add(expirationField);
    panel.add(categoryComboBox);
    panel.add(descriptionField);
    panel.add(addButton);
    panel.add(backToMainButton);
    add(panel);
    addEventListener();
    setTitle("Add Book Page");
    setLocationRelativeTo(null);
    setSize(new Dimension(480, 320));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void addEventListener() {
    addButton.addActionListener(
        e -> {
          String bookName = bookNameField.getText();
          String expiryDate = expirationField.getText();
          String category = Objects.requireNonNull(categoryComboBox.getSelectedItem()).toString();
          String description = descriptionField.getText();

          controller.addBook(bookName, expiryDate, category, description);
        });

    backToMainButton.addActionListener(e -> controller.goMainPage());

    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            controller.exit();
          }
        });
  }
}
