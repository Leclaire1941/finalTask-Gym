package by.epam.gym.model.user;

import by.epam.gym.model.AbstractEntity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * This class describes users of application.
 *
 * @author Dzmitry Turko
 * @see UserRole
 * @see AbstractEntity
 */

public class User extends AbstractEntity {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private Integer discount;
    private BigDecimal accountBalance;

    /**
     * Instantiates a new Entity.
     */
    public User() {
    }

    /**
     * Gets user's login.
     *
     * @return the user's login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets user's login.
     *
     * @param login the user's login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets user's password.
     *
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user's password.
     *
     * @param password the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets user's first name.
     *
     * @return the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets user's first name.
     *
     * @param firstName the user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets user's last name.
     *
     * @return the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets user's last name.
     *
     * @param lastName the user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets user's role.
     *
     * @return the user's role.
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * Sets user's role.
     *
     * @param userRole the user's role.
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * Gets user's account balance.
     *
     * @return the user's account balance.
     */
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    /**
     * Sets user's account balance.
     *
     * @param accountBalance the user's account balance.
     */
    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Gets user's discount.
     *
     * @return the user's discount.
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Sets user's discount.
     *
     * @param discount the user's discount.
     */
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    /**
     * This method builds string information about object.
     *
     * @return string information about object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userRole=" + userRole +
                ", discount=" + discount +
                ", accountBalance=" + accountBalance +
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

        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                userRole == user.userRole &&
                Objects.equals(discount, user.discount) &&
                Objects.equals(accountBalance, user.accountBalance);
    }

    /**
     * This method calculate object's hashcode.
     *
     * @return hashcode of object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password, firstName, lastName, userRole, discount, accountBalance);
    }
}