package by.epam.gym.exception;

/**
 * The type of checked exception that thrown from Dao-level.
 *
 * @author Dzmitry Turko
 * @see Exception
 */
public class DaoException extends Exception {

    /**
     * Instantiates a new DaoException.
     */
    public DaoException() {
    }

    /**
     * Instantiates a new DaoException.
     *
     * @param message the message.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Instantiates a new DaoException.
     *
     * @param message the message.
     * @param cause   the cause.
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new DaoException.
     *
     * @param cause the cause.
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
