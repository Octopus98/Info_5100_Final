package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BookDB implements Iterable<Book> {
  private final Set<Book> bookSet;

  /**
   * Construct a book in memory database from the file.
   *
   * @param path db file path
   */
  public BookDB(String path) {
    bookSet = new TreeSet<>();
    initialize(path);
  }

  private void initialize(String path) {
    try {
      String line;
      BufferedReader reader = new BufferedReader(new FileReader(path));
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(",");
        if (tokens.length != 4) {
          System.err.println("Invalid line: " + line);
          continue;
        }

        String bookName = tokens[0].trim();
        long expiryDate = Long.parseLong(tokens[1].trim());
        Book.Category category = Book.parseCategory(tokens[2].trim());
        String description = tokens[3].trim();

        addBook(new Book(bookName, expiryDate, category, description));
      }

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Add book to the database.
   *
   * @param book a reference to book
   */
  public void addBook(Book book) {
    bookSet.add(book);
  }

  @Override
  public Iterator<Book> iterator() {
    return bookSet.iterator();
  }

  /**
   * Check the book has exists.
   *
   * @param bookName book name
   * @return true if the book has existed false otherwise
   */
  public boolean contains(String bookName) {
    return bookSet.stream().anyMatch(e -> e.getBookName().equals(bookName));
  }

  /**
   * Search the database by book name.
   *
   * @param bookName book name
   * @return a list of Book with book name contains the given parameter `bookName`
   */
  public List<Book> getBookByName(String bookName) {
    return bookSet.stream()
        .filter(
            e ->
                e.getBookName()
                    .toLowerCase(Locale.ROOT)
                    .contains(bookName.toLowerCase(Locale.ROOT)))
        .collect(Collectors.toList());
  }
}
