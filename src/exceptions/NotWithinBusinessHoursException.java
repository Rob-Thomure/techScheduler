package exceptions;

public class NotWithinBusinessHoursException extends Exception{
    public NotWithinBusinessHoursException(String message) {
        super(message);
    }
}
