package kaftter.exception;

public class InvalidPayloadException extends Exception {
    public InvalidPayloadException(final String message) {
        super(message);
    }
}
