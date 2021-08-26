package exceptions;

public class StartIsAfterEndException extends Exception{
    public StartIsAfterEndException(String message) {
        super(message);
    }
}
