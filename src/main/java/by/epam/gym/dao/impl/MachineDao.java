package by.epam.gym.dao.impl;

import by.epam.gym.dao.AbstractDao;
import by.epam.gym.exception.DaoException;
import by.epam.gym.model.Machine;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that provide access to the database and deal with Machine entity.
 *
 * @author Dzmitry Turko
 * @see Machine
 * @see Connection
 */
public class MachineDao extends AbstractDao<Machine> {
    private static final String TABLE_NAME = "machines";
    private static final String FIND_MACHINE_BY_NAME = "select * from machines where name=?";
    private static final String INSERT_QUERY = "insert into machines (name, description) values (?,?)";
    private static final String UPDATE_QUERY = "update machines set name=?, description=? where id=?";

    /**
     * Instantiates a new MachineDao.
     *
     * @param connection the connection to data base.
     */
    public MachineDao(Connection connection) {
        super(connection);
    }

    /**
     * Method to return concrete table name of this object from DB.
     *
     * @return String name of table at DB.
     */
    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    /**
     * This method gets entity's parameters for insert query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getParametersForInsert(Machine entity) {
        List<String> parameters = new ArrayList<>();

        String machineName = entity.getName();
        parameters.add(machineName);

        String description = entity.getDescription();
        parameters.add(description);

        return parameters;
    }

    /**
     * This method gets entity's parameters for update query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getParametersForUpdate(Machine entity) {
        List<String> parameters = getParametersForInsert(entity);

        int machineId = entity.getId();
        String machineIdValue = String.valueOf(machineId);
        parameters.add(machineIdValue);

        return parameters;
    }

    /**
     * Method, realize SQL-query insert object's into DB.
     *
     * @return String sql query.
     */
    @Override
    public String getInsertQuery() {
        return INSERT_QUERY;
    }

    /**
     * Method, realize SQL-query update object's into DB.
     *
     * @return String sql query.
     */
    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    /**
     * The method finds machine name by id and returns it's value.
     *
     * @param machineName the name of machine.
     * @return int machine ID.
     * @throws DaoException object if execution of method is failed.
     */
    public int findMachineIdByName(String machineName) throws DaoException {
        Optional<Machine> optionalMachine = executeQueryForSingleResult(FIND_MACHINE_BY_NAME, machineName);

        if (optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            return machine.getId();

        } else {
            throw new DaoException("Couldn't find machine by name.");
        }
    }
}