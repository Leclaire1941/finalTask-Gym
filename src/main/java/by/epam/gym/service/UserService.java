package by.epam.gym.service;

import by.epam.gym.model.user.User;
import by.epam.gym.dao.DaoCreator;
import by.epam.gym.dao.impl.UserDao;
import by.epam.gym.exception.ConnectionException;
import by.epam.gym.exception.DaoException;
import by.epam.gym.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class for User entity that communicate with UserDao.
 *
 * @author Dzmitry Turko
 * @see User
 * @see UserDao
 * @see ServiceException
 * @see ConnectionException
 * @see DaoCreator
 */

public class UserService {

    /**
     * The method returns optional of authorized user.
     *
     * @param login    the user's login.
     * @param password the user's password.
     * @return the optional of User.
     * @throws ServiceException object if execution of method is failed.
     */
    public Optional<User> login(String login, String password) throws ServiceException {

        try (DaoCreator daoCreator = new DaoCreator()) {
            UserDao userDao = daoCreator.getUserDao();

            return userDao.findUserByLoginAndPassword(login, password);

        } catch (DaoException | ConnectionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method finds user by ID and returns optional of it's entity.
     *
     * @return the optional of User.
     * @throws ServiceException object if execution of method is failed.
     */
    public Optional<User> findById(Integer id) throws ServiceException {

        try (DaoCreator daoCreator = new DaoCreator()) {
            UserDao userDao = daoCreator.getUserDao();

            return userDao.findById(id);

        } catch (DaoException | ConnectionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method finds all users where type equals "client" and returns entities as List .
     *
     * @return the List of all clients.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<User> findAllClients() throws ServiceException {

        try (DaoCreator daoCreator = new DaoCreator()) {
            UserDao userDao = daoCreator.getUserDao();

            return userDao.findAllClients();

        } catch (DaoException | ConnectionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method returns String name of trainer (User) with concrete ID.
     *
     * @param programId the trainer's ID.
     * @return the name of trainer.
     * @throws ServiceException object if execution of method is failed.
     */
    public String findTrainerNameByProgramId(int programId) throws ServiceException {

        try (DaoCreator daoCreator = new DaoCreator()) {
            UserDao userDao = daoCreator.getUserDao();

            return userDao.findTrainerNameByProgramId(programId);

        } catch (DaoException | ConnectionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method change account balance of user with concrete ID and returns new balance.
     *
     * @param userId the user's ID.
     * @param amount the value to increment the account balance.
     * @return the new balance of account.
     * @throws ServiceException object if execution of method is failed.
     */
    public BigDecimal takePrepaymentById(int userId, BigDecimal amount) throws ServiceException {

        try (DaoCreator daoCreator = new DaoCreator()) {
            UserDao userDao = daoCreator.getUserDao();

            return userDao.takePrepaymentById(userId, amount);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method finds user's discount and returns it's value.
     *
     * @param clientId the user's ID.
     * @return int.
     * @throws ServiceException object if execution of method is failed.
     */
    public int findClientDiscount(int clientId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            UserDao userDao = daoCreator.getUserDao();

            Optional<User> optionalUser = userDao.findById(clientId);

            if (optionalUser.isPresent()) {
                User client = optionalUser.get();
                return client.getDiscount();
            }
            throw new ServiceException("Couldn't get user discount for trainer information.");

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method update user's discount value in DB.
     *
     * @param clientId the user's ID.
     * @param discount new value of discount.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean updateDiscountById(int discount, int clientId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            UserDao userDao = daoCreator.getUserDao();

            return userDao.updateDiscountById(discount, clientId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
