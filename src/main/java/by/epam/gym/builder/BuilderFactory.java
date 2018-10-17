package by.epam.gym.builder;

import by.epam.gym.builder.impl.*;

/**
 * Factory class for building Builder implementation entities.
 *
 * @author Dzmitry Turko
 * @see Builder
 */
public class BuilderFactory {
    private static final String USERS_TABLE = "users";
    private static final String ORDERS_TABLE = "orders";
    private static final String TRAINING_PROGRAMS_TABLE = "training_programs";
    private static final String COMPLEXES_TABLE = "complexes";
    private static final String MACHINES_TABLE = "machines";

    /**
     * This method define concrete builder and return it's instance.
     *
     * @param tableName the name of table in database.
     * @return the defined builder.
     */
    public Builder create(String tableName) {
        switch (tableName) {
            case USERS_TABLE:
                return new UserBuilder();
            case ORDERS_TABLE:
                return new OrderBuilder();
            case TRAINING_PROGRAMS_TABLE:
                return new ProgramBuilder();
            case COMPLEXES_TABLE:
                return new ComplexBuilder();
            case MACHINES_TABLE:
                return new MachineBuilder();

            default:
                throw new IllegalArgumentException("Unknown table name in Builder factory.");
        }
    }
}