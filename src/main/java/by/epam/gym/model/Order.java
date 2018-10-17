package by.epam.gym.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

/**
 * Class describes model of order.
 *
 * @author Dzmitry Turko
 * @see AbstractEntity
 */
public class Order extends AbstractEntity {
    private Integer userId;
    private BigDecimal price;
    private Date purchaseDate;
    private Date expirationDate;
    private String feedback;

    /**
     * Instantiates a new Entity.
     */
    public Order() {
    }

    /**
     * Gets entity's id.
     *
     * @return the entity's id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets entity's id.
     *
     * @param userId the entity's id.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets entity's price.
     *
     * @return the entity's price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets entity's price.
     *
     * @param price the entity's price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets entity's purchase date.
     *
     * @return the entity's purchase date.
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets entity's purchase date.
     *
     * @param purchaseDate the entity's purchase date.
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets entity's expiration date.
     *
     * @return the entity's expiration date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets entity's expiration date.
     *
     * @param expirationDate the entity's expiration date.
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Gets entity's feedback.
     *
     * @return the entity's feedback.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets entity's feedback.
     *
     * @param feedback the entity's feedback.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                "userId=" + userId +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                ", expirationDate=" + expirationDate +
                ", feedback='" + feedback + '\'' +
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

        Order order = (Order) o;
        return Objects.equals(userId, order.userId) &&
                Objects.equals(price, order.price) &&
                Objects.equals(purchaseDate, order.purchaseDate) &&
                Objects.equals(expirationDate, order.expirationDate) &&
                Objects.equals(feedback, order.feedback);
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, price, purchaseDate, expirationDate, feedback);
    }
}
