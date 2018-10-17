package by.epam.gym.builder.impl;

import by.epam.gym.model.user.User;
import by.epam.gym.model.user.UserRole;
import by.epam.gym.builder.Builder;
import by.epam.gym.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Builder to build user.
 *
 * @author Dzmitry Turko
 * @see Builder
 * @see ResultSet
 */
public class UserBuilder implements Builder {
    private static final Logger LOGGER = LogManager.getLogger(UserBuilder.class);
    private static final String ID_LABEL = "id";
    private static final String LOGIN_LABEL = "login";
    private static final String PASSWORD_LABEL = "password";
    private static final String FIRST_NAME_LABEL = "first_name";
    private static final String LAST_NAME_LABEL = "last_name";
    private static final String TYPE_LABEL = "type";
    private static final String DISCOUNT_LABEL = "discount";
    private static final String ACCOUNT_BALANCE_LABEL = "account_balance";

    /**
     * Implementation of Builder to build concrete user and returns it's entity.
     *
     * @param resultSet the line-by-line access to query results.
     * @return user's entity.
     * @throws DaoException object if execution of method is failed.
     */
    public User build(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            Integer id = resultSet.getInt(ID_LABEL);
            user.setId(id);

            String login = resultSet.getString(LOGIN_LABEL);
            user.setLogin(login);

            String password = resultSet.getString(PASSWORD_LABEL);
            user.setPassword(password);

            String firstName = resultSet.getString(FIRST_NAME_LABEL);
            user.setFirstName(firstName);

            String lastName = resultSet.getString(LAST_NAME_LABEL);
            user.setLastName(lastName);

            String userRoleValue = resultSet.getString(TYPE_LABEL);
            UserRole userRole = UserRole.valueOf(userRoleValue);
            user.setUserRole(userRole);

            Integer discount = resultSet.getInt(DISCOUNT_LABEL);
            user.setDiscount(discount);

            BigDecimal balance = resultSet.getBigDecimal(ACCOUNT_BALANCE_LABEL);
            user.setAccountBalance(balance);

            LOGGER.debug("User built - " + user);
            return user;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}