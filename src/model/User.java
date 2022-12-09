package model;

import java.text.SimpleDateFormat;
import java.util.*;

public class User implements Comparable<User>, Iterable<Map.Entry<String, Date>> {
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  private final String username;
  private final String password;
  private final Map<String, Date> borrowedBooks;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    borrowedBooks = new TreeMap<>();
  }

  /**
   * Check the user has borrowed the book before.
   *
   * @param bookName book name
   * @return true if the user has borrowed book before false otherwise
   */
  public boolean hasBorrowed(String bookName) {
    return borrowedBooks.containsKey(bookName);
  }

  /**
   * Borrow the book starting from today.
   *
   * @param bookName book name
   * @param date borrowed date
   */
  public void borrowBook(String bookName, Date date) {
    if (hasBorrowed(bookName)) {
      return;
    }
    borrowedBooks.put(bookName, date);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(username, user.username) && Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public int compareTo(User o) {
    return username.compareTo(o.username);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(username).append(",").append(password);

    for (String key : borrowedBooks.keySet()) {
      sb.append(",");
      Date date = borrowedBooks.get(key);
      sb.append(key).append(",").append(DATE_FORMAT.format(date));
    }

    return sb.toString();
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public Iterator<Map.Entry<String, Date>> iterator() {
    return borrowedBooks.entrySet().iterator();
  }
}
