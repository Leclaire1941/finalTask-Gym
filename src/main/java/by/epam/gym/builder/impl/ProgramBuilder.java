package by.epam.gym.builder.impl;

import by.epam.gym.model.program.Program;
import by.epam.gym.model.program.ProgramNutrition;
import by.epam.gym.builder.Builder;
import by.epam.gym.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Builder to build program.
 *
 * @author Dzmitry Turko
 * @see Builder
 * @see ResultSet
 */
public class ProgramBuilder implements Builder {
    private static final Logger LOGGER = LogManager.getLogger(ProgramBuilder.class);
    private static final String ID_LABEL = "id";
    private static final String ORDER_ID_LABEL = "order_id";
    private static final String TRAINER_ID_LABEL = "trainer_id";
    private static final String NUTRITION_LABEL = "nutrition_type";
    private static final String START_DATE_LABEL = "start_date";
    private static final String END_DATE_LABEL = "end_date";

    /**
     * Implementation of Builder to build concrete program and returns it's entity.
     *
     * @param resultSet the line-by-line access to query results.
     * @return program's entity.
     * @throws DaoException object if execution of method is failed.
     */
    @Override
    public Object build(ResultSet resultSet) throws DaoException {
        Program program = new Program();
        try {
            Integer id = resultSet.getInt(ID_LABEL);
            program.setId(id);

            Integer orderId = resultSet.getInt(ORDER_ID_LABEL);
            program.setOrderId(orderId);

            Integer trainerId = resultSet.getInt(TRAINER_ID_LABEL);
            program.setOrderId(trainerId);

            String nutritionValue = resultSet.getString(NUTRITION_LABEL);
            ProgramNutrition nutrition = ProgramNutrition.valueOf(nutritionValue);
            program.setProgramNutritionName(nutrition);

            Date startDate = resultSet.getDate(START_DATE_LABEL);
            program.setStartDate(startDate);

            Date endDate = resultSet.getDate(END_DATE_LABEL);
            program.setEndDate(endDate);

            LOGGER.debug("Program built - " + program);
            return program;

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}