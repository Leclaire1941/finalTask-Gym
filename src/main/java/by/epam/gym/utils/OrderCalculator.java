package by.epam.gym.utils;

import java.math.BigDecimal;

/**
 * Util class to calculate order's price.
 *
 * @author Dzmitry Turko
 */
public class OrderCalculator {
    private static final int PRICE_PER_DAY = 1;
    private static final int ABSOLUTE_PERCENTAGE = 100;

    /**
     * This method calculates difference between new date and user's old date and returns difference.
     *
     * @param days     the amount of days to pay.
     * @param discount the user's discount.
     * @return BigDecimal order's price.
     */
    public static BigDecimal getOrderPrice(int days, int discount) {
        double price = days * PRICE_PER_DAY;
        double discountAmount = price * discount / ABSOLUTE_PERCENTAGE;
        double priceWithDiscount = Math.ceil(price - discountAmount);

        return new BigDecimal(priceWithDiscount);
    }
}
