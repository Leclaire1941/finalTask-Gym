package by.epam.gym.builder.impl;

import by.epam.gym.builder.Builder;
import by.epam.gym.exception.DaoException;
import by.epam.gym.model.Machine;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Builder to build machine.
 *
 * @author Dzmitry Turko
 * @see Builder
 * @see ResultSet
 */
public class MachineBuilder implements Builder {
    private static final Logger LOGGER = LogManager.getLogger(MachineBuilder.class);
    private static final String ID_LABEL = "id";
    private static final String NAME_LABEL = "name";
    private static final String DESCRIPTION_LABEL = "description";

    /**
     * Implementation of Builder to build concrete machine and return's it's entity.
     *
     * @param resultSet the line-by-line access to query results.
     * @return machine's entity.
     * @throws DaoException object if execution of method is failed.
     */
    @Override
    public Object build(ResultSet resultSet) throws DaoException {
        Machine machine = new Machine();
        try {
            Integer id = resultSet.getInt(ID_LABEL);
            machine.setId(id);

            String name = resultSet.getString(NAME_LABEL);
            machine.setName(name);

            String description = resultSet.getString(DESCRIPTION_LABEL);
            machine.setDescription(description);

            LOGGER.debug("Machine built - " + machine);
            return machine;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}