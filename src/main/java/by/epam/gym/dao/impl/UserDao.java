package by.epam.gym.dao.impl;

import by.epam.gym.dao.AbstractDao;
import by.epam.gym.model.user.User;
import by.epam.gym.exception.DaoException;
import by.epam.gym.model.user.UserRole;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that provide access to the database and deal with User entity.
 *
 * @author Dzmitry Turko
 * @see User
 * @see Connection
 */
public class UserDao extends AbstractDao<User> {
    private static final String TABLE_NAME = "users";
    private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "select * from users where login=? " +
            "AND password=sha2(?,256);";
    private static final String GET_ALL_CLIENTS = "select * from users where type='client';";
    private static final String UPDATE_DISCOUNT_BY_USER_ID = "update users set discount=? where id=?;";
    private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE users SET account_balance = account_balance+? " +
            "where id=?;";
    private static final String GET_TRAINER_BY_PROGRAM_ID = "select * from users where id=(select trainer_id " +
            "from training_programs where id=?)";
    private static final String INSERT_QUERY = "insert into users (login, password, first_name, last_name, type, " +
            "discount, account_balance) values (?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "update users set login=?, password=?, first_name=?, " +
            "last_name=?, type=?, discount=?, account_balance=? where id=?;";

    /**
     * Instantiates a new UserDao.
     *
     * @param connection the connection to data base.
     */
    public UserDao(Connection connection) {
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
    protected List<String> getParametersForInsert(User entity) {
        List<String> parameters = new ArrayList<>();

        String login = entity.getLogin();
        parameters.add(login);

        String password = entity.getPassword();
        parameters.add(password);

        String firstName = entity.getFirstName();
        parameters.add(firstName);

        String lastName = entity.getLastName();
        parameters.add(lastName);

        UserRole userRole = entity.getUserRole();
        String userRoleValue = String.valueOf(userRole);
        parameters.add(userRoleValue);

        int discount = entity.getDiscount();
        String discountValue = String.valueOf(discount);
        parameters.add(discountValue);

        BigDecimal accountBalance = entity.getAccountBalance();
        String accountBalanceValue = String.valueOf(accountBalance);
        parameters.add(accountBalanceValue);

        return parameters;
    }

    /**
     * This method gets entity's parameters for update query.
     *
     * @param entity the entity.
     * @return List object with parameters.
     */
    @Override
    protected List<String> getParametersForUpdate(User entity) {
        List<String> parameters = getParametersForInsert(entity);

        int userId = entity.getId();
        String userIdValue = String.valueOf(userId);
        parameters.add(userIdValue);

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
     * The method returns optional of authorized user.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the optional of User.
     * @throws DaoException object if execution of method is failed.
     */
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeQueryForSingleResult(GET_USER_BY_LOGIN_AND_PASSWORD, login, password);
    }

    /**
     * The method finds all users where type equals "client" and returns entities as List .
     *
     * @return the List of all clients.
     * @throws DaoException object if execution of method is failed.
     */
    public List<User> findAllClients() throws DaoException {
        return executeQuery(GET_ALL_CLIENTS);
    }

    /**
     * The method returns String name of trainer (User) with concrete ID.
     *
     * @param programId the trainer's ID.
     * @return String name of the trainer.
     * @throws DaoException object if execution of method is failed.
     */
    public String findTrainerNameByProgramId(int programId) throws DaoException {
        Optional<User> optionalTrainer = executeQueryForSingleResult(GET_TRAINER_BY_PROGRAM_ID, programId);

        if (optionalTrainer.isPresent()) {
            User trainer = optionalTrainer.get();
            return trainer.getFirstName() + " " + trainer.getLastName();

        } else {
            throw new DaoException("Couldn't find trainer name.");
        }
    }

    /**
     * The method change account balance of user with concrete ID and returns new balance.
     *
     * @param userId the user's ID.
     * @param amount the value to increment the account balance.
     * @return the new balance of account.
     * @throws DaoException object if execution of method is failed.
     */
    public BigDecimal takePrepaymentById(int userId, BigDecimal amount) throws DaoException {
        executeForUpdate(UPDATE_ACCOUNT_BALANCE, amount, userId);

        Optional<User> optionalUser = findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getAccountBalance();

        } else {
            throw new DaoException("Couldn't update account balance data.");
        }
    }

    /**
     * The method update user's discount value in DB.
     *
     * @param clientId the user's ID.
     * @param discount new value of discount.
     * @throws DaoException object if execution of method is failed.
     */
    public boolean updateDiscountById(int discount, int clientId) throws DaoException {
        return executeForUpdate(UPDATE_DISCOUNT_BY_USER_ID, discount, clientId);
    }
}