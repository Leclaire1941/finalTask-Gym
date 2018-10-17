package by.epam.gym.utils;

/**
 * Util class to check discount value for editing user's information.
 *
 * @author Dzmitry Turko
 */
public class DiscountValidator {
    private static final int MAX_DISCOUNT_VALUE = 30;

    /**
     * This method checks discount value.
     *
     * @param discountValue new discount value to save.
     * @return true if data is valid and false otherwise.
     */
    public static boolean checkDiscount(String discountValue) {

        if (discountValue.isEmpty()) {
            return false;
        }

        int discount = Integer.parseInt(discountValue);

        return discount >= 0 && discount <= MAX_DISCOUNT_VALUE;
    }
}