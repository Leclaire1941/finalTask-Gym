package by.epam.gym.dao.impl;

import by.epam.gym.dao.AbstractDao;
import by.epam.gym.model.complex.Complex;
import by.epam.gym.exception.DaoException;
import by.epam.gym.model.complex.ComplexDifficulty;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provide access to the database and deal with Complex entity.
 *
 * @author Dzmitry Turko
 * @see Complex
 * @see Connection
 */
public class ComplexDao extends AbstractDao<Complex> {
    private static final String TABLE_NAME = "complexes";
    private static final String FIND_COMPLEXES_BY_PROGRAM_ID = "select complexes.id, complexes.training_program_id, " +
            "machines.name, complexes.difficulty, complexes.repeat_count from complexes left join machines on " +
            "machine_id=machines.id where training_program_id=?";
    private static final String UPDATE_QUERY = "update complexes set machine_id=?, difficulty=?, " +
            "repeat_count=? where id=?";
    private static final String INSERT_QUERY = "insert into complexes " +
            "(training_program_id, machine_id, difficulty, repeat_count) values (?,?,?,?)";
    private static final String DELETE_ALL_COMPLEXES = "delete from complexes where training_program_id=?";

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
     * This method gets entity's parameters for insert query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getParametersForInsert(Complex entity) {
        List<String> parameters = new ArrayList<>();

        int programId = entity.getProgramId();
        String programIdValue = String.valueOf(programId);
        parameters.add(programIdValue);

        String machineName = entity.getMachineName();
        parameters.add(machineName);

        ComplexDifficulty complexDifficulty = entity.getComplexDifficulty();
        String complexDifficultyValue = String.valueOf(complexDifficulty);
        parameters.add(complexDifficultyValue);

        int repeatCounts = entity.getRepeatCounts();
        String repeatCountsValue = String.valueOf(repeatCounts);
        parameters.add(repeatCountsValue);

        return parameters;
    }

    /**
     * This method gets entity's parameters for update query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getParametersForUpdate(Complex entity) {
        List<String> parameters = new ArrayList<>();

        String machineName = entity.getMachineName();
        parameters.add(machineName);

        ComplexDifficulty complexDifficulty = entity.getComplexDifficulty();
        String complexDifficultyValue = String.valueOf(complexDifficulty);
        parameters.add(complexDifficultyValue);

        int repeatCounts = entity.getRepeatCounts();
        String repeatCountsValue = String.valueOf(repeatCounts);
        parameters.add(repeatCountsValue);

        int complexId = entity.getId();
        String complexIdValue = String.valueOf(complexId);
        parameters.add(complexIdValue);

        return parameters;
    }

    /**
     * Instantiates a new ComplexDao.
     *
     * @param connection the connection to data base.
     */
    public ComplexDao(Connection connection) {
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
     * Method finds all complexes by program ID and returns entities as List.
     *
     * @param programId the program's ID.
     * @return the List of complexes.
     * @throws DaoException object if execution of method is failed.
     */
    public List<Complex> findComplexesByProgramId(int programId) throws DaoException {
        return executeQuery(FIND_COMPLEXES_BY_PROGRAM_ID, programId);
    }

    /**
     * Method finds all complexes by program ID and returns entities as List.
     *
     * @param programId the program's ID.
     * @return the List of complexes.
     * @throws DaoException object if execution of method is failed.
     */
    public boolean removeAllComplexes(int programId) throws DaoException {
        return executeForDelete(DELETE_ALL_COMPLEXES, programId);
    }
}
