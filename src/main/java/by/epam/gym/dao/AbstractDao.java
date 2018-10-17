package by.epam.gym.dao;

import by.epam.gym.builder.BuilderFactory;
import by.epam.gym.builder.Builder;
import by.epam.gym.exception.DaoException;
import by.epam.gym.model.AbstractEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Abstract root class of Dao level that provide access to the database and deal with application entities.
 *
 * @param <T> the entity type.
 * @author Dzmitry Turko
 * @see Connection
 */
public abstract class AbstractDao<T extends AbstractEntity> implements Dao<T> {
    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class);
    private static final String SELECT_ALL_FROM = "SELECT * FROM ";
    private static final String WHERE_ID = " WHERE ID=?";
    private static final int FIRST_INDEX = 0;
    private Connection connection;

    /**
     * Instantiates a new AbstractDao.
     *
     * @param connection the connection to database.
     */
    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Abstract method needs to be override at sub classes to return concrete table name.
     *
     * @return String name of table at DB.
     */
    protected abstract String getTableName();

    /**
     * Abstract method needs to be override at sub classes to get entity's parameters for insert query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    protected abstract List<String> getParametersForInsert(T entity);

    /**
     * Abstract method needs to be override at sub classes to get entity's parameters for update query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    protected abstract List<String> getParametersForUpdate(T entity);

    /**
     * Method, realize SQL-query insert object's into DB.
     *
     * @return String sql query.
     */
    public abstract String getInsertQuery();

    /**
     * Method, realize SQL-query update object's into DB.
     *
     * @return String sql query.
     */
    public abstract String getUpdateQuery();

    /**
     * Gets used connection to change the settings.
     *
     * @return used Connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * This method executes query for returning a single result.
     *
     * @param query      the sql query.
     * @param parameters the parameters.
     * @return optional of entity
     * @throws DaoException object if execution of query is failed.
     */
    protected Optional<T> executeQueryForSingleResult(String query, Object... parameters) throws DaoException {
        List<T> objects = executeQuery(query, parameters);
        if (objects.size() == 1) {
            T object = objects.get(FIRST_INDEX);
            return Optional.of(object);

        } else {
            return Optional.empty();
        }
    }

    /**
     * This method executes query for returning a list of entities.
     *
     * @param query      the sql query.
     * @param parameters parameters for sql query.
     * @return list of entities.
     * @throws DaoException object if execution of query is failed.
     */
    protected List<T> executeQuery(String query, Object... parameters) throws DaoException {
        try (PreparedStatement statement = createStatement(query, parameters)) {
            try (ResultSet resultSet = statement.executeQuery()) {

                BuilderFactory builderFactory = new BuilderFactory();
                Builder<T> builder = builderFactory.create(getTableName());

                List<T> objects = new ArrayList<>();
                while (resultSet.next()) {
                    T object = builder.build(resultSet);
                    objects.add(object);
                }
                LOGGER.debug("Query:" + query + " " + Arrays.toString(parameters) + " was executed. Result - " + objects);
                return objects;
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     * This method initialize PreparedStatement object and sets it's parameters.
     *
     * @param query      the sql query.
     * @param parameters list with parameters for sql query.
     * @return PreparedStatement object.
     * @throws SQLException object if execution of query is failed.
     */
    private PreparedStatement createStatement(String query, Object... parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);

        for (int i = 1; i <= parameters.length; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
        return statement;
    }

    /**
     * This method initialize PreparedStatement object and sets it's parameters.
     *
     * @param query the sql query.
     * @param list  with parameters for sql query.
     * @return PreparedStatement object.
     * @throws SQLException object if execution of query is failed.
     */
    private PreparedStatement createStatement(String query, List list) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);

        int i = 1;
        for (Object o : list) {
            statement.setObject(i, list.get(i - 1));
            i++;
        }
        return statement;
    }

    /**
     * This method executes query for update entity's parameters.
     *
     * @param query      the sql query.
     * @param parameters the parameters.
     * @return boolean true if only one row was updated and false if otherwise.
     * @throws DaoException object if execution of query is failed.
     */
    protected boolean executeForUpdate(String query, Object... parameters) throws DaoException {
        try (PreparedStatement statement = createStatement(query, parameters)) {

            int queryResult = statement.executeUpdate();
            if (queryResult == 1) {
                LOGGER.debug("Query:" + query + " " + Arrays.toString(parameters) + " was executed.");
                return true;
            }

            throw new DaoException("On update modify more then 1 record. Query:" + query + " parameters:" +
                    Arrays.toString(parameters));

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     * This method executes query for delete concrete entity from DB.
     *
     * @param query      the sql query.
     * @param parameters the parameters.
     * @return boolean true if any rows ware deleted and false if otherwise.
     * @throws DaoException object if execution of query is failed.
     */
    protected boolean executeForDelete(String query, Object... parameters) throws DaoException {
        try (PreparedStatement statement = createStatement(query, parameters)) {

            int queryResult = statement.executeUpdate();
            if (queryResult != 0) {
                LOGGER.debug("Query:" + query + " " + Arrays.toString(parameters) + " was executed.");
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }

    }

    /**
     * This method finds all entities.
     *
     * @return List of found objects.
     * @throws DaoException object if execution of query is failed.
     */
    @Override
    public List<T> findAll() throws DaoException {
        String query = SELECT_ALL_FROM + getTableName();
        return executeQuery(query);
    }

    /**
     * This method finds entity from database by id.
     *
     * @param id the entity's id.
     * @return optional of the entity.
     * @throws DaoException object if execution of query is failed.
     */
    @Override
    public Optional<T> findById(int id) throws DaoException {
        String query = SELECT_ALL_FROM + getTableName() + WHERE_ID;
        return executeQueryForSingleResult(query, id);
    }

    /**
     * This method updates object in DB.
     *
     * @param object the entity to update.
     * @return boolean.
     * @throws DaoException object if execution of query is failed.
     */
    @Override
    public boolean update(T object) throws DaoException {
        String query = getUpdateQuery();
        List<String> parameters = getParametersForUpdate(object);
        try (PreparedStatement statement = createStatement(query, parameters)) {

            int queryResult = statement.executeUpdate();
            if (queryResult == 1) {
                LOGGER.debug("Query:" + query + " " + parameters + " was executed.");
                return true;
            }

            throw new DaoException("On update modify more then 1 record.");

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     * This method inserts object in DB.
     *
     * @param object the entity to update.
     * @return boolean.
     * @throws DaoException object if execution of query is failed.
     */
    @Override
    public boolean insert(T object) throws DaoException {
        String query = getInsertQuery();
        List<String> parameters = getParametersForInsert(object);
        try (PreparedStatement statement = createStatement(query, parameters)) {

            int queryResult = statement.executeUpdate();
            if (queryResult == 1) {
                LOGGER.debug("Query:" + query + " " + parameters + " was executed.");
                return true;
            }

            throw new DaoException("On update modify more then 1 record.");

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(T object) {
        throw new UnsupportedOperationException("Delete option is not in use yet.");
    }

}
