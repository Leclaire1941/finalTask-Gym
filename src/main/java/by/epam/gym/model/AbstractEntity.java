package by.epam.gym.model;

/**
 * This abstract class shows type of Entity with unique ID.
 *
 * @author Dzmitry Turko
 */

public abstract class AbstractEntity {
    private Integer id;

    /**
     * Instantiates a new AbstractEntity.
     */
    protected AbstractEntity() {
    }

    /**
     * Instantiates a new AbstractEntity.
     *
     * @param id is unique ID of entity.
     */
    public AbstractEntity(Integer id) {
        this.id = id;
    }

    /**
     * Gets entity's id.
     *
     * @return the entity's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets entity's id.
     *
     * @param id the entity's id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method equals two objects.
     *
     * @param o the object.
     * @return true if objects are equal and false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbstractEntity that = (AbstractEntity) o;
        return id.equals(that.id);
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        return 31 * id;
    }
}
