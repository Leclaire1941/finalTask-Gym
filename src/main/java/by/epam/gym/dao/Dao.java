package by.epam.gym.dao;

import by.epam.gym.exception.DaoException;
import by.epam.gym.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface has all main methods to work with entity.
 *
 * @author Dzmitry Turko
 * @param <T> The entity.
 */
public interface Dao<T extends AbstractEntity>  {

    /**
     * This method finds all entities.
     *
     * @return List of found objects.
     * @throws DaoException object if execution of query is failed.
     */
    List<T> findAll() throws DaoException;

    /**
     * This method finds entity from database by id.
     *
     * @param id the entity's id.
     * @return optional of the entity.
     * @throws DaoException object if execution of query is failed.
     */
    Optional<T> findById(int id) throws DaoException;

    /**
     * This method insert entity in database.
     *
     * @param object the entity.
     * @return boolean true if entity created successfully, otherwise false.
     * @throws DaoException object if execution of query is failed.
     */
    boolean insert(T object) throws DaoException;

    /**
     * This method deletes entity from database by entity.
     *
     * @param object the entity.
     * @throws DaoException object if execution of query is failed.
     */
    boolean delete(T object) throws DaoException;

    /**
     * This method update entity in database.
     *
     * @param object the entity.
     * @throws DaoException object if execution of query is failed.
     */
    boolean update(T object) throws DaoException;
}