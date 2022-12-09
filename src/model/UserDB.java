package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static model.User.DATE_FORMAT;

public class UserDB implements Iterable<User> {

  private final Set<User> userSet;

  /**
   * Construct a user in memory database from the file.
   *
   * @param path db file path
   */
  public UserDB(String path) {
    userSet = new TreeSet<>();
    initialize(path);
  }

  private void initialize(String path) {
    try {
      String line;
      BufferedReader reader = new BufferedReader(new FileReader(path));
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(",");
        if (tokens.length < 2) {
          System.err.println("Invalid line: " + line);
          continue;
        }

        User user = new User(tokens[0], tokens[1]);
        for (int i = 2; i < tokens.length; i += 2) {
          String bookName = tokens[i].trim();
          Date date = DATE_FORMAT.parse(tokens[i + 1].trim());
          user.borrowBook(bookName, date);
        }

        addUser(user);
      }

      reader.close();
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * Check the user exists in user.
   *
   * @param username user name
   * @param password password
   * @return a reference to user if the user exists null otherwise
   */
  public User findUser(String username, String password) {
    return userSet.stream()
        .filter(e -> e.getUsername().equals(username) && e.getPassword().equals(password))
        .findFirst()
        .orElse(null);
  }

  /**
   * Add user to database.
   *
   * @param user a reference to user
   */
  public void addUser(User user) {
    userSet.add(user);
  }

  @Override
  public Iterator<User> iterator() {
    return userSet.iterator();
  }
}
