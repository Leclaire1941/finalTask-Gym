package by.epam.gym.builder.impl;

import by.epam.gym.model.Order;
import by.epam.gym.builder.Builder;
import by.epam.gym.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Builder to build order.
 *
 * @author Dzmitry Turko
 * @see Builder
 * @see ResultSet
 */
public class OrderBuilder implements Builder {
    private static final Logger LOGGER = LogManager.getLogger(OrderBuilder.class);
    private static final String ID_LABEL = "id";
    private static final String USER_ID_LABEL = "user_id";
    private static final String PRICE_LABEL = "price";
    private static final String PURCHASE_DATE_LABEL = "purchase_date";
    private static final String EXPIRATION_DATE_LABEL = "end_date";
    private static final String FEEDBACK_LABEL = "feedback";

    /**
     * Implementation of Builder to build concrete order and return's it's entity.
     *
     * @param resultSet the line-by-line access to query results.
     * @return order entity.
     * @throws DaoException object if execution of method is failed.
     */
    @Override
    public Object build(ResultSet resultSet) throws DaoException {
        Order order = new Order();
        try {
            Integer id = resultSet.getInt(ID_LABEL);
            order.setId(id);

            Integer userId = resultSet.getInt(USER_ID_LABEL);
            order.setUserId(userId);

            BigDecimal price = resultSet.getBigDecimal(PRICE_LABEL);
            order.setPrice(price);

            Date purchaseDate = resultSet.getDate(PURCHASE_DATE_LABEL);
            order.setPurchaseDate(purchaseDate);

            Date expirationDate = resultSet.getDate(EXPIRATION_DATE_LABEL);
            order.setExpirationDate(expirationDate);

            if (resultSet.getString(FEEDBACK_LABEL) != null) {
                String feedback = resultSet.getString(FEEDBACK_LABEL);
                order.setFeedback(feedback);
            }
            LOGGER.debug("Order built - " + order);
            return order;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}