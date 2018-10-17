package by.epam.gym.model;

import java.util.Objects;

/**
 * Class describes model of training machine with name and description.
 *
 * @author Dzmitry Turko
 * @see AbstractEntity
 */
public class Machine extends AbstractEntity {
    private String name;
    private String description;

    /**
     * Instantiates a new Entity.
     */
    public Machine() {
    }

    /**
     * Gets entity's name.
     *
     * @return the entity's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets entity's name.
     *
     * @param name the entity's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets entity's description.
     *
     * @return the entity's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets entity's description.
     *
     * @param description the entity's description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Machine{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
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
        if (!super.equals(o))
            return false;

        Machine machine = (Machine) o;
        return Objects.equals(name, machine.name) &&
                Objects.equals(description, machine.description);
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}