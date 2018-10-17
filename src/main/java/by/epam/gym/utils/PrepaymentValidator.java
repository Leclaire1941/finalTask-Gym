package by.epam.gym.utils;

/**
 * Util class to check prepayment value for editing user's information.
 *
 * @author Dzmitry Turko
 */
public class PrepaymentValidator {
    private static final int MAX_PREPAYMENT_VALUE = 50;

    /**
     * This method checks discount value.
     *
     * @param prepaymentValue value to add to the current balance.
     * @return true if data is valid and false otherwise.
     */
    public static boolean checkPrepayment(String prepaymentValue) {

        if (prepaymentValue.isEmpty()) {
            return false;
        }

        int prepayment = Integer.parseInt(prepaymentValue);

        return prepayment > 0 && prepayment <= MAX_PREPAYMENT_VALUE;
    }
}
