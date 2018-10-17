package by.epam.gym.builder.impl;

import by.epam.gym.model.complex.Complex;
import by.epam.gym.model.complex.ComplexDifficulty;
import by.epam.gym.builder.Builder;
import by.epam.gym.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Builder to build complex.
 *
 * @author Dzmitry Turko
 * @see Builder
 * @see ResultSet
 */
public class ComplexBuilder implements Builder {
    private static final Logger LOGGER = LogManager.getLogger(ComplexBuilder.class);
    private static final String ID_LABEL = "id";
    private static final String TRAINING_PROGRAM_ID_LABEL = "training_program_id";
    private static final String MACHINE_NAME_LABEL = "name";
    private static final String DIFFICULTY_LABEL = "difficulty";
    private static final String REPEAT_COUNT_LABEL = "repeat_count";

    /**
     * Implementation of Builder to build concrete complex and returns it's entity.
     *
     * @param resultSet the line-by-line access to query results.
     * @return entity of complex.
     * @throws DaoException object if execution of method is failed.
     */
    @Override
    public Object build(ResultSet resultSet) throws DaoException {
        Complex complex = new Complex();
        try {
            Integer id = resultSet.getInt(ID_LABEL);
            complex.setId(id);

            Integer programId = resultSet.getInt(TRAINING_PROGRAM_ID_LABEL);
            complex.setProgramId(programId);

            String machineName = resultSet.getString(MACHINE_NAME_LABEL);
            complex.setMachineName(machineName);

            String difficultyValue = resultSet.getString(DIFFICULTY_LABEL);
            ComplexDifficulty difficulty = ComplexDifficulty.valueOf(difficultyValue);
            complex.setComplexDifficulty(difficulty);

            Integer repeatCounts = resultSet.getInt(REPEAT_COUNT_LABEL);
            complex.setRepeatCounts(repeatCounts);

            LOGGER.debug("Complex built - " + complex);
            return complex;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}