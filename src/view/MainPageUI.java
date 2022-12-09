package view;

import control.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainPageUI extends JFrame {
  private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);

  private final Controller controller;
  private final JButton addBookButton;
  private final JButton userInfoButton;
  private final JButton searchButton;

  /**
   * Construct main page UI with the controller.
   *
   * @param controller global controller
   */
  public MainPageUI(Controller controller) {
    this.controller = controller;

    JLabel addBookLabel = new JLabel("ADD BOOK");
    JLabel userInfoLabel = new JLabel("USER INFO", SwingConstants.CENTER);
    JLabel searchLabel = new JLabel("SEARCH", SwingConstants.CENTER);
    addBookButton = new JButton("ADD BOOK");
    userInfoButton = new JButton("USER INFO");
    searchButton = new JButton("SEARCH");

    JLabel bookLabel = new JLabel();
    try {
      BufferedImage img = ImageIO.read(new File("books.png"));
      ImageIcon icon = new ImageIcon(img);
      bookLabel.setIcon(icon);
      bookLabel.setBounds(160, 150, icon.getIconWidth(), icon.getIconHeight());
    } catch (IOException e) {
      e.printStackTrace();
    }

    JPanel panel = new JPanel();
    panel.setLayout(null);

    addBookLabel.setBounds(40, 40, 90, 30);
    userInfoLabel.setBounds(160, 40, 160, 30);
    searchLabel.setBounds(350, 40, 90, 30);
    addBookLabel.setFont(DEFAULT_FONT);
    userInfoLabel.setFont(DEFAULT_FONT);
    searchLabel.setFont(DEFAULT_FONT);

    addBookButton.setBounds(40, 80, 90, 40);
    userInfoButton.setBounds(160, 80, 160, 40);
    searchButton.setBounds(350, 80, 90, 40);

    panel.add(addBookLabel);
    panel.add(userInfoLabel);
    panel.add(searchLabel);
    panel.add(bookLabel);
    panel.add(addBookButton);
    panel.add(userInfoButton);
    panel.add(searchButton);
    add(panel);
    addEventListener();

    setTitle("Main Page");
    setLocationRelativeTo(null);
    setSize(new Dimension(480, 400));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void addEventListener() {
    addBookButton.addActionListener(e -> controller.goAddBookPage());
    searchButton.addActionListener(e -> controller.goSearchBookPage());
    userInfoButton.addActionListener(e -> controller.goUserInfoPage());
    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            controller.exit();
          }
        });
  }
}
