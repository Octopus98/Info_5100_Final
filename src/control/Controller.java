package control;

import model.*;
import view.*;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Controller {
  private static final String DEFAULT_USER_PATH = "user.txt";
  private static final String DEFAULT_BOOK_PATH = "book.txt";

  private final Map<State, JFrame> ui;
  private final String userPath;
  private final String bookPath;
  private final UserDB userDB;
  private final BookDB bookDB;
  private User currentUser;
  private State state;

  /** Construct a default controller. */
  public Controller() {
    this(DEFAULT_USER_PATH, DEFAULT_BOOK_PATH);
  }

  /**
   * Constructs a controller with the given user and book database paths.
   *
   * @param userPath user database text path
   * @param bookPath book database text path
   */
  public Controller(String userPath, String bookPath) {
    this.userPath = userPath;
    this.bookPath = bookPath;
    userDB = new UserDB(userPath);
    bookDB = new BookDB(bookPath);
    ui = new HashMap<>();
    state = State.LOGIN;

    ui.put(State.LOGIN, new LoginUI(this));
    ui.put(State.MAIN_PAGE, new MainPageUI(this));
    ui.put(State.ADD_BOOK, new AddBookUI(this));
    ui.put(State.SEARCH_BOOK, new SearchBookUI(this));
    ui.put(State.USER_INFO, new UserInfoUI(this));

    updateUI();
  }

  private void updateUI() {
    hideAllUI();
    ui.get(state).setVisible(true);
  }

  private void hideAllUI() {
    for (JFrame frame : ui.values()) {
      frame.setVisible(false);
    }
  }

  /**
   * A callback function when user enters user name and password and click login button.
   *
   * @param username user name
   * @param password password
   */
  public void tryLogin(String username, String password) {
    currentUser = userDB.findUser(username, password);

    if (currentUser != null) {
      state = State.MAIN_PAGE;
    } else {
      JOptionPane.showMessageDialog(null, "Invalid username and password");
    }

    updateUI();
  }

  /**
   * A callback function when user name and password and click signup button.
   *
   * @param username user name
   * @param password password
   */
  public void trySignup(String username, String password) {
    User user = userDB.findUser(username, password);

    if (user != null) {
      JOptionPane.showMessageDialog(null, "You have been registered.");
    } else {
      if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Username and password can't be empty.");
        return;
      }

      userDB.addUser(new User(username, password));
      JOptionPane.showMessageDialog(null, "Succeed to register.");
    }
  }

  /** Go to add book page */
  public void goAddBookPage() {
    state = State.ADD_BOOK;
    updateUI();
  }

  /** Go to search book page. */
  public void goSearchBookPage() {
    state = State.SEARCH_BOOK;
    updateUI();
  }
  /**
   * Add book to database.
   *
   * @param bookName book name
   * @param expiryDate book expiry date in days
   * @param category book category
   * @param description book description
   */
  public void addBook(String bookName, String expiryDate, String category, String description) {
    if (bookName.isEmpty() || expiryDate.isEmpty() || category.isEmpty() || description.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Each entry can't be empty.");
      return;
    }

    if (bookDB.contains(bookName)) {
      JOptionPane.showMessageDialog(null, "This book has been added.");
      return;
    }

    try {
      long days = Long.parseLong(expiryDate);
      if (days <= 0) {
        JOptionPane.showMessageDialog(null, "Expiry date must be positive.");
        return;
      }
      Book.Category cate = Book.parseCategory(category);
      bookDB.addBook(new Book(bookName, days, cate, description));
      JOptionPane.showMessageDialog(null, "Succeed to add book.");
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Invalid expiry date.");
    }
  }

  /** Back to main page. */
  public void goMainPage() {
    state = State.MAIN_PAGE;
    updateUI();
  }

  /**
   * Search book by name.
   *
   * @param bookName book name
   * @return an array of books of the book name is found in database otherwise return null
   */
  public Object[][] searchBook(String bookName) {
    if (bookName.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Search name can't be empty.");
      return null;
    }

    List<Book> bookList = bookDB.getBookByName(bookName);

    if (bookList.isEmpty()) {
      JOptionPane.showMessageDialog(null, bookName + " NOT found.");
      return null;
    }

    Object[][] objects = new Object[bookList.size()][];
    for (int i = 0; i < objects.length; i++) {
      Book book = bookList.get(i);
      objects[i] =
          new Object[] {
            book.getBookName(), book.getExpiryDate(), book.getCategory(), book.getDescription()
          };
    }

    return objects;
  }

  public Object[][] getUserBorrowedBook() {
    if (currentUser == null) {
      return null;
    }

    List<Object[]> temp = new ArrayList<>();
    for (Map.Entry<String, Date> entry : currentUser) {
      Date date = entry.getValue();
      temp.add(new Object[] {entry.getKey(), User.DATE_FORMAT.format(date)});
    }
    Object[][] objects = new Object[temp.size()][];
    for (int i = 0; i < objects.length; i++) {
      objects[i] = temp.get(i);
    }

    return objects;
  }

  /**
   * The user borrows book.
   *
   * @param bookName book name
   */
  public void borrowBook(String bookName) {
    if (currentUser.hasBorrowed(bookName)) {
      JOptionPane.showMessageDialog(null, "You have been borrowed this book.");
      return;
    }

    currentUser.borrowBook(bookName, new Date());
    JOptionPane.showMessageDialog(null, "Succeed to borrow this book.");
  }

  /** Exit the system. */
  public void exit() {
    saveUser(userPath);
    saveBook(bookPath);

    System.exit(0);
  }

  private void saveUser(String filename) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
      for (User user : userDB) {
        bw.write(user.toString() + "\n");
      }

      bw.flush();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveBook(String filename) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
      for (Book book : bookDB) {
        bw.write(book.toString() + "\n");
      }

      bw.flush();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void goUserInfoPage() {
    state = State.USER_INFO;
    UserInfoUI userInfoUI = (UserInfoUI) ui.get(state);
    userInfoUI.updateTable(getUserBorrowedBook(), currentUser.getUsername());
    updateUI();
  }
}
