package by.epam.gym.db;

import by.epam.gym.exception.ConnectionException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread safe connection pool. Pool is a singleton.
 *
 * @author Dzmitry Turko
 * @see LinkedList
 * @see ResourceBundle
 * @see Lock
 * @see Semaphore
 */
public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static final ResourceBundle DATABASE = ResourceBundle.getBundle("database");
    private static final String DRIVER = DATABASE.getString("db.driver");
    private static final String URL = DATABASE.getString("db.url");
    private static final String USER = DATABASE.getString("db.user");
    private static final String PASSWORD = DATABASE.getString("db.password");

    private static final Queue<Connection> pool = new LinkedList<>();
    private static final Lock INSTANCE_LOCKER = new ReentrantLock();
    private final Lock poolLocker = new ReentrantLock();
    private static final int POOL_SIZE = 10;
    private static final int PERMISSIBLE_POOL_SIZE = 3;
    private static Semaphore semaphore;
    private static ConnectionPool instance;

    /**
     * Get instance of connection pool.
     *
     * @return instance.
     * @throws ConnectionException object if execution of method is failed.
     */
    public static ConnectionPool getInstance() throws ConnectionException {
        if (instance == null) {
            INSTANCE_LOCKER.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new ConnectionException("Error while initialize ConnectionPool", e);
            } finally {
                INSTANCE_LOCKER.unlock();
            }
        }
        return instance;
    }

    /**
     * Instantiates a new connection pool and load concrete driver.
     *
     * @throws SQLException           object if execution of method is failed.
     * @throws ClassNotFoundException object if execution of method is failed.
     * @throws ConnectionException    object if execution of method is failed.
     */
    private ConnectionPool() throws SQLException, ClassNotFoundException, ConnectionException {
        semaphore = new Semaphore(POOL_SIZE, true);
        Class.forName(DRIVER);
        initializeConnectionPool();
    }

    /**
     * Initialize the connection pool with concrete size.
     *
     * @throws SQLException        object if execution of method is failed.
     * @throws ConnectionException object if execution of method is failed.
     */
    private void initializeConnectionPool() throws SQLException, ConnectionException {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection currentConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            pool.add(currentConnection);
        }

        if (pool.size() != POOL_SIZE) {
            LOGGER.debug("Given number of connections - " + POOL_SIZE + " | Current number of connections - " + pool.size());
        }
        if (pool.size() < PERMISSIBLE_POOL_SIZE) {
            throw new ConnectionException("There is no required number of connections in pool");
        }
    }

    /**
     * Get connection and remove it from pool.
     *
     * @return first connection from pool.
     * @throws ConnectionException object if execution of method is failed.
     */
    public Connection getConnection() throws ConnectionException {
        try {
            semaphore.acquire();
            poolLocker.lock();
            Connection connection = pool.poll();
            return connection;

        } catch (InterruptedException e) {
            throw new ConnectionException("Error while getting a connection " + e);
        } finally {
            poolLocker.unlock();
        }
    }

    /**
     * Adds concrete connection back to pool.
     *
     * @param connection is a connection that returning to pool.
     */
    public void returnConnection(Connection connection) {
        if (connection != null) {
            try {
                poolLocker.lock();
                semaphore.release();
                pool.add(connection);
            } finally {
                poolLocker.unlock();
            }
        }
    }
}