package by.epam.gym.dao.impl;

import by.epam.gym.dao.AbstractDao;
import by.epam.gym.model.Order;
import by.epam.gym.exception.DaoException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class that provide access to the database and deal with Order entity.
 *
 * @author Dzmitry Turko
 * @see Order
 * @see Connection
 */
public class OrderDao extends AbstractDao<Order> {
    private static final String TABLE_NAME = "orders";
    private static final String FIND_ORDER_BY_USER_ID = "select * from orders where user_id=?";
    private static final String UPDATE_FEEDBACK_BY_ORDER_ID = "update orders SET feedback=? where id=?;";
    private static final String FIND_ORDER_EXPIRATION_DATE_BY_PROGRAM_ID = "select * from orders where id= " +
            "(select order_id from training_programs where id=?);";
    private static final String UPDATE_ORDER_END_DATE = "update orders set end_date = ? where user_id = ?;";
    private static final String UPDATE_ACCOUNT_BALANCE = "update users set account_balance = account_balance - ? " +
            "where id = ?;";
    private static final String INSERT_QUERY = "insert into orders (user_id, price, purchase_date, end_date, feedback) " +
            "values (?,?,?,?,?)";
    private static final String UPDATE_QUERY = "update orders set user_id=?, price=?, purchase_date=?, " +
            "expiration_date=?, feedback = ? where user_id = ?;";


    /**
     * Instantiates a new OrderDao.
     *
     * @param connection the connection to data base.
     */
    public OrderDao(Connection connection) {
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
    protected List<String> getParametersForInsert(Order entity) {
        List<String> parameters = new ArrayList<>();

        int userId = entity.getUserId();
        String userIdValue = String.valueOf(userId);
        parameters.add(userIdValue);

        BigDecimal price = entity.getPrice();
        String priceValue = String.valueOf(price);
        parameters.add(priceValue);

        Date purchaseDate = entity.getPurchaseDate();
        String purchaseDateValue = String.valueOf(purchaseDate);
        parameters.add(purchaseDateValue);

        Date expirationDate = entity.getExpirationDate();
        String expirationDateValue = String.valueOf(expirationDate);
        parameters.add(expirationDateValue);

        String feedback = entity.getFeedback();
        parameters.add(feedback);

        return parameters;
    }

    /**
     * This method gets entity's parameters for update query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getParametersForUpdate(Order entity) {
        List<String> parameters = getParametersForInsert(entity);

        int orderId = entity.getId();
        String orderIfValue = String.valueOf(orderId);
        parameters.add(orderIfValue);

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
     * The method finds concrete order by user ID and returns Optional of this order.
     *
     * @param userId the user's ID.
     * @return the Optional of order.
     * @throws DaoException object if execution of method is failed.
     */
    public Optional<Order> findOrderByUserId(Integer userId) throws DaoException {
        return executeQueryForSingleResult(FIND_ORDER_BY_USER_ID, userId);
    }

    /**
     * The method finds feedback by order ID and update it.
     *
     * @param orderId     the order's ID.
     * @param newFeedback the new feedback to save.
     * @throws DaoException object if execution of method is failed.
     */
    public boolean updateFeedbackByOrderId(String newFeedback, int orderId) throws DaoException {
        return executeForUpdate(UPDATE_FEEDBACK_BY_ORDER_ID, newFeedback, orderId);
    }

    /**
     * The method finds order's expiration date by order ID and returns this date.
     *
     * @param programId the ID of program.
     * @return Date.
     * @throws DaoException object if execution of method is failed.
     */
    public Date findOrderExpirationDateByProgramId(int programId) throws DaoException {
        Optional<Order> optionalOrder = executeQueryForSingleResult(FIND_ORDER_EXPIRATION_DATE_BY_PROGRAM_ID, programId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return order.getExpirationDate();
        }
        throw new DaoException("Couldn't find order expiration date.");
    }

    /**
     * The method extends order expiration date and updates account balance info.
     *
     * @param newDateValue new value of order's expiration date.
     * @param price        value to minus from user's account balance.
     * @param userId       the user's ID.
     * @throws DaoException object if execution of method is failed.
     */
    public boolean extendOrderByUserId(String newDateValue, BigDecimal price, int userId) throws DaoException {
        boolean isOrderUpdated = executeForUpdate(UPDATE_ORDER_END_DATE, newDateValue, userId);
        boolean isBalanceUpdated = executeForUpdate(UPDATE_ACCOUNT_BALANCE, price, userId);

        return isOrderUpdated && isBalanceUpdated;
    }
}