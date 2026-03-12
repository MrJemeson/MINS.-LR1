package exception;

public class NoOrdersOnUserException extends RuntimeException{
    public NoOrdersOnUserException(String message) {
        super(message);
    }
}
