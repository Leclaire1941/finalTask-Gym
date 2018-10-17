package by.epam.gym.dao;

import by.epam.gym.dao.impl.*;
import by.epam.gym.db.ConnectionPool;
import by.epam.gym.exception.ConnectionException;

import java.sql.Connection;

/**
 * The class is intended for managing connections, namely the distribution and closing of connections.
 * Also creates instances of Dao objects and puts a connection in them.
 *
 * @author Dzmitry Turko
 */
public class DaoCreator implements AutoCloseable {
    private ConnectionPool connectionPool;
    private Connection connection;

    /**
     * Instantiates a new DaoCreator.
     *
     * @throws ConnectionException object if instantiation of query is failed.
     */
    public DaoCreator() throws ConnectionException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }

    /**
     * Method give access to UserDao
     *
     * @return UserDao
     */
    public UserDao getUserDao() {
        return new UserDao(connection);
    }

    /**
     * Method give access to OrderDao
     *
     * @return OrderDao
     */
    public OrderDao getOrderDao() {
        return new OrderDao(connection);
    }

    /**
     * Method give access to programDao
     *
     * @return ProgramDao
     */
    public ProgramDao getProgramDao() {
        return new ProgramDao(connection);
    }

    /**
     * Method give access to complexDao
     *
     * @return ComplexDao
     */
    public ComplexDao getComplexDao() {
        return new ComplexDao(connection);
    }

    /**
     * Method give access to machineDao
     *
     * @return machineDao
     */
    public MachineDao getMachineDao() {
        return new MachineDao(connection);
    }

    /**
     * Puts the connection back to the connection pool.
     */
    @Override
    public void close() {
        connectionPool.returnConnection(connection);
    }
}