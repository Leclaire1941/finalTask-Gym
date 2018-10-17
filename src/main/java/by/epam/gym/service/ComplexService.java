package by.epam.gym.service;

import by.epam.gym.dao.DaoCreator;
import by.epam.gym.exception.ConnectionException;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.complex.Complex;
import by.epam.gym.dao.impl.ComplexDao;
import by.epam.gym.exception.DaoException;
import by.epam.gym.model.complex.ComplexDifficulty;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Complex entity that communicate with ComplexDao.
 *
 * @author Dzmitry Turko
 * @see Complex
 * @see ComplexDao
 * @see ServiceException
 * @see ConnectionException
 * @see DaoCreator
 */
public class ComplexService {
    private static final Logger LOGGER = Logger.getLogger(ComplexService.class);

    /**
     * The method finds all complexes by program ID and returns entities as List.
     *
     * @param programId the program's ID.
     * @return the List of complexes.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<Complex> findComplexesByProgramId(int programId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            ComplexDao complexDao = daoCreator.getComplexDao();

            return complexDao.findComplexesByProgramId(programId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method finds concrete complex by ID and returns Optional of this object.
     *
     * @param complexId the ID of complex.
     * @return the Optional of complex.
     * @throws ServiceException object if execution of method is failed.
     */
    public Optional<Complex> findById(Integer complexId) throws ServiceException {

        try (DaoCreator daoCreator = new DaoCreator()) {
            ComplexDao complexDao = daoCreator.getComplexDao();

            return complexDao.findById(complexId);

        } catch (DaoException | ConnectionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method updates concrete complex and returns boolean value about result of operation.
     *
     * @param complex the entity of complex object.
     * @return boolean value about result of operation.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean updateComplex(Complex complex) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            ComplexDao complexDao = daoCreator.getComplexDao();

            return complexDao.update(complex);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method adds new complex to DB and returns boolean value about result of operation.
     *
     * @param complex the entity of complex object.
     * @return boolean value about result of operation.
     * @throws ServiceException object if execution of method is failed.
     */
    public boolean addNewComplex(Complex complex) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            ComplexDao complexDao = daoCreator.getComplexDao();

            return complexDao.insert(complex);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method creates complex object for insert query and returns it's entity.
     *
     * @param programId    the program's ID.
     * @param machineId    the ID of machine.
     * @param difficulty   complex difficulty value.
     * @param repeatCounts the amount of repeat counts.
     * @return Complex entity.
     */
    public Complex createComplexForInsert(int programId, int machineId, String difficulty, int repeatCounts) {
        Complex complex = new Complex();

        complex.setProgramId(programId);
        String machineName = String.valueOf(machineId);
        complex.setMachineName(machineName);
        ComplexDifficulty complexDifficulty = ComplexDifficulty.valueOf(difficulty);
        complex.setComplexDifficulty(complexDifficulty);
        complex.setRepeatCounts(repeatCounts);

        return complex;
    }

    /**
     * The method creates complex object for insert query and returns it's entity.
     *
     * @param complexId    the ID of complex.
     * @param machineId    the ID of machine.
     * @param difficulty   complex difficulty value.
     * @param repeatCounts the amount of repeat counts.
     * @return Complex entity.
     */
    public Complex createComplexForUpdate(int machineId, String difficulty, int repeatCounts, int complexId) {
        Complex complex = new Complex();

        complex.setId(complexId);
        String machineName = String.valueOf(machineId);
        complex.setMachineName(machineName);
        ComplexDifficulty complexDifficulty = ComplexDifficulty.valueOf(difficulty);
        complex.setComplexDifficulty(complexDifficulty);
        complex.setRepeatCounts(repeatCounts);

        LOGGER.debug("Complex was built for update - " + complex);
        return complex;
    }

    public boolean removeAllComplexes(int programId) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            ComplexDao complexDao = daoCreator.getComplexDao();

            return complexDao.removeAllComplexes(programId);

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
