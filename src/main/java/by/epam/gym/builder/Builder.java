package by.epam.gym.builder;

import by.epam.gym.exception.DaoException;

import java.sql.ResultSet;

/**
 * The interface Builder. Realization of pattern - Command.
 *
 * @author Dzmitry Turko
 * @see ResultSet
 */
public interface Builder<T> {

    /**
     * Need to be implemented by concrete builder classes.
     *
     * @param resultSet the line-by-line access to query results.
     * @throws DaoException object if execution of method is failed.
     */
    T build(ResultSet resultSet) throws DaoException;

}
