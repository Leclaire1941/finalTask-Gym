package by.epam.gym.service;

import by.epam.gym.dao.impl.OrderDao;
import by.epam.gym.exception.ConnectionException;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Order;
import by.epam.gym.dao.DaoCreator;
import by.epam.gym.exception.DaoException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

/**
 * Service class for Order entity that communicate with OrderDao.
 *
 * @author Dzmitry Turko
 * @see Order
 * @see OrderDao
 * @see ServiceException
 * @see ConnectionException
 * @see DaoCreator
 */
public class OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderService.class);

    /**
     * The method finds concrete order by user ID and returns Optional of this order.
     *
     * @param userId the user's ID.
     * @return the Optional of order.
     * @throws ServiceException object if execution of method is failed.
     */
    public Optional<Order> findOrderByUserId(int userId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            OrderDao orderDao = daoCreator.getOrderDao();

            return orderDao.findOrderByUserId(userId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method finds feedback by order ID and update it.
     *
     * @param orderId     the order's ID.
     * @param newFeedback the new feedback to save.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean updateFeedbackByOrderId(String newFeedback, int orderId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            OrderDao orderDao = daoCreator.getOrderDao();

            return orderDao.updateFeedbackByOrderId(newFeedback, orderId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    /**
     * The method finds concrete order by ID and returns Optional of this object.
     *
     * @param orderId the ID of order.
     * @return the Optional of order.
     * @throws ServiceException object if execution of method is failed.
     */
    public Optional<Order> findById(int orderId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            OrderDao orderDao = daoCreator.getOrderDao();

            return orderDao.findById(orderId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method finds order's expiration date by order ID and returns this date.
     *
     * @param programId the ID of program.
     * @return Date.
     * @throws ServiceException object if execution of method is failed.
     */
    public Date findOrderExpirationDateByProgramId(int programId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            OrderDao orderDao = daoCreator.getOrderDao();

            return orderDao.findOrderExpirationDateByProgramId(programId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method extend order expiration date and update account balance info. Returns true if all functions were done.
     * Rollback to save point and returns false if any functions were not done.
     *
     * @param newDateValue new value of order's expiration date.
     * @param price        value to minus from user's account balance.
     * @param userId       the user's ID.
     * @return boolean.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean extendOrder(String newDateValue, BigDecimal price, int userId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            OrderDao orderDao = daoCreator.getOrderDao();
            Connection connection = orderDao.getConnection();

            try {
                connection.setAutoCommit(false);

                boolean isAllInfoUpdated = orderDao.extendOrderByUserId(newDateValue, price, userId);
                if (isAllInfoUpdated) {
                    connection.commit();
                    return true;
                }
                connection.rollback();
                return false;

            } catch (SQLException | DaoException e) {
                LOGGER.debug("Error while extending order. Start rollback.", e);
                try {
                    connection.rollback();
                    return false;
                } catch (SQLException e1) {
                    throw new ServiceException("Error while extending order. Cannot rollback data.", e1);
                }

            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    LOGGER.error("AutoCommit is false.");
                }
            }
        } catch (ConnectionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

