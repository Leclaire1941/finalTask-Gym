package by.epam.gym.model.program;

import by.epam.gym.model.AbstractEntity;

import java.util.Date;
import java.util.Objects;

/**
 * This class describes program in training complex.
 *
 * @author Dzmitry Turko
 * @see ProgramNutrition
 * @see AbstractEntity
 */

public class Program extends AbstractEntity {
    private Integer orderId;
    private Integer trainerId;
    private ProgramNutrition programNutritionName;
    private Date startDate;
    private Date endDate;

    /**
     * Instantiates a new Entity.
     */
    public Program() {
    }

    /**
     * Gets order's ID.
     *
     * @return the order's ID.
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Sets order's ID.
     *
     * @param orderId is the order's ID.
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets trainer's ID.
     *
     * @return the trainer's ID.
     */
    public Integer getTrainerId() {
        return trainerId;
    }

    /**
     * Sets trainer's ID.
     *
     * @param trainerId is the trainer's ID.
     */
    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    /**
     * Gets name of program nutrition.
     *
     * @return the name of program nutrition.
     */
    public ProgramNutrition getProgramNutritionName() {
        return programNutritionName;
    }

    /**
     * Sets name of program nutrition.
     *
     * @param programNutritionName is the name of program nutrition.
     */
    public void setProgramNutritionName(ProgramNutrition programNutritionName) {
        this.programNutritionName = programNutritionName;
    }

    /**
     * Gets program's start date.
     *
     * @return the program's start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets program's start date.
     *
     * @param startDate is the program's start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets program's end date.
     *
     * @return the program's end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets program's end date.
     *
     * @param endDate is the program's end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Program{" +
                "id=" + getId() +
                "orderId=" + orderId +
                "trainerId=" + trainerId +
                ", programNutritionName=" + programNutritionName +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
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

        Program program = (Program) o;
        return Objects.equals(orderId, program.orderId) &&
                Objects.equals(trainerId, program.trainerId) &&
                programNutritionName == program.programNutritionName &&
                Objects.equals(startDate, program.startDate) &&
                Objects.equals(endDate, program.endDate);
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderId, trainerId, programNutritionName, startDate, endDate);
    }
}
