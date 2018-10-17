package by.epam.gym.exception;

/**
 * The type of checked exception that thrown while getting a connection from pool.
 *
 * @author Dzmitry Turko
 * @see Exception
 */
public class ConnectionException extends Exception {

    /**
     * Instantiates a new ConnectionException.
     */
    public ConnectionException() {
    }

    /**
     * Instantiates a new ConnectionException.
     *
     * @param message the message.
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new ConnectionException.
     *
     * @param message the message.
     * @param cause   the cause.
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new ConnectionException.
     *
     * @param cause the cause.
     */
    public ConnectionException(Throwable cause) {
        super(cause);
    }
}
