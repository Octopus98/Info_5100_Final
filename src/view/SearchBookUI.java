package view;

import control.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SearchBookUI extends JFrame {
  private static final String[] COLUMNS = {"Book Name", "Expiry Date", "Category", "Description"};

  private final Controller controller;
  private final JTextField bookNameField;
  private final JButton searchButton;
  private final JButton backToMainButton;
  private final JTable table;
  private final JButton borrowButton;

  public SearchBookUI(Controller controller) {
    this.controller = controller;
    bookNameField = new JTextField(20);
    searchButton = new JButton("SEARCH");
    backToMainButton = new JButton("BACK TO Main");
    borrowButton = new JButton("Borrow");

    DefaultTableModel model = new DefaultTableModel();
    for (String column : COLUMNS) {
      model.addColumn(column);
    }

    table = new JTable(model);

    JLabel bookNameLabel = new JLabel("SEARCH A BOOK");
    JPanel mainPanel = new JPanel(new FlowLayout());
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    topPanel.add(bookNameLabel);
    topPanel.add(bookNameField);
    mainPanel.add(topPanel);

    JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    middlePanel.add(searchButton);
    middlePanel.add(backToMainButton);
    mainPanel.add(middlePanel);

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JScrollPane scrollPane = new JScrollPane(table);
    bottomPanel.add(scrollPane);
    mainPanel.add(bottomPanel);

    JPanel lastPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    lastPanel.add(borrowButton);
    mainPanel.add(lastPanel);

    setContentPane(mainPanel);
    pack();
    addEventListener();

    setTitle("Search Book Page");
    setLocationRelativeTo(null);
    setSize(new Dimension(480, 640));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void addEventListener() {
    searchButton.addActionListener(
        e -> {
          Object[][] objects = controller.searchBook(bookNameField.getText());
          if (objects != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Object[] row : objects) {
              model.addRow(row);
            }
          }
        });

    backToMainButton.addActionListener(e -> controller.goMainPage());

    borrowButton.addActionListener(
        e -> {
          int row = table.getSelectedRow();
          if (row != -1) {
            String bookName = (String) table.getModel().getValueAt(row, 0);
            controller.borrowBook(bookName);
          }
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
