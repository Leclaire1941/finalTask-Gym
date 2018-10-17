package by.epam.gym.dao.impl;

import by.epam.gym.dao.AbstractDao;
import by.epam.gym.exception.DaoException;
import by.epam.gym.model.program.Program;
import by.epam.gym.model.program.ProgramNutrition;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class that provide access to the database and deal with Program entity.
 *
 * @author Dzmitry Turko
 * @see Program
 * @see Connection
 */
public class ProgramDao extends AbstractDao<Program> {
    private static final String TABLE_NAME = "training_programs";
    private static final String FIND_PROGRAM_BY_ORDER_ID = "select * from training_programs where order_id=?";
    private static final String UPDATE_PROGRAM_BASIC_INFO = "update training_programs set nutrition_type=?, " +
            "end_date =? where id = ?;";
    private static final String INSERT_QUERY = "insert into training_programs (order_id, trainer_id, nutrition_type, " +
            "start_date, end_date) values (?,?,?,?,?)";
    private static final String UPDATE_QUERY = "update training_programs set order_id=?, trainer_id =?," +
            " program_nutrition=?, start_date=?, end_date=? where id = ?";

    /**
     * Instantiates a new ProgramDao.
     *
     * @param connection the connection to data base.
     */
    public ProgramDao(Connection connection) {
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
    protected List<String> getParametersForInsert(Program entity) {
        List<String> parameters = new ArrayList<>();

        int orderId = entity.getOrderId();
        String orderIdValue = String.valueOf(orderId);
        parameters.add(orderIdValue);

        int trainerId = entity.getTrainerId();
        String trainerIdValue = String.valueOf(trainerId);
        parameters.add(trainerIdValue);

        ProgramNutrition nutrition = entity.getProgramNutritionName();
        String nutritionValue = String.valueOf(nutrition);
        parameters.add(nutritionValue);

        Date startDate = entity.getStartDate();
        String startDateValue = String.valueOf(startDate);
        parameters.add(startDateValue);

        Date endDate = entity.getEndDate();
        String endDateValue = String.valueOf(endDate);
        parameters.add(endDateValue);

        return parameters;

    }

    /**
     * This method gets entity's parameters for update query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getParametersForUpdate(Program entity) {
        List<String> parameters = getParametersForInsert(entity);

        int programId = entity.getId();
        String programIdValue = String.valueOf(programId);
        parameters.add(programIdValue);

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
     * The method finds concrete program by order ID and returns Optional of this program.
     *
     * @param orderId the orders's ID.
     * @return the Optional of program.
     * @throws DaoException object if execution of method is failed.
     */
    public Optional<Program> findProgramByOrderId(int orderId) throws DaoException {
        return executeQueryForSingleResult(FIND_PROGRAM_BY_ORDER_ID, orderId);
    }

    /**
     * The method update client's current program.
     *
     * @param programId         the program's ID.
     * @param nutrition         new value of nutrition.
     * @param programExpiration new value of program expiration date.
     * @throws DaoException object if execution of method is failed.
     */
    public boolean updateClientProgram(String nutrition, String programExpiration, int programId) throws DaoException {
        return executeForUpdate(UPDATE_PROGRAM_BASIC_INFO, nutrition, programExpiration, programId);
    }
}