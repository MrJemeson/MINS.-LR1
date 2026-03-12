package object;

public class Book {
    private final int id;
    private final String bookName;
    private final String authorName;
    private boolean takenStatus = false;

    public Book(int id, String bookName, String authorName) {
        this.id = id;
        this.bookName = bookName;
        this.authorName = authorName;
    }

    public Book(int id, String bookName, String authorName, boolean takenStatus) {
        this.id = id;
        this.bookName = bookName;
        this.authorName = authorName;
        this.takenStatus = takenStatus;
    }

    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public boolean isTakenStatus() {
        return takenStatus;
    }

    public void setTakenStatus(boolean takenStatus) {
        this.takenStatus = takenStatus;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + "'" +
                ", authorName='" + authorName + "'}";
    }
}
