package by.epam.gym.model.complex;

import by.epam.gym.model.AbstractEntity;

import java.util.Objects;

/**
 * This class describes complex of training.
 *
 * @author Dzmitry Turko
 * @see ComplexDifficulty
 * @see AbstractEntity
 */

public class Complex extends AbstractEntity {
    private Integer programId;
    private String machineName;
    private ComplexDifficulty complexDifficulty;
    private Integer repeatCounts;

    /**
     * Instantiates a new Entity.
     */
    public Complex() {
    }

    /**
     * Gets program's ID.
     *
     * @return the program's ID.
     */
    public Integer getProgramId() {
        return programId;
    }

    /**
     * Sets program's ID.
     *
     * @param programId is the program's ID.
     */
    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    /**
     * Gets name of machine.
     *
     * @return the name of machine.
     */
    public String getMachineName() {
        return machineName;
    }

    /**
     * Sets name of machine.
     *
     * @param machineName is the name of machine.
     */
    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    /**
     * Gets enum difficulty of complex.
     *
     * @return the enum difficulty of complex.
     */
    public ComplexDifficulty getComplexDifficulty() {
        return complexDifficulty;
    }

    /**
     * Sets enum difficulty of complex.
     *
     * @param complexDifficulty is the enum difficulty of complex.
     */
    public void setComplexDifficulty(ComplexDifficulty complexDifficulty) {
        this.complexDifficulty = complexDifficulty;
    }

    /**
     * Gets the amount of repeats.
     *
     * @return the amount of repeats.
     */
    public Integer getRepeatCounts() {
        return repeatCounts;
    }

    /**
     * Sets the amount of repeats.
     *
     * @param repeatCounts is the amount of repeats.
     */
    public void setRepeatCounts(Integer repeatCounts) {
        this.repeatCounts = repeatCounts;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Complex{" +
                "id=" + getId() +
                " programId=" + programId +
                " machineName=" + machineName +
                ", complexDifficulty=" + complexDifficulty +
                ", repeatCounts=" + repeatCounts +
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

        Complex complex = (Complex) o;
        return Objects.equals(programId, complex.programId) &&
                Objects.equals(machineName, complex.machineName) &&
                complexDifficulty == complex.complexDifficulty &&
                Objects.equals(repeatCounts, complex.repeatCounts);
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), programId, machineName, complexDifficulty, repeatCounts);
    }
}
