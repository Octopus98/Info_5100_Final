package view;

import control.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInfoUI extends JFrame {
  private static final String[] COLUMNS = {"Borrowed Book", "Borrowed Date"};

  private final Controller controller;
  private final JButton backToMainButton;
  private final JTextField userNameField;
  private final JTable table;

  public UserInfoUI(Controller controller) {
    this.controller = controller;
    userNameField = new JTextField(20);
    backToMainButton = new JButton("BACK TO Main");

    DefaultTableModel model = new DefaultTableModel();
    for (String column : COLUMNS) {
      model.addColumn(column);
    }
    table = new JTable(model);

    JPanel mainPanel = new JPanel(new FlowLayout());
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JLabel userNameLabel = new JLabel("User Name");
    userNameField.setEnabled(false);
    topPanel.add(userNameLabel);
    topPanel.add(userNameField);
    mainPanel.add(topPanel);

    JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JScrollPane scrollPane = new JScrollPane(table);
    middlePanel.add(scrollPane);
    mainPanel.add(middlePanel);

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    bottomPanel.add(backToMainButton);
    mainPanel.add(bottomPanel);

    setContentPane(mainPanel);
    pack();
    addEventListener();

    setTitle("Search Book Page");
    setLocationRelativeTo(null);
    setSize(new Dimension(480, 640));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void addEventListener() {
    backToMainButton.addActionListener(e -> controller.goMainPage());

    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            controller.exit();
          }
        });
  }

  public void updateTable(Object[][] objects, String userName) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    if (objects != null) {
      userNameField.setText(userName);
      model.setRowCount(0);
      for (Object[] obj : objects) {
        model.addRow(obj);
      }
    }
  }
}
