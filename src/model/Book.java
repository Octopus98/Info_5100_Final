package model;

import java.util.Objects;

public class Book implements Comparable<Book> {
  private final String bookName;
  private final long expiryDate;
  private final Category category;
  private final String description;

  /**
   * Constructs a book with given parameters.
   *
   * @param bookName book name
   * @param expiryDate expiry days
   * @param category book category
   * @param description book description
   */
  public Book(String bookName, long expiryDate, Category category, String description) {
    this.bookName = bookName;
    this.expiryDate = expiryDate;
    this.category = category;
    this.description = description;
  }

  public static Category parseCategory(String text) {
    for (Category category : Category.values()) {
      if (category.name().equals(text)) {
        return category;
      }
    }

    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;

    return Objects.equals(bookName, book.bookName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookName);
  }

  @Override
  public int compareTo(Book o) {
    return bookName.compareTo(o.bookName);
  }

  public String getBookName() {
    return bookName;
  }

  public long getExpiryDate() {
    return expiryDate;
  }

  public Category getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return bookName + "," + expiryDate + "," + category + "," + description;
  }

  public enum Category {
    ARTS,
    BUSINESS,
    COMICS,
    COMPUTERS,
    COOKING,
    ENTERTAINMENT,
    HEALTH,
    HISTORY
  }
}
