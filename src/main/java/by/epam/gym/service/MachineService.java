package by.epam.gym.service;

import by.epam.gym.dao.DaoCreator;
import by.epam.gym.dao.impl.MachineDao;
import by.epam.gym.exception.ConnectionException;
import by.epam.gym.exception.DaoException;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Machine;

import java.util.List;

/**
 * Service class for Machine entity that communicate with MachineDao.
 *
 * @author Dzmitry Turko
 * @see Machine
 * @see MachineDao
 * @see ServiceException
 * @see ConnectionException
 * @see DaoCreator
 */
public class MachineService {

    /**
     * The method finds all machines and returns entities as List.
     *
     * @return the List of machines.
     * @throws ServiceException object if execution of method is failed.
     */
    public List<Machine> findAll() throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            MachineDao machineDao = daoCreator.getMachineDao();

            return machineDao.findAll();

        } catch (ConnectionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * The method finds machine name by id and returns it's value.
     *
     * @param machineName the name of machine.
     * @return int machine ID.
     * @throws ServiceException object if execution of method is failed.
     */
    public int findMachineIdByName(String machineName) throws ServiceException {
        try (DaoCreator daoCreator = new DaoCreator()) {
            MachineDao machineDao = daoCreator.getMachineDao();

            return machineDao.findMachineIdByName(machineName);

        } catch (DaoException | ConnectionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
