package kaftter.exception;

/**
 * Exception used when consuming an invalid payload from the tweets topic.
 */
public class InvalidPayloadException extends Exception {
    public InvalidPayloadException(final String message) {
        super(message);
    }
}
