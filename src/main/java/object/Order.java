package object;

public class Order {
    private final int id;
    private final int bookId;
    private String bookName = "";
    private String bookAuthor = "";
    private final int userId;
    private String userName = "";
    private boolean closedStatus = false;

    public Order(int id, int bookId, int userId) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isClosedStatus() {
        return closedStatus;
    }

    public void setClosedStatus(boolean closedStatus) {
        this.closedStatus = closedStatus;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", bookId=" + bookId + ((bookName.isEmpty())?(""):(", bookName="))  + bookName + ((bookAuthor.isEmpty())?(""):(", bookAuthor=")) + bookAuthor +
                ", userId=" + userId + ((userName.isEmpty())?(""):(", userName=")) + userName +
                ", closedStatus=" + closedStatus +
                '}';
    }
}
