package by.epam.gym.service;

import by.epam.gym.dao.DaoCreator;
import by.epam.gym.dao.impl.ProgramDao;
import by.epam.gym.exception.ConnectionException;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.program.Program;
import by.epam.gym.exception.DaoException;

import java.util.Optional;

/**
 * Service class for Program entity that communicate with ProgramDao.
 *
 * @author Dzmitry Turko
 * @see Program
 * @see ProgramDao
 * @see ServiceException
 * @see ConnectionException
 * @see DaoCreator
 */

public class ProgramService {

    /**
     * The method finds concrete program by order ID and returns Optional of this program.
     *
     * @param orderId the orders's ID.
     * @return the Optional of program.
     * @throws ServiceException object if execution of method is failed.
     */
    public Optional<Program> findProgramByOrderId(int orderId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            ProgramDao programDao = daoCreator.getProgramDao();
            return programDao.findProgramByOrderId(orderId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method update client's current program.
     *
     * @param programId         the program's ID.
     * @param nutrition         new value of nutrition.
     * @param programExpiration new value of program expiration date.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean updateClientProgram(String nutrition, String programExpiration, int programId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            ProgramDao programDao = daoCreator.getProgramDao();
            return programDao.updateClientProgram(nutrition, programExpiration, programId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
